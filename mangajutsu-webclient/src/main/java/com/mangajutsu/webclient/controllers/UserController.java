package com.mangajutsu.webclient.controllers;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/register")
    public String register(final Model model) {
        model.addAttribute("user", new UserModel());
        return "register";
    }

    @PostMapping("/register")
    public String userRegistration(final @Valid @ModelAttribute("user") UserModel user,
            final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        try {
            mangajutsuProxy.userRegistration(user);
        } catch (FeignException e) {
            bindingResult.rejectValue("username", "user.username", "Ce nom d'utilisateur existe déjà");
            model.addAttribute("user", user);
            return "register";
        }
        model.addAttribute("user", user);
        return "/login";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("user") UserModel user, Model model) {
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
