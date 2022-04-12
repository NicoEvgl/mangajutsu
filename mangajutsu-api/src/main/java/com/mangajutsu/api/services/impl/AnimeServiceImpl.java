package com.mangajutsu.api.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mangajutsu.api.dao.entities.AnimeEntity;
import com.mangajutsu.api.dao.entities.FileEntity;
import com.mangajutsu.api.dao.entities.ReviewEntity;
import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.repositories.AnimeRepository;
import com.mangajutsu.api.exceptions.AnimeAlreadyExistException;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;
import com.mangajutsu.api.services.AnimeService;
import com.mangajutsu.api.services.FileService;
import com.mangajutsu.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("animeService")
public class AnimeServiceImpl implements AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Override
    public List<AnimeEntity> getAnimeList() {
        List<AnimeEntity> animes = animeRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
        for (AnimeEntity anime : animes) {
            List<FileEntity> files = fileService.getAnimeFiles(anime.getTitle());
            anime.setFiles(files);
        }
        return animes;
    }

    @Override
    public AnimeEntity getAnimeDetails(String title) {
        AnimeEntity anime = animeRepository.findByTitle(title);
        SetAnimeRating(anime);
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
        editedAnime.setGenre(anime.getGenre());
        editedAnime.setMangaka(anime.getMangaka());
        editedAnime.setProducer(anime.getProducer());
        editedAnime.setStudios(anime.getStudios());
        editedAnime.setPublisher(anime.getPublisher());
        editedAnime.setPublisherUrl(anime.getPublisherUrl());
        editedAnime.setPublisherVod(anime.getPublisherVod());
        editedAnime.setPublisherVodUrl(anime.getPublisherVodUrl());
        editedAnime.setSynopsis(anime.getSynopsis());

        animeRepository.save(editedAnime);
    }

    public void addUserToAnime(AnimeEntity anime, String username) {
        UserEntity user = userService.findByUsername(username);
        anime.setUser(user);
        user.getAnimes().add(anime);
    }

    public void SetAnimeRating(AnimeEntity anime) {
        if (!anime.getReviews().isEmpty()) {
            Collection<Float> ratings = new ArrayList<>(anime.getReviews().size());
            for (ReviewEntity review : anime.getReviews()) {
                ratings.add(review.getRating());
            }
            float averageRating = (float) ratings.stream().mapToDouble(Float::intValue).sum() / ratings.size();
            if (averageRating != anime.getRating()) {
                anime.setRating(averageRating);
                animeRepository.save(anime);
            }
        } else if (anime.getReviews().isEmpty() && anime.getRating() != 0) {
            anime.setRating(0);
            animeRepository.save(anime);
        }
    }
}
