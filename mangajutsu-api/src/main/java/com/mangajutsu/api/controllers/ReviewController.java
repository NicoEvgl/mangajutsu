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

    @PostMapping("/manga/{title}/add-review")
    public void addMangaReview(@RequestBody ReviewEntity review, String username, @PathVariable String title)
            throws ResourceNotFoundException {
        reviewService.addMangaReview(review, username, title);
    }

    @PostMapping("/anime/{title}/add-review")
    public void addAnimeReview(@RequestBody ReviewEntity review, String username, @PathVariable String title)
            throws ResourceNotFoundException {
        reviewService.addAnimeReview(review, username, title);
    }

    @PostMapping("/movie/{title}/add-review")
    public void addMovieReview(@RequestBody ReviewEntity review, String username, @PathVariable String title)
            throws ResourceNotFoundException {
        reviewService.addMovieReview(review, username, title);
    }

    @GetMapping("/{title}/manga-reviews")
    public List<ReviewEntity> getMangaReviews(@PathVariable String title) {
        List<ReviewEntity> reviews = reviewService.getMangaReviews(title);
        return reviews;
    }

    @GetMapping("/{title}/anime-reviews")
    public List<ReviewEntity> getAnimeReviews(@PathVariable String title) {
        List<ReviewEntity> reviews = reviewService.getAnimeReviews(title);
        return reviews;
    }

    @GetMapping("/{title}/movie-reviews")
    public List<ReviewEntity> getMovieReviews(@PathVariable String title) {
        List<ReviewEntity> reviews = reviewService.getMovieReviews(title);
        return reviews;
    }

    @GetMapping("/review-details/{id}")
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
