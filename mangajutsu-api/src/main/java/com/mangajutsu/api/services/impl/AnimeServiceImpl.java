package com.mangajutsu.api.services.impl;

import java.util.List;

import com.mangajutsu.api.dao.entities.AnimeEntity;
import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.repositories.AnimeRepository;
import com.mangajutsu.api.exceptions.AnimeAlreadyExistException;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;
import com.mangajutsu.api.services.AnimeService;
import com.mangajutsu.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("animeService")
public class AnimeServiceImpl implements AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private UserService userService;

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

    @Override
    public void addAnime(AnimeEntity anime, String username) throws AnimeAlreadyExistException {
        if (checkIfAnimeExist(anime.getTitle())) {
            throw new AnimeAlreadyExistException("Anime already exist");
        }
        addUserToAnime(anime, username);
        animeRepository.save(anime);
    }

    @Override
    public boolean checkIfAnimeExist(String title) {
        return animeRepository.findByTitle(title) != null ? true : false;
    }

    public void addUserToAnime(AnimeEntity anime, String username) {
        UserEntity user = userService.findByUsername(username);
        anime.setUser(user);
        user.getAnims().add(anime);
    }

    @Override
    public void updateAnime(AnimeEntity anime, String title) throws ResourceNotFoundException {
        AnimeEntity editedAnime = animeRepository.findByTitle(title);
        if (editedAnime == null) {
            throw new ResourceNotFoundException("Anime not found for the title : " + title);
        }
        editedAnime.setTitle(anime.getTitle());
        editedAnime.setTitleVo(anime.getTitleVo());
        editedAnime.setOrigin(anime.getOrigin());
        editedAnime.setStatus(anime.getStatus());
        editedAnime.setReleaseDate(anime.getReleaseDate());
        editedAnime.setEndDate(anime.getEndDate());
        editedAnime.setNbEpisodes(anime.getNbEpisodes());
        editedAnime.setDuration(anime.getDuration());
        editedAnime.setType(anime.getType());
        editedAnime.setMangaka(anime.getMangaka());
        editedAnime.setProducer(anime.getProducer());
        editedAnime.setStudios(anime.getStudios());
        editedAnime.setSynopsis(anime.getSynopsis());

        animeRepository.save(editedAnime);
    }
}
