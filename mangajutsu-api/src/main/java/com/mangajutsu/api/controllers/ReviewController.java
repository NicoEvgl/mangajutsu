package com.mangajutsu.api.controllers;

import java.util.List;

import com.mangajutsu.api.dao.entities.ReviewEntity;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;
import com.mangajutsu.api.services.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public List<ReviewEntity> getAnimeReviews(@PathVariable String title) {
        List<ReviewEntity> reviews = reviewService.getAnimeReviews(title);
        return reviews;
    }

    @GetMapping("review-details/{id}")
    public ReviewEntity getFileDetails(@PathVariable Integer id) {
        ReviewEntity review = reviewService.getReviewDetails(id);
        return review;
    }

    @PostMapping("/update-review/{id}")
    public void updateReview(@RequestBody ReviewEntity review, @PathVariable Integer id)
            throws ResourceNotFoundException {
        reviewService.updateReview(review, id);
    }

    @DeleteMapping("/delete-review/{id}")
    public void deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
    }
}
