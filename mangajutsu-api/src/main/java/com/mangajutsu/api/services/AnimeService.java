package com.mangajutsu.api.services;

import java.util.List;

import com.mangajutsu.api.dao.entities.AnimeEntity;
import com.mangajutsu.api.exceptions.ResourceAlreadyExistException;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;

import org.springframework.stereotype.Service;

@Service
public interface AnimeService {

    List<AnimeEntity> getAnimeList();

    AnimeEntity getAnimeDetails(String title);

    List<AnimeEntity> getUserAnimes(String username);

    void addAnime(AnimeEntity anime, String username) throws ResourceAlreadyExistException;

    boolean checkIfAnimeExist(String title);

    void updateAnime(AnimeEntity anime, String title) throws ResourceNotFoundException;

    void deleteAnime(String title);
}
