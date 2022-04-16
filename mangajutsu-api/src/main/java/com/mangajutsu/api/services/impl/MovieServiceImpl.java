package com.mangajutsu.api.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mangajutsu.api.dao.entities.FileEntity;
import com.mangajutsu.api.dao.entities.MovieEntity;
import com.mangajutsu.api.dao.entities.ReviewEntity;
import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.repositories.MovieRepository;
import com.mangajutsu.api.exceptions.ResourceAlreadyExistException;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;
import com.mangajutsu.api.services.FileService;
import com.mangajutsu.api.services.MovieService;
import com.mangajutsu.api.services.ReviewService;
import com.mangajutsu.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("movieService")
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private FileService fileService;

    @Override
    public List<MovieEntity> getMovieList() {
        List<MovieEntity> movies = movieRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
        return movies;
    }

    @Override
    public MovieEntity getMovieDetails(String title) {
        MovieEntity movie = movieRepository.findByTitle(title);
        if (movie != null) {
            SetMovieRating(movie);
        }
        return movie;
    }

    @Override
    public List<MovieEntity> getUserMovies(String username) {
        List<MovieEntity> movies = movieRepository.findAllByUser_Username(username,
                Sort.by(Sort.Direction.ASC, "title"));
        return movies;
    }

    @Override
    public void addMovie(MovieEntity movie, String username) throws ResourceAlreadyExistException {
        if (checkIfMovieExist(movie.getTitle())) {
            throw new ResourceAlreadyExistException("Movie already exist");
        }
        addUserToMovie(movie, username);
        movieRepository.save(movie);
    }

    @Override
    public boolean checkIfMovieExist(String title) {
        return movieRepository.findByTitle(title) != null ? true : false;
    }

    @Override
    public void updateMovie(MovieEntity movie, String title) throws ResourceNotFoundException {
        MovieEntity editedMovie = movieRepository.findByTitle(title);
        if (editedMovie == null) {
            throw new ResourceNotFoundException("Movie not found for the title : " + title);
        }
        editedMovie.setTitle(movie.getTitle());
        editedMovie.setTitleVo(movie.getTitleVo());
        editedMovie.setOrigin(movie.getOrigin());
        editedMovie.setReleaseDate(movie.getReleaseDate());
        editedMovie.setDuration(movie.getDuration());
        editedMovie.setType(movie.getType());
        editedMovie.setGenre(movie.getGenre());
        editedMovie.setDirector(movie.getDirector());
        editedMovie.setProducer(movie.getProducer());
        editedMovie.setStudios(movie.getStudios());
        editedMovie.setPublisherVod(movie.getPublisherVod());
        editedMovie.setPublisherVodUrl(movie.getPublisherVodUrl());
        editedMovie.setDealerUrl(movie.getDealerUrl());
        editedMovie.setSynopsis(movie.getSynopsis());

        movieRepository.save(editedMovie);
    }

    @Override
    public void deleteMovie(String title) {
        MovieEntity movie = movieRepository.findByTitle(title);
        if (!movie.getReviews().isEmpty()) {
            for (ReviewEntity review : movie.getReviews()) {
                reviewService.deleteReview(review.getId());
            }
        }
        for (FileEntity file : movie.getFiles()) {
            fileService.deleteFile(file.getId());
        }
        movieRepository.delete(movie);
    }

    public void addUserToMovie(MovieEntity movie, String username) {
        UserEntity user = userService.findByUsername(username);
        movie.setUser(user);
        user.getMovies().add(movie);
    }

    public void SetMovieRating(MovieEntity movie) {
        if (!movie.getReviews().isEmpty()) {
            Collection<Float> ratings = new ArrayList<>(movie.getReviews().size());
            for (ReviewEntity review : movie.getReviews()) {
                ratings.add(review.getRating());
            }
            float averageRating = (float) ratings.stream().mapToDouble(Float::intValue).sum() / ratings.size();
            if (averageRating != movie.getRating()) {
                movie.setRating(averageRating);
                movieRepository.save(movie);
            }
        } else if (movie.getReviews().isEmpty() && movie.getRating() != 0) {
            movie.setRating(0);
            movieRepository.save(movie);
        }
    }
}
