package com.mangajutsu.api.services;

import java.util.Set;

import com.mangajutsu.api.dao.entities.ReviewEntity;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;

import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    void create(ReviewEntity review, String username, String title) throws ResourceNotFoundException;

    Set<ReviewEntity> getAnimeReviews(String title);
}
