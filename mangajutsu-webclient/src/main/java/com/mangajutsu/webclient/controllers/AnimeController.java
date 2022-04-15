package com.mangajutsu.webclient.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import com.mangajutsu.webclient.models.AnimeModel;
import com.mangajutsu.webclient.models.FileModel;
import com.mangajutsu.webclient.models.ReviewModel;
import com.mangajutsu.webclient.models.UserPrincipal;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;
import com.mangajutsu.webclient.utils.UploadFileProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import feign.FeignException;

@Controller
@RequestMapping("anime")
public class AnimeController {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @Autowired
    private UploadFileProperties uploadFileProperties;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/anime-list")
    public String animeList(final Model model) {
        List<AnimeModel> animes = mangajutsuProxy.getAnimeList();

        for (AnimeModel anime : animes) {
            List<FileModel> files = mangajutsuProxy.getAnimeFiles(anime.getTitle());
            anime.setFiles(files);
        }

        model.addAttribute("animes", animes);
        return "anime/anime_list";
    }

    @GetMapping("/anime-details/{title}")
    public String animeDetails(@PathVariable String title, final Model model) {
        AnimeModel anime = mangajutsuProxy.getAnimeDetails(title);
        List<FileModel> files = mangajutsuProxy.getAnimeFiles(title);
        List<ReviewModel> reviews = mangajutsuProxy.getAnimeReviews(title);

        anime.setFiles(files);
        anime.setReviews(reviews);

        for (ReviewModel review : reviews) {
            model.addAttribute("userReviewed", review.getUser().getUsername());
        }
        model.addAttribute("anime", anime);
        return "anime/anime_details";
    }

    @GetMapping("/add-anime")
    public String addAnime(final Model model) {
        List<String> statusList = mangajutsuProxy.getStatus();
        List<String> types = mangajutsuProxy.getTypes();
        List<String> genres = mangajutsuProxy.getGenres();

        model.addAttribute("statusList", statusList);
        model.addAttribute("types", types);
        model.addAttribute("genres", genres);
        if (!model.containsAttribute("anime")) {
            model.addAttribute("anime", new AnimeModel());
        }
        return "anime/add_anime";
    }

    @PostMapping("/add-anime")
    public String addAnime(@Valid @ModelAttribute("anime") AnimeModel anime, final BindingResult bindingResult,
            RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.anime",
                    bindingResult);
            redirectAttributes.addFlashAttribute("anime", anime);
            return "redirect:/anime/add-anime";
        }
        UserPrincipal userInSession = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try {
            mangajutsuProxy.addAnime(anime, userInSession.getUsername());
        } catch (FeignException e) {
            bindingResult.rejectValue("title", "anime.title",
                    messageSource.getMessage("error.anime", null, LocaleContextHolder.getLocale()));
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.anime",
                    bindingResult);
            redirectAttributes.addFlashAttribute("anime", anime);
            return "redirect:/anime/add-anime";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("add-anime.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("anime", anime);

        return "redirect:/anime/anime-list";
    }

    @GetMapping("/update-anime/{title}")
    public String updateAnime(@PathVariable String title, final Model model) {
        AnimeModel anime = mangajutsuProxy.getAnimeDetails(title);
        List<String> statusList = mangajutsuProxy.getStatus();
        List<String> types = mangajutsuProxy.getTypes();
        List<String> genres = mangajutsuProxy.getGenres();

        model.addAttribute("statusList", statusList);
        model.addAttribute("types", types);
        model.addAttribute("genres", genres);
        if (!model.containsAttribute("anime")) {
            model.addAttribute("anime", anime);
        }
        return "anime/update_anime";
    }

    @PostMapping("/update-anime/{title}")
    public String updateAnime(@Valid @ModelAttribute("anime") AnimeModel anime, final BindingResult bindingResult,
            RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.anime",
                    bindingResult);
            redirectAttributes.addFlashAttribute("anime", anime);
            return "redirect:/anime/update-anime/{title}";
        }
        try {
            mangajutsuProxy.updateAnime(anime, anime.getTitle());
        } catch (FeignException e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("error.update-anime", null, LocaleContextHolder.getLocale()));
            model.addAttribute("anime", anime);
            return "redirect:/anime/update-anime/{title}";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("update-anime.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("anime", anime);

        return "redirect:/anime/anime-details/{title}";
    }

    @GetMapping("/delete-anime/{title}")
    public String deleteAnime(@PathVariable String title, RedirectAttributes redirectAttributes, final Model model)
            throws IOException {
        List<FileModel> files = mangajutsuProxy.getAnimeFiles(title);
        FileModel animeFile = new FileModel();
        for (FileModel file : files) {
            animeFile = file;
        }
        Path path = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize()
                .resolve(animeFile.getFileName());
        try {
            Files.deleteIfExists(path);
            mangajutsuProxy.deleteAnime(title);
        } catch (FeignException e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("error.delete-anime", null, LocaleContextHolder.getLocale()));
            redirectAttributes.addFlashAttribute("anime", mangajutsuProxy.getAnimeDetails(title));
            return "redirect:/anime/anime-details/{title}";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("error.delete-anime", null, LocaleContextHolder.getLocale()));
            redirectAttributes.addFlashAttribute("anime", mangajutsuProxy.getAnimeDetails(title));
            return "redirect:/anime/anime-details/{title}";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("delete-anime.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("anime", mangajutsuProxy.getAnimeDetails(title));

        return "redirect:/anime/anime-list";
    }
}
