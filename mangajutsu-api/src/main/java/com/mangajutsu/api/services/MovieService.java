package com.mangajutsu.api.services;

import java.util.List;

import com.mangajutsu.api.dao.entities.MovieEntity;

import org.springframework.stereotype.Service;

@Service
public interface MovieService {
    List<MovieEntity> getMovieList();

    MovieEntity getMovieDetails(String title);
}
