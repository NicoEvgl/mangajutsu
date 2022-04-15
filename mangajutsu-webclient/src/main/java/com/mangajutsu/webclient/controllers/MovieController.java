package com.mangajutsu.webclient.controllers;

import java.util.List;

import com.mangajutsu.webclient.models.MovieModel;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @GetMapping("/movie-list")
    public String movieList(final Model model) {
        List<MovieModel> movies = mangajutsuProxy.getMovieList();

        model.addAttribute("movies", movies);
        return "movie/movie_list";
    }

    @GetMapping("/movie-details/{title}")
    public String movieDetails(@PathVariable String title, final Model model) {
        MovieModel movie = mangajutsuProxy.getMovieDetails(title);

        model.addAttribute("movie", movie);
        return "movie/movie_details";
    }
}
