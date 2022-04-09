package com.mangajutsu.api.services.impl;

import java.util.Set;

import com.mangajutsu.api.dao.entities.AnimeEntity;
import com.mangajutsu.api.dao.entities.ReviewEntity;
import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.repositories.AnimeRepository;
import com.mangajutsu.api.dao.repositories.ReviewRepository;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;
import com.mangajutsu.api.services.ReviewService;
import com.mangajutsu.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Override
    public void create(ReviewEntity review, String username, String title) throws ResourceNotFoundException {
        if (review == null || title == null) {
            throw new ResourceNotFoundException("Review or Anime not found");
        }
        addAnimeToReview(review, title);
        addUserToReview(review, username);

        reviewRepository.save(review);
    }

    @Override
    public Set<ReviewEntity> getAnimeReviews(String title) {
        Set<ReviewEntity> reviews = reviewRepository.findAllByAnime_Title(title);
        return reviews;
    }

    public void addUserToReview(ReviewEntity review, String username) {
        UserEntity user = userService.findByUsername(username);
        review.setUser(user);
        user.getReviews().add(review);
    }

    public void addAnimeToReview(ReviewEntity review, String title) {
        AnimeEntity anime = animeRepository.findByTitle(title);
        review.setAnime(anime);
        anime.getReviews().add(review);
    }
}
