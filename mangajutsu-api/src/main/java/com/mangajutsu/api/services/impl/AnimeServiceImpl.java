package com.mangajutsu.api.services.impl;

import java.util.List;

import com.mangajutsu.api.dao.entities.AnimeEntity;
import com.mangajutsu.api.dao.repositories.AnimeRepository;
import com.mangajutsu.api.services.AnimeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("animeService")
public class AnimeServiceImpl implements AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    @Override
    public List<AnimeEntity> getAnimeList() {
        List<AnimeEntity> animes = animeRepository.findAll();
        return animes;
    }

    @Override
    public AnimeEntity getAnimeDetails(String title) {
        AnimeEntity anime = animeRepository.findByTitle(title);
        return anime;
    }
}
