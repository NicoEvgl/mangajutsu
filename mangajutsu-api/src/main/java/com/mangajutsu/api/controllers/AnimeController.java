package com.mangajutsu.api.controllers;

import java.util.List;

import com.mangajutsu.api.dao.entities.AnimeEntity;
import com.mangajutsu.api.exceptions.AnimeAlreadyExistException;
import com.mangajutsu.api.services.AnimeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    @GetMapping("/anime_list")
    public List<AnimeEntity> getAnimeList() {
        List<AnimeEntity> animes = animeService.getAnimeList();
        return animes;
    }

    @GetMapping("/anime_details/{title}")
    public AnimeEntity getAnimeDetails(@PathVariable String title) {
        AnimeEntity anime = animeService.getAnimeDetails(title);
        return anime;
    }

    @PostMapping("/add_anime")
    public void addAnime(@RequestBody AnimeEntity anime, String username) throws AnimeAlreadyExistException {
        animeService.addAnime(anime, username);
    }
}
