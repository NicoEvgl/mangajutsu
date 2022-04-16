package com.mangajutsu.api.controllers;

import java.util.List;

import com.mangajutsu.api.dao.entities.MangaEntity;
import com.mangajutsu.api.exceptions.ResourceAlreadyExistException;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;
import com.mangajutsu.api.services.MangaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manga")
public class MangaController {

    @Autowired
    private MangaService mangaService;

    @GetMapping("/manga-list")
    public List<MangaEntity> getAnimeList() {
        List<MangaEntity> mangas = mangaService.getMangaList();
        return mangas;
    }

    @GetMapping("/manga-details/{title}")
    public MangaEntity getMangaDetails(@PathVariable String title) {
        MangaEntity manga = mangaService.getMangaDetails(title);
        return manga;
    }

    @GetMapping("/user-mangas/{username}")
    public List<MangaEntity> getUserMangas(@PathVariable String username) {
        List<MangaEntity> mangas = mangaService.getUserMangas(username);
        return mangas;
    }

    @PostMapping("/add-manga")
    public void addManga(@RequestBody MangaEntity manga, String username) throws ResourceAlreadyExistException {
        mangaService.addManga(manga, username);
    }

    @PostMapping("/update-manga/{title}")
    public void updateManga(@RequestBody MangaEntity manga, @PathVariable String title)
            throws ResourceNotFoundException {
        mangaService.updateManga(manga, title);
    }

    @DeleteMapping("/delete-manga/{title}")
    public void deleteManga(@PathVariable String title) {
        mangaService.deleteManga(title);
    }
}
