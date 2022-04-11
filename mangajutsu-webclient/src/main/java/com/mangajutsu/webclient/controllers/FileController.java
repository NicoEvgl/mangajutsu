package com.mangajutsu.webclient.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import feign.FeignException;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/{title}/add-file")
    public String addFile(@PathVariable String title, final Model model) {
        List<String> fileTypes = mangajutsuProxy.getFileTypes();

        model.addAttribute("fileTypes", fileTypes);
        if (!model.containsAttribute("file")) {
            model.addAttribute("file", new FileModel());
        }
        return "file/upload_file";
    }

    @PostMapping("/{title}/add-file")
    public String addFile(@PathVariable String title, @Valid @ModelAttribute("file") FileModel file,
            BindingResult bindingResult, RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.file",
                    bindingResult);
            redirectAttributes.addFlashAttribute("file", file);
            return "redirect:/file/{title}/add-file";
        }
        try {
            mangajutsuProxy.uploadFile(file, title);
        } catch (FeignException e) {
            model.addAttribute("error",
                    messageSource.getMessage("error.upload-file", null, LocaleContextHolder.getLocale()));
            model.addAttribute("file", file);
            return "file/upload_file";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("upload-file.success.msg", null, LocaleContextHolder.getLocale()));

        model.addAttribute("title", title);
        model.addAttribute("file", file);

        return "redirect:/anime/anime-details/{title}";
    }

    @GetMapping("/{title}/file-list")
    public String filesList(@PathVariable String title, final Model model) {
        List<FileModel> files = mangajutsuProxy.getAnimeFiles(title);
        model.addAttribute("files", files);
        return "file/file_list";
    }

    @GetMapping("{title}/update-file/{id}")
    public String updateFile(@PathVariable Integer id, final Model model) {
        FileModel file = mangajutsuProxy.getFileDetails(id);
        List<String> fileTypes = mangajutsuProxy.getFileTypes();

        model.addAttribute("fileTypes", fileTypes);
        model.addAttribute("file", file);
        return "file/update_file";
    }

    @PostMapping("{title}/update-file/{id}")
    public String updateFile(@PathVariable Integer id, @Valid @ModelAttribute("file") FileModel file,
            BindingResult bindingResult, RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("file", file);
            return "file/update_file";
        }
        try {
            mangajutsuProxy.updateFile(file, id);
        } catch (FeignException e) {
            model.addAttribute("error",
                    messageSource.getMessage("error.update-file", null, LocaleContextHolder.getLocale()));
            model.addAttribute("file", file);
            return "file/update_file";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("update-file.success.msg", null, LocaleContextHolder.getLocale()));

        model.addAttribute("file", file);

        return "redirect:/file/{title}/file-list";
    }

    @GetMapping("{title}/delete-file/{id}")
    public String deleteFile(@PathVariable Integer id, final Model model, RedirectAttributes redirectAttributes) {
        try {
            mangajutsuProxy.deleteFile(id);
        } catch (FeignException e) {
            model.addAttribute("error",
                    messageSource.getMessage("error.delete-file", null, LocaleContextHolder.getLocale()));
            model.addAttribute("file", mangajutsuProxy.getFileDetails(id));
            return "file/upload_file";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("delete-file.success.msg", null, LocaleContextHolder.getLocale()));
        model.addAttribute("file", mangajutsuProxy.getFileDetails(id));

        return "redirect:/file/{title}/file-list";
    }
}
