package com.mangajutsu.webclient.controllers;

import javax.validation.Valid;

import com.mangajutsu.webclient.models.ReviewModel;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import feign.FeignException;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("{title}/add-review/")
    public String addReview(@PathVariable String title, final Model model) {
        model.addAttribute("review", new ReviewModel());
        return "review/add_review";
    }

    @PostMapping("{title}/add-review/")
    public String addReview(@PathVariable String title, @Valid ReviewModel review, BindingResult bindingResult,
            final Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("review", review);
            return "review/add_review";
        }
        UserPrincipal userInSession = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try {
            mangajutsuProxy.addReview(review, userInSession.getUsername(), title);
        } catch (FeignException e) {
            model.addAttribute("reviewError",
                    messageSource.getMessage("error.add-review", null, LocaleContextHolder.getLocale()));
            model.addAttribute("review", review);
            return "review/add_review";
        }
        redirectAttributes.addFlashAttribute("animeMsg",
                messageSource.getMessage("add-review.success.msg", null, LocaleContextHolder.getLocale()));

        model.addAttribute("title", title);
        model.addAttribute("review", review);

        return "redirect:/anime/anime-details/{title}";
    }

    @GetMapping("{title}/update-review/{id}")
    public String updateReview(@PathVariable Integer id, final Model model) {
        ReviewModel review = mangajutsuProxy.getReviewDetails(id);
        model.addAttribute("review", review);
        return "review/update_review";
    }

    @PostMapping("{title}/update-review/{id}")
    public String updateReview(@PathVariable Integer id, @Valid ReviewModel review, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("review", review);
            return "review/update_review";
        }
        try {
            mangajutsuProxy.updateReview(review, id);
        } catch (FeignException e) {
            model.addAttribute("reviewError",
                    messageSource.getMessage("error.update-review", null, LocaleContextHolder.getLocale()));
            model.addAttribute("review", review);
            return "review/add_review";
        }
        redirectAttributes.addFlashAttribute("animeMsg",
                messageSource.getMessage("update-review.success.msg", null, LocaleContextHolder.getLocale()));

        model.addAttribute("review", review);

        return "redirect:/anime/anime-details/{title}";
    }

    @GetMapping("{title}/delete-review/{id}")
    public String deleteReview(@PathVariable Integer id, RedirectAttributes redirectAttributes, final Model model) {
        try {
            mangajutsuProxy.deleteReview(id);
        } catch (FeignException e) {
            model.addAttribute("reviewError",
                    messageSource.getMessage("error.delete-review", null, LocaleContextHolder.getLocale()));
            model.addAttribute("review", mangajutsuProxy.getReviewDetails(id));
            return "review/add_review";
        }
        redirectAttributes.addFlashAttribute("animeMsg",
                messageSource.getMessage("delete-review.success.msg", null, LocaleContextHolder.getLocale()));

        model.addAttribute("review", mangajutsuProxy.getFileDetails(id));

        return "redirect:/anime/anime-details/{title}";
    }
}
