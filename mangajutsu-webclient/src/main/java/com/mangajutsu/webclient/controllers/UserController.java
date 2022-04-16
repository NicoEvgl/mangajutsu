package com.mangajutsu.webclient.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import feign.FeignException.FeignClientException;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.mangajutsu.webclient.models.AnimeModel;
import com.mangajutsu.webclient.models.ForgotPasswordModel;
import com.mangajutsu.webclient.models.MangaModel;
import com.mangajutsu.webclient.models.MovieModel;
import com.mangajutsu.webclient.models.UserModel;
import com.mangajutsu.webclient.models.UserPrincipal;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;

@Controller
public class UserController {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", defaultValue = "false") boolean loginError,
            @RequestParam(value = "invalid-session", defaultValue = "false") boolean invalidSession,
            @ModelAttribute("user") UserModel user, final Model model, HttpSession httpSession) {
        String username = getUsername(httpSession);
        if (loginError) {
            if (StringUtils.isNotEmpty(username) && mangajutsuProxy.loginDisabled(username)) {
                model.addAttribute("accountLocked", Boolean.TRUE);
                model.addAttribute("forgotPassword", new ForgotPasswordModel());
                model.addAttribute("user", user);
                return "login";
            }
        }
        model.addAttribute("forgotPassword", new ForgotPasswordModel());
        model.addAttribute("accountLocked", Boolean.FALSE);
        model.addAttribute("user", user);
        return "login";
    }

    @GetMapping("/personal-space")
    public String personalSpace(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userInSession = (UserPrincipal) authentication.getPrincipal();

        List<MangaModel> mangas = mangajutsuProxy.getUserMangas(userInSession.getUsername());
        for (MangaModel manga : mangas) {
            manga.setFiles(mangajutsuProxy.getMangaFiles(manga.getTitle()));
        }

        List<AnimeModel> animes = mangajutsuProxy.getUserAnimes(userInSession.getUsername());
        for (AnimeModel anime : animes) {
            anime.setFiles(mangajutsuProxy.getAnimeFiles(anime.getTitle()));
        }

        List<MovieModel> movies = mangajutsuProxy.getUserMovies(userInSession.getUsername());
        for (MovieModel movie : movies) {
            movie.setFiles(mangajutsuProxy.getMovieFiles(movie.getTitle()));
        }

        model.addAttribute("mangas", mangas);
        model.addAttribute("animes", animes);
        model.addAttribute("movies", movies);
        model.addAttribute("userInSession", userInSession);

        return "user/personal_space";
    }

    final String getUsername(HttpSession httpSession) {
        final String username = (String) httpSession.getAttribute("LAST_USERNAME");
        if (StringUtils.isNotEmpty(username)) {
            httpSession.removeAttribute("LAST_USERNAME");
        }
        return username;
    }

    @GetMapping("/update-user/{id}")
    public String updateUser(@PathVariable Integer id, final Model model) {
        UserPrincipal userInSession = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        model.addAttribute("userInSession", userInSession);
        return "user/update_user";
    }

    @PostMapping("/update-user/{id}")
    public String updateUser(@PathVariable Integer id,
            @Valid @ModelAttribute("userInSession") UserPrincipal userInSession, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userInSession", userInSession);
            return "user/update_user";
        }
        try {
            mangajutsuProxy.updateUser(userInSession.getUser(), id);
        } catch (FeignClientException e) {
            model.addAttribute("error",
                    messageSource.getMessage("error.update-user", null, LocaleContextHolder.getLocale()));
            model.addAttribute("userInSession", userInSession);
            return "user/update_user";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("update-user.success.msg", null, LocaleContextHolder.getLocale()));

        model.addAttribute("userInSession", userInSession);

        return "redirect:/personal-space";
    }

    @GetMapping("/user-profile/{id}")
    public String userProfile(@PathVariable Integer id, final Model model) {
        UserModel user = mangajutsuProxy.getUserDetails(id);
        model.addAttribute("user", user);
        return "user/user_profile";
    }
}
