package com.mangajutsu.webclient.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import com.mangajutsu.webclient.models.ForgotPasswordModel;
import com.mangajutsu.webclient.models.UserModel;
import com.mangajutsu.webclient.models.UserPrincipal;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;

@Controller
public class UserController {

    @Autowired
    MangajutsuProxy mangajutsuProxy;

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
        UserPrincipal loggedInUser = (UserPrincipal) authentication.getPrincipal();

        model.addAttribute("loggedInUser", loggedInUser);
        return "user/personal_space";
    }

    final String getUsername(HttpSession httpSession) {
        final String username = (String) httpSession.getAttribute("LAST_USERNAME");
        if (StringUtils.isNotEmpty(username)) {
            httpSession.removeAttribute("LAST_USERNAME");
        }
        return username;
    }
}
