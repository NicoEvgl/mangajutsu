package com.mangajutsu.api.controllers;

import java.util.List;

import com.mangajutsu.api.dao.entities.AnimeEntity;
import com.mangajutsu.api.exceptions.ResourceAlreadyExistException;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;
import com.mangajutsu.api.services.AnimeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/anime")
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    @GetMapping("/anime-list")
    public List<AnimeEntity> getAnimeList() {
        List<AnimeEntity> animes = animeService.getAnimeList();
        return animes;
    }

    @GetMapping("/top-anime-list")
    public List<AnimeEntity> getTopAnimeList() {
        List<AnimeEntity> animes = animeService.getTopAnimeList();
        return animes;
    }

    @GetMapping("/anime-details/{title}")
    public AnimeEntity getAnimeDetails(@PathVariable String title) {
        AnimeEntity anime = animeService.getAnimeDetails(title);
        return anime;
    }

    @GetMapping("/user-animes/{username}")
    public List<AnimeEntity> getUserAnimes(@PathVariable String username) {
        List<AnimeEntity> animes = animeService.getUserAnimes(username);
        return animes;
    }

    @PostMapping("/add-anime")
    public void addAnime(@RequestBody AnimeEntity anime, String username) throws ResourceAlreadyExistException {
        animeService.addAnime(anime, username);
    }

    @PostMapping("/update-anime/{title}")
    public void updateAnime(@RequestBody AnimeEntity anime, @PathVariable String title)
            throws ResourceNotFoundException {
        animeService.updateAnime(anime, title);
    }

    @DeleteMapping("/delete-anime/{title}")
    public void deleteAnime(@PathVariable String title) {
        animeService.deleteAnime(title);
    }
}
