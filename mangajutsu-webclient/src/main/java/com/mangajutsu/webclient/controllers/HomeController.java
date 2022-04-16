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
        List<MangaModel> topMangas = mangajutsuProxy.getTopMangaList();
        List<AnimeModel> topAnimes = mangajutsuProxy.getTopAnimeList();
        List<MovieModel> topMovies = mangajutsuProxy.getTopMovieList();
        List<MangaModel> lastMangas = mangajutsuProxy.getLastMangaList();
        List<AnimeModel> lastAnimes = mangajutsuProxy.getLastAnimeList();
        List<MovieModel> lastMovies = mangajutsuProxy.getLastMovieList();

        // Top files //
        for (MangaModel manga : topMangas) {
            List<FileModel> files = mangajutsuProxy.getMangaFiles(manga.getTitle());
            manga.setFiles(files);
        }
        for (AnimeModel anime : topAnimes) {
            List<FileModel> files = mangajutsuProxy.getAnimeFiles(anime.getTitle());
            anime.setFiles(files);
        }
        for (MovieModel movie : topMovies) {
            List<FileModel> files = mangajutsuProxy.getMovieFiles(movie.getTitle());
            movie.setFiles(files);
        }

        // Last Files //
        for (MangaModel manga : lastMangas) {
            List<FileModel> files = mangajutsuProxy.getMangaFiles(manga.getTitle());
            manga.setFiles(files);
        }
        for (AnimeModel anime : lastAnimes) {
            List<FileModel> files = mangajutsuProxy.getAnimeFiles(anime.getTitle());
            anime.setFiles(files);
        }
        for (MovieModel movie : lastMovies) {
            List<FileModel> files = mangajutsuProxy.getMovieFiles(movie.getTitle());
            movie.setFiles(files);
        }

        mangajutsuProxy.deleteUnverifiedAccount();

        model.addAttribute("topMangas", topMangas);
        model.addAttribute("topAnimes", topAnimes);
        model.addAttribute("topMovies", topMovies);
        model.addAttribute("lastMangas", lastMangas);
        model.addAttribute("lastAnimes", lastAnimes);
        model.addAttribute("lastMovies", lastMovies);
        return "index";
    }
}
