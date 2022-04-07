package com.mangajutsu.webclient.controllers;

import java.util.List;

import javax.validation.Valid;

import com.mangajutsu.webclient.models.AnimeModel;
import com.mangajutsu.webclient.models.UserPrincipal;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;

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

import feign.FeignException;

@Controller
@RequestMapping("anime")
public class AnimeController {

    @Autowired
    MangajutsuProxy mangajutsuProxy;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/anime-list")
    public String animeList(final Model model) {
        List<AnimeModel> animes = mangajutsuProxy.getAnimeList();
        model.addAttribute("animes", animes);

        return "anime/anime_list";
    }

    @GetMapping("/anime-details/{title}")
    public String animeDetails(@PathVariable String title, final Model model) {
        AnimeModel anime = mangajutsuProxy.getAnimeDetails(title);
        model.addAttribute("anime", anime);

        return "anime/anime_details";
    }

    @GetMapping("/add-anime")
    public String addAnime(@ModelAttribute("anime") AnimeModel anime, final Model model) {
        List<String> statusList = mangajutsuProxy.getStatus();
        List<String> types = mangajutsuProxy.getTypes();
        List<String> genres = mangajutsuProxy.getGenres();

        model.addAttribute("statusList", statusList);
        model.addAttribute("types", types);
        model.addAttribute("genres", genres);
        model.addAttribute("anime", anime);
        return "anime/add_anime";
    }

    @PostMapping("/add-anime")
    public String addAnime(@Valid @ModelAttribute("anime") AnimeModel anime, final BindingResult bindingResult,
            final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("anime", anime);
            return "anime/add_anime";
        }
        UserPrincipal userInSession = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try {
            System.err.println(userInSession.getUsername());
            mangajutsuProxy.addAnime(anime, userInSession.getUsername());
        } catch (FeignException e) {
            bindingResult.rejectValue("title", "anime.title",
                    messageSource.getMessage("error.anime", null, LocaleContextHolder.getLocale()));
            model.addAttribute("anime", anime);
            return "anime/add_anime";
        }
        model.addAttribute("animeMsg",
                messageSource.getMessage("add-anime.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("anime", anime);

        return "anime/anime_list";
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
        model.addAttribute("anime", anime);
        return "anime/update_anime";
    }

    @PostMapping("/update-anime/{title}")
    public String updateAnime(@Valid @ModelAttribute("anime") AnimeModel anime, final BindingResult bindingResult,
            final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("anime", anime);
            return "anime/update_anime";
        }

        try {
            mangajutsuProxy.updateAnime(anime, anime.getTitle());
        } catch (FeignException e) {
            model.addAttribute("animeError",
                    messageSource.getMessage("error.edit-anime", null,
                            LocaleContextHolder.getLocale()));
            model.addAttribute("anime", anime);
            return "/anime/update_anime";
        }
        model.addAttribute("animeMsg",
                messageSource.getMessage("edit-anime.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("anime", anime);

        return "anime/anime_details";
    }
}
