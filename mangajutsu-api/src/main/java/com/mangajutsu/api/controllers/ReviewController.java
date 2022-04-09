package com.mangajutsu.api.controllers;

import java.util.Set;

import com.mangajutsu.api.dao.entities.ReviewEntity;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;
import com.mangajutsu.api.services.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("{title}/add-review")
    public void addReview(@RequestBody ReviewEntity review, String username, @PathVariable String title)
            throws ResourceNotFoundException {
        reviewService.create(review, username, title);
    }

    @GetMapping("{title}/anime-reviews")
    public Set<ReviewEntity> getAnimeReviews(@PathVariable String title) {
        Set<ReviewEntity> reviews = reviewService.getAnimeReviews(title);
        return reviews;
    }
}
