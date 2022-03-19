package com.mangajutsu.webclient.controllers;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.mangajutsu.webclient.models.UserModel;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;

@Controller
public class UserController {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @Autowired
    private MessageSource messageSource;

    @GetMapping(value = "/register")
    public String register(@ModelAttribute("user") UserModel user, final Model model) {
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String userRegistration(final @Valid @ModelAttribute("user") UserModel user,
            final BindingResult bindingResult, final Model model) {
        String errorMessage = messageSource.getMessage("error.register", null, LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        try {
            mangajutsuProxy.userRegistration(user);
        } catch (FeignException e) {
            bindingResult.rejectValue("username", "user.username", errorMessage);
            model.addAttribute("user", user);
            return "register";
        }
        model.addAttribute("user", user);
        return "/login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", defaultValue = "false") boolean loginError,
            @ModelAttribute("user") UserModel user,
            Model model) {
        if (loginError) {
            model.addAttribute("user", user);
            return "login";
        }
        model.addAttribute("user", user);
        return "login";
    }

    @GetMapping("/personalSpace")
    public String personalSpace(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel loggedInUser = (UserModel) authentication.getPrincipal();

        model.addAttribute("loggedInUser", loggedInUser);
        return "personalSpace";
    }
}
