package com.mangajutsu.webclient.controllers;

import javax.validation.Valid;

import com.mangajutsu.webclient.models.ForgotPasswordModel;
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
@RequestMapping("/password")
public class ForgotPasswordController {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/reset")
    public String resetPassword(@ModelAttribute("forgotPassword") ForgotPasswordModel forgotPassword,
            final Model model) {
        model.addAttribute("forgotPassword", forgotPassword);
        return "user/reset_password";
    }

    @PostMapping("/reset")
    public String resetPassword(final ForgotPasswordModel forgotPassword, RedirectAttributes redirectAttributes) {
        try {
            mangajutsuProxy.resetPassword(forgotPassword.getEmail());
        } catch (FeignException e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("error.reset-password", null, LocaleContextHolder.getLocale()));
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("user.forgot-password.success.msg", null, LocaleContextHolder.getLocale()));
        return "redirect:/login";
    }

    @GetMapping("/change")
    public String changePassword(@RequestParam(required = false) String token,
            final RedirectAttributes redirectAttributes, final Model model) {
        if (StringUtils.isEmpty(token)) {
            redirectAttributes.addFlashAttribute("error", messageSource
                    .getMessage("registration.verif.token.missing", null, LocaleContextHolder.getLocale()));
            return "redirect:/password/change";
        }
        ForgotPasswordModel forgotPassword = new ForgotPasswordModel();
        forgotPassword.setToken(token);
        setResetPasswordForm(model, forgotPassword);

        return "user/update_password";
    }

    @PostMapping("/change")
    public String changePassword(final @Valid @ModelAttribute("forgotPassword") ForgotPasswordModel forgotPassword,
            BindingResult bindingResult,
            final Model model, RedirectAttributes redirectAttributes) throws FeignException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("forgotPassword", forgotPassword);
            return "user/update_password";
        }
        try {
            mangajutsuProxy.changePassword(forgotPassword.getPassword(), forgotPassword.getToken());
        } catch (FeignException e) {
            bindingResult.rejectValue("error", messageSource.getMessage("registration.verif.token.invalid", null,
                    LocaleContextHolder.getLocale()));
            return "redirect:/password/change";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("user.update-password.success.msg", null, LocaleContextHolder.getLocale()));
        setResetPasswordForm(model, new ForgotPasswordModel());
        return "redirect:/login";
    }

    private void setResetPasswordForm(final Model model, ForgotPasswordModel forgotPassword) {
        model.addAttribute("forgotPassword", forgotPassword);
    }
}
