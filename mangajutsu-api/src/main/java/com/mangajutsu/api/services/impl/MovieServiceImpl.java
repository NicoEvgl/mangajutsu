package com.mangajutsu.api.services.impl;

import java.util.List;

import com.mangajutsu.api.dao.entities.MovieEntity;
import com.mangajutsu.api.dao.repositories.MovieRepository;
import com.mangajutsu.api.services.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("movieService")
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<MovieEntity> getMovieList() {
        List<MovieEntity> movies = movieRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
        return movies;
    }

    @Override
    public MovieEntity getMovieDetails(String title) {
        MovieEntity movie = movieRepository.findByTitle(title);
        return movie;
    }
}
