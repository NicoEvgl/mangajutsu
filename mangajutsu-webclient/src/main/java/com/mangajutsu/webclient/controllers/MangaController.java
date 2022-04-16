package com.mangajutsu.webclient.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import com.mangajutsu.webclient.models.FileModel;
import com.mangajutsu.webclient.models.MangaModel;
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
@RequestMapping("/manga")
public class MangaController {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @Autowired
    private UploadFileProperties uploadFileProperties;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/manga-list")
    public String mangaList(final Model model) {
        List<MangaModel> mangas = mangajutsuProxy.getMangaList();

        for (MangaModel manga : mangas) {
            List<FileModel> files = mangajutsuProxy.getMangaFiles(manga.getTitle());
            manga.setFiles(files);
        }

        model.addAttribute("mangas", mangas);
        return "manga/manga_list";
    }

    @GetMapping("/manga-details/{title}")
    public String mangaDetails(@PathVariable String title, final Model model) {
        MangaModel manga = mangajutsuProxy.getMangaDetails(title);
        List<FileModel> files = mangajutsuProxy.getMangaFiles(title);
        List<ReviewModel> reviews = mangajutsuProxy.getMangaReviews(title);

        manga.setFiles(files);
        manga.setReviews(reviews);

        for (ReviewModel review : reviews) {
            model.addAttribute("userReviewed", review.getUser().getUsername());
        }
        model.addAttribute("manga", manga);
        return "manga/manga_details";
    }

    @GetMapping("/add-manga")
    public String addManga(final Model model) {
        List<String> statusList = mangajutsuProxy.getStatus();
        List<String> types = mangajutsuProxy.getTypes();
        List<String> genres = mangajutsuProxy.getGenres();

        model.addAttribute("statusList", statusList);
        model.addAttribute("types", types);
        model.addAttribute("genres", genres);
        if (!model.containsAttribute("manga")) {
            model.addAttribute("manga", new MangaModel());
        }
        return "manga/add_manga";
    }

    @PostMapping("/add-manga")
    public String addManga(@Valid @ModelAttribute("manga") MangaModel manga, final BindingResult bindingResult,
            RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.manga",
                    bindingResult);
            redirectAttributes.addFlashAttribute("manga", manga);
            return "redirect:/manga/add-manga";
        }
        UserPrincipal userInSession = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try {
            mangajutsuProxy.addManga(manga, userInSession.getUsername());
        } catch (FeignException e) {
            bindingResult.rejectValue("title", "manga.title",
                    messageSource.getMessage("error.manga", null, LocaleContextHolder.getLocale()));
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.manga",
                    bindingResult);
            redirectAttributes.addFlashAttribute("manga", manga);
            return "redirect:/manga/add-manga";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("add-manga.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("manga", manga);

        return "redirect:/manga/manga-list";
    }

    @GetMapping("/update-manga/{title}")
    public String updateManga(@PathVariable String title, final Model model) {
        MangaModel manga = mangajutsuProxy.getMangaDetails(title);
        List<String> statusList = mangajutsuProxy.getStatus();
        List<String> types = mangajutsuProxy.getTypes();
        List<String> genres = mangajutsuProxy.getGenres();

        model.addAttribute("statusList", statusList);
        model.addAttribute("types", types);
        model.addAttribute("genres", genres);
        if (!model.containsAttribute("manga")) {
            model.addAttribute("manga", manga);
        }
        return "manga/update_manga";
    }

    @PostMapping("/update-manga/{title}")
    public String updateManga(@Valid @ModelAttribute("manga") MangaModel manga, final BindingResult bindingResult,
            RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.manga",
                    bindingResult);
            redirectAttributes.addFlashAttribute("manga", manga);
            return "redirect:/manga/update-manga/{title}";
        }
        try {
            mangajutsuProxy.updateManga(manga, manga.getTitle());
        } catch (FeignException e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("error.update-manga", null, LocaleContextHolder.getLocale()));
            model.addAttribute("manga", manga);
            return "redirect:/manga/update-manga/{title}";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("update-manga.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("manga", manga);

        return "redirect:/manga/manga-details/{title}";
    }

    @GetMapping("/delete-manga/{title}")
    public String deleteManga(@PathVariable String title, RedirectAttributes redirectAttributes, final Model model)
            throws IOException {
        List<FileModel> files = mangajutsuProxy.getMangaFiles(title);
        FileModel file = new FileModel();
        for (FileModel mangaFile : files) {
            file = mangaFile;
        }
        Path path = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize()
                .resolve(file.getFileName());
        try {
            Files.deleteIfExists(path);
            mangajutsuProxy.deleteManga(title);
        } catch (FeignException e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("error.delete-manga", null, LocaleContextHolder.getLocale()));
            redirectAttributes.addFlashAttribute("manga", mangajutsuProxy.getMangaDetails(title));
            return "redirect:/manga/manga-details/{title}";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("error.delete-manga", null, LocaleContextHolder.getLocale()));
            redirectAttributes.addFlashAttribute("manga", mangajutsuProxy.getMangaDetails(title));
            return "redirect:/manga/manga-details/{title}";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("delete-manga.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("manga", mangajutsuProxy.getMangaDetails(title));

        return "redirect:/manga/manga-list";
    }
}
