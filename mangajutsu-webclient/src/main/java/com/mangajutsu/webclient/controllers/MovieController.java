package com.mangajutsu.webclient.controllers;

import java.util.List;

import javax.validation.Valid;

import com.mangajutsu.webclient.models.MovieModel;
import com.mangajutsu.webclient.models.UserPrincipal;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import feign.FeignException;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @Autowired
    private MessageSource messageSource;

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

    @GetMapping("/add-movie")
    public String addMovie(final Model model) {
        List<String> types = mangajutsuProxy.getMovieTypes();
        List<String> genres = mangajutsuProxy.getGenres();

        model.addAttribute("types", types);
        model.addAttribute("genres", genres);

        if (!model.containsAttribute("movie")) {
            model.addAttribute("movie", new MovieModel());
        }
        return "movie/add_movie";
    }

    @PostMapping("/add-movie")
    public String addMovie(@Valid @ModelAttribute("movie") MovieModel movie, final BindingResult bindingResult,
            RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.movie",
                    bindingResult);
            redirectAttributes.addFlashAttribute("movie", movie);
            return "redirect:/movie/add-movie";
        }
        UserPrincipal userInSession = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try {
            mangajutsuProxy.addMovie(movie, userInSession.getUsername());
        } catch (FeignException e) {
            bindingResult.rejectValue("title", "movie.title",
                    messageSource.getMessage("error.movie", null, LocaleContextHolder.getLocale()));
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.movie",
                    bindingResult);
            redirectAttributes.addFlashAttribute("movie", movie);
            return "redirect:/movie/add-movie";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("add-movie.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("movie", movie);

        return "redirect:/movie/movie-list";
    }

    @GetMapping("/update-movie/{title}")
    public String updateMovie(@PathVariable String title, final Model model) {
        MovieModel movie = mangajutsuProxy.getMovieDetails(title);
        List<String> types = mangajutsuProxy.getMovieTypes();
        List<String> genres = mangajutsuProxy.getGenres();

        model.addAttribute("types", types);
        model.addAttribute("genres", genres);
        if (!model.containsAttribute("movie")) {
            model.addAttribute("movie", movie);
        }
        return "movie/update_movie";
    }

    @PostMapping("/update-movie/{title}")
    public String updateMovie(@Valid @ModelAttribute("movie") MovieModel movie, final BindingResult bindingResult,
            RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.movie",
                    bindingResult);
            redirectAttributes.addFlashAttribute("movie", movie);
            return "redirect:/movie/update-movie/{title}";
        }
        try {
            mangajutsuProxy.updateMovie(movie, movie.getTitle());
        } catch (FeignException e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("error.update-movie", null, LocaleContextHolder.getLocale()));
            model.addAttribute("movie", movie);
            return "redirect:/movie/update-movie/{title}";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("update-movie.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("movie", movie);

        return "redirect:/movie/movie-details/{title}";
    }
}
