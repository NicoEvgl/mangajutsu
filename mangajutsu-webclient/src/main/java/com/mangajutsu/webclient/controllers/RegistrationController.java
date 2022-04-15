package com.mangajutsu.webclient.controllers;

import javax.validation.Valid;

import com.mangajutsu.webclient.models.UserModel;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import feign.FeignException;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String register(@ModelAttribute("user") UserModel user, final Model model) {
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping
    public String userRegistration(final @Valid @ModelAttribute("user") UserModel user,
            final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        try {
            mangajutsuProxy.userRegistration(user);
        } catch (FeignException e) {
            bindingResult.rejectValue("username", "user.username",
                    messageSource.getMessage("error.register", null, LocaleContextHolder.getLocale()));
            model.addAttribute("user", user);
            return "register";
        }
        model.addAttribute("success",
                messageSource.getMessage("registration.verif.email.success.msg", null,
                        LocaleContextHolder.getLocale()));
        model.addAttribute("user", user);
        return "login";
    }

    @GetMapping("/verify")
    public String verifyAccount(@RequestParam("token") String token, final Model model,
            RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(token)) {
            redirectAttributes.addFlashAttribute("error", messageSource
                    .getMessage("registration.verif.token.missing", null, LocaleContextHolder.getLocale()));
            return "redirect:/login";
        }
        try {
            mangajutsuProxy.verifyAccount(token);
        } catch (FeignException e) {
            redirectAttributes.addFlashAttribute("error", messageSource
                    .getMessage("registration.verif.token.invalid", null, LocaleContextHolder.getLocale()));
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("registration.verif.success.msg", null, LocaleContextHolder.getLocale()));
        return "redirect:/login";
    }
}
