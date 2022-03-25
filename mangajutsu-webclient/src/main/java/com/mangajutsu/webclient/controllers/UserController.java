package com.mangajutsu.webclient.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mangajutsu.webclient.models.UserModel;
import com.mangajutsu.webclient.models.UserPrincipal;

@Controller
public class UserController {

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

    @GetMapping("/personal-space")
    public String personalSpace(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal loggedInUser = (UserPrincipal) authentication.getPrincipal();

        model.addAttribute("loggedInUser", loggedInUser);
        return "personal-space";
    }
}
