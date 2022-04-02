package com.mangajutsu.api.services;

import java.util.List;

import com.mangajutsu.api.dao.entities.AnimeEntity;
import com.mangajutsu.api.exceptions.AnimeAlreadyExistException;

import org.springframework.stereotype.Service;

@Service
public interface AnimeService {

    List<AnimeEntity> getAnimeList();

    AnimeEntity getAnimeDetails(String title);

    void addAnime(AnimeEntity anime, String username) throws AnimeAlreadyExistException;

    boolean checkIfAnimeExist(String title);
}
