package com.mangajutsu.webclient.controllers;

import javax.validation.Valid;

import com.mangajutsu.webclient.models.FileModel;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
@RequestMapping("/file")
public class FileController {

    @Autowired
    MangajutsuProxy mangajutsuProxy;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/{title}/add-file")
    public String addFile(@PathVariable String title, final Model model) {
        model.addAttribute("file", new FileModel());
        return "upload_file";
    }

    @PostMapping("/{title}/add-file")
    public String addFile(@PathVariable String title, @Valid FileModel file, BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("file", file);
            return "upload_file";
        }
        try {
            mangajutsuProxy.uploadFile(file, title);
        } catch (FeignException e) {
            model.addAttribute("fileError",
                    messageSource.getMessage("error.upload-file", null,
                            LocaleContextHolder.getLocale()));
            model.addAttribute("file", file);
            return "/upload_file";
        }
        redirectAttributes.addFlashAttribute("animeMsg",
                messageSource.getMessage("upload-file.success.msg", null,
                        LocaleContextHolder.getLocale()));
        model.addAttribute("title", title);
        model.addAttribute("file", file);

        return "redirect:/anime/anime-details/{title}";
    }
}
