package com.mangajutsu.webclient.controllers;

import java.util.List;

import com.mangajutsu.webclient.models.AnimeModel;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AnimeController {

    @Autowired
    MangajutsuProxy mangajutsuProxy;

    @GetMapping("/anime_list")
    public String animeList(final Model model) {
        List<AnimeModel> animes = mangajutsuProxy.getAnimeList();
        model.addAttribute("animes", animes);

        return "anime_list";
    }

    @GetMapping("/anime_details/{title}")
    public String animeDetails(@PathVariable String title, final Model model) {
        AnimeModel anime = mangajutsuProxy.getAnimeDetails(title);
        model.addAttribute("anime", anime);

        return "anime_details";
    }
}
