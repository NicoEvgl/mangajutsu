package com.mangajutsu.api.services.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import com.mangajutsu.api.dao.entities.AnimeEntity;
import com.mangajutsu.api.dao.entities.MangaEntity;
import com.mangajutsu.api.dao.entities.MovieEntity;
import com.mangajutsu.api.dao.entities.ReviewEntity;
import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.repositories.AnimeRepository;
import com.mangajutsu.api.dao.repositories.MangaRepository;
import com.mangajutsu.api.dao.repositories.MovieRepository;
import com.mangajutsu.api.dao.repositories.ReviewRepository;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;
import com.mangajutsu.api.services.ReviewService;
import com.mangajutsu.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserService userService;

    @Override
    public void addMangaReview(ReviewEntity review, String username, String title) throws ResourceNotFoundException {
        if (Objects.isNull(review) || title == null || username == null) {
            throw new ResourceNotFoundException("Ressource not found");
        }
        addMangaToReview(review, title);
        addUserToReview(review, username);

        reviewRepository.save(review);
    }

    @Override
    public void addAnimeReview(ReviewEntity review, String username, String title) throws ResourceNotFoundException {
        if (Objects.isNull(review) || title == null || username == null) {
            throw new ResourceNotFoundException("Ressource not found");
        }
        addAnimeToReview(review, title);
        addUserToReview(review, username);

        reviewRepository.save(review);
    }

    @Override
    public void addMovieReview(ReviewEntity review, String username, String title) throws ResourceNotFoundException {
        if (Objects.isNull(review) || title == null || username == null) {
            throw new ResourceNotFoundException("Ressource not found");
        }
        addMovieToReview(review, title);
        addUserToReview(review, username);

        reviewRepository.save(review);
    }

    @Override
    public List<ReviewEntity> getMangaReviews(String title) {
        List<ReviewEntity> reviews = reviewRepository.findAllByManga_Title(title,
                Sort.by(Sort.Direction.DESC, "releaseDate"));
        return reviews;
    }

    @Override
    public List<ReviewEntity> getAnimeReviews(String title) {
        List<ReviewEntity> reviews = reviewRepository.findAllByAnime_Title(title,
                Sort.by(Sort.Direction.DESC, "releaseDate"));
        return reviews;
    }

    @Override
    public List<ReviewEntity> getMovieReviews(String title) {
        List<ReviewEntity> reviews = reviewRepository.findAllByMovie_Title(title,
                Sort.by(Sort.Direction.DESC, "releaseDate"));
        return reviews;
    }

    @Override
    public ReviewEntity getReviewDetails(Integer id) {
        ReviewEntity review = reviewRepository.findById(id).orElse(null);
        return review;
    }

    @Override
    public void updateReview(ReviewEntity review, Integer id) throws ResourceNotFoundException {
        ReviewEntity editedReview = reviewRepository.findById(id).orElse(null);
        if (editedReview == null) {
            throw new ResourceNotFoundException("File not found for the id : " + id);
        }
        editedReview.setRating(review.getRating());
        editedReview.setContent(review.getContent());
        editedReview.setReleaseDate(new Timestamp(System.currentTimeMillis()));

        reviewRepository.save(editedReview);
    }

    @Override
    public void deleteReview(Integer id) {
        ReviewEntity review = reviewRepository.findById(id).orElse(null);
        reviewRepository.delete(review);
    }

    public void addUserToReview(ReviewEntity review, String username) {
        UserEntity user = userService.findByUsername(username);
        review.setUser(user);
        user.getReviews().add(review);
    }

    public void addMangaToReview(ReviewEntity review, String title) {
        MangaEntity manga = mangaRepository.findByTitle(title);
        review.setManga(manga);
        manga.getReviews().add(review);
    }

    public void addAnimeToReview(ReviewEntity review, String title) {
        AnimeEntity anime = animeRepository.findByTitle(title);
        review.setAnime(anime);
        anime.getReviews().add(review);
    }

    public void addMovieToReview(ReviewEntity review, String title) {
        MovieEntity movie = movieRepository.findByTitle(title);
        review.setMovie(movie);
        movie.getReviews().add(review);
    }
}
