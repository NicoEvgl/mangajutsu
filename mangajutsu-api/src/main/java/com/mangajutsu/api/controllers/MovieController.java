package com.mangajutsu.api.controllers;

import java.util.List;

import com.mangajutsu.api.dao.entities.MovieEntity;
import com.mangajutsu.api.exceptions.ResourceAlreadyExistException;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;
import com.mangajutsu.api.services.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movie-list")
    public List<MovieEntity> getMovieList() {
        List<MovieEntity> movies = movieService.getMovieList();
        return movies;
    }

    @GetMapping("/top-movie-list")
    public List<MovieEntity> getTopMovieList() {
        List<MovieEntity> movies = movieService.getTopMovieList();
        return movies;
    }

    @GetMapping("/last-movie-list")
    public List<MovieEntity> getLastMovieList() {
        List<MovieEntity> movies = movieService.getLastMovieList();
        return movies;
    }

    @GetMapping("/movie-details/{title}")
    public MovieEntity getMovieDetails(@PathVariable String title) {
        MovieEntity movie = movieService.getMovieDetails(title);
        return movie;
    }

    @GetMapping("/user-movies/{username}")
    public List<MovieEntity> getUserMovies(@PathVariable String username) {
        List<MovieEntity> movies = movieService.getUserMovies(username);
        return movies;
    }

    @PostMapping("/add-movie")
    public void addMovie(@RequestBody MovieEntity movie, String username) throws ResourceAlreadyExistException {
        movieService.addMovie(movie, username);
    }

    @PostMapping("/update-movie/{title}")
    public void updateMovie(@RequestBody MovieEntity movie, @PathVariable String title)
            throws ResourceNotFoundException {
        movieService.updateMovie(movie, title);
    }

    @DeleteMapping("/delete-movie/{title}")
    public void deleteMovie(@PathVariable String title) {
        movieService.deleteMovie(title);
    }
}
