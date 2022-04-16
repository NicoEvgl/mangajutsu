package com.mangajutsu.api.services;

import java.util.List;

import com.mangajutsu.api.dao.entities.ReviewEntity;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;

import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    void addAnimeReview(ReviewEntity review, String username, String title) throws ResourceNotFoundException;

    void addMovieReview(ReviewEntity review, String username, String title) throws ResourceNotFoundException;

    List<ReviewEntity> getAnimeReviews(String title);

    List<ReviewEntity> getMovieReviews(String title);

    ReviewEntity getReviewDetails(Integer id);

    void updateReview(ReviewEntity review, Integer id) throws ResourceNotFoundException;

    void deleteReview(Integer id);
}
