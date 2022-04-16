package com.mangajutsu.webclient.controllers.reviews;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import feign.FeignException;

@Controller
@RequestMapping("/review/manga")
public class MangaReviewController {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/{title}/add-review/")
    public String addReview(@PathVariable String title, final Model model) {
        List<ReviewModel> mangaReviews = mangajutsuProxy.getMangaReviews(title);
        List<String> usersReviewed = new ArrayList<>();
        UserPrincipal userInSession = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        for (ReviewModel mangaReview : mangaReviews) {
            usersReviewed.add(mangaReview.getUser().getUsername());
        }
        for (String userReviewed : usersReviewed) {
            if (userInSession.getUsername().equals(userReviewed)) {
                return "errors/access_denied";
            }
        }
        model.addAttribute("review", new ReviewModel());
        return "review/manga/add_review";
    }

    @PostMapping("/{title}/add-review/")
    public String addReview(@PathVariable String title, @Valid @ModelAttribute("review") ReviewModel review,
            BindingResult bindingResult, RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("review", review);
            return "review/manga/add_review";
        }
        UserPrincipal userInSession = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try {
            mangajutsuProxy.addMangaReview(review, userInSession.getUsername(), title);
        } catch (FeignException e) {
            model.addAttribute("error",
                    messageSource.getMessage("error.add-review", null, LocaleContextHolder.getLocale()));
            model.addAttribute("review", review);
            return "review/manga/add_review";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("add-review.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("title", title);
        model.addAttribute("review", review);

        return "redirect:/manga/manga-details/{title}";
    }

    @GetMapping("/{title}/update-review/{id}")
    public String updateReview(@PathVariable Integer id, final Model model) {
        ReviewModel review = mangajutsuProxy.getReviewDetails(id);
        model.addAttribute("review", review);
        return "review/manga/update_review";
    }

    @PostMapping("/{title}/update-review/{id}")
    public String updateReview(@PathVariable Integer id, @Valid @ModelAttribute("review") ReviewModel review,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("review", review);
            return "review/manga/update_review";
        }
        try {
            mangajutsuProxy.updateReview(review, id);
        } catch (FeignException e) {
            model.addAttribute("error",
                    messageSource.getMessage("error.update-review", null, LocaleContextHolder.getLocale()));
            model.addAttribute("review", review);
            return "review/manga/update_review";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("update-review.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("review", review);

        return "redirect:/manga/manga-details/{title}";
    }

    @GetMapping("/{title}/delete-review/{id}")
    public String deleteReview(@PathVariable Integer id, RedirectAttributes redirectAttributes, final Model model) {
        try {
            mangajutsuProxy.deleteReview(id);
        } catch (FeignException e) {
            model.addAttribute("error",
                    messageSource.getMessage("error.delete-review", null, LocaleContextHolder.getLocale()));
            model.addAttribute("review", mangajutsuProxy.getReviewDetails(id));
            return "review/manga/add_review";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("delete-review.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("review", mangajutsuProxy.getReviewDetails(id));

        return "redirect:/manga/manga-details/{title}";
    }

}
