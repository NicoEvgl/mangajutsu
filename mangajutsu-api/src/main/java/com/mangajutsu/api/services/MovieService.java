package com.mangajutsu.api.services;

import java.util.List;

import com.mangajutsu.api.dao.entities.MovieEntity;
import com.mangajutsu.api.exceptions.ResourceAlreadyExistException;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;

import org.springframework.stereotype.Service;

@Service
public interface MovieService {
    List<MovieEntity> getMovieList();

    List<MovieEntity> getTopMovieList();

    MovieEntity getMovieDetails(String title);

    List<MovieEntity> getUserMovies(String username);

    void addMovie(MovieEntity movie, String username) throws ResourceAlreadyExistException;

    boolean checkIfMovieExist(String title);

    void updateMovie(MovieEntity movie, String title) throws ResourceNotFoundException;

    void deleteMovie(String title);
}
