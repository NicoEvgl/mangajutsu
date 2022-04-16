package com.mangajutsu.webclient.controllers;

import java.util.List;

import com.mangajutsu.webclient.models.AnimeModel;
import com.mangajutsu.webclient.models.FileModel;
import com.mangajutsu.webclient.models.MangaModel;
import com.mangajutsu.webclient.models.MovieModel;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    MangajutsuProxy mangajutsuProxy;

    @GetMapping({ "/index", "/" })
    public String Home(final Model model) {
        List<MangaModel> mangas = mangajutsuProxy.getTopMangaList();
        List<AnimeModel> animes = mangajutsuProxy.getTopAnimeList();
        List<MovieModel> movies = mangajutsuProxy.getTopMovieList();

        for (MangaModel manga : mangas) {
            List<FileModel> files = mangajutsuProxy.getMangaFiles(manga.getTitle());
            manga.setFiles(files);
        }
        for (AnimeModel anime : animes) {
            List<FileModel> files = mangajutsuProxy.getAnimeFiles(anime.getTitle());
            anime.setFiles(files);
        }
        for (MovieModel movie : movies) {
            List<FileModel> files = mangajutsuProxy.getMovieFiles(movie.getTitle());
            movie.setFiles(files);
        }

        mangajutsuProxy.deleteUnverifiedAccount();

        model.addAttribute("mangas", mangas);
        model.addAttribute("animes", animes);
        model.addAttribute("movies", movies);
        return "index";
    }
}
