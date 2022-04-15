package com.mangajutsu.webclient.controllers.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import com.mangajutsu.webclient.exceptions.FileStorageException;
import com.mangajutsu.webclient.models.FileModel;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;
import com.mangajutsu.webclient.services.FileStrorageService;
import com.mangajutsu.webclient.utils.UploadFileProperties;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import feign.FeignException;

@Controller
@RequestMapping("/file/movie")
public class MovieFileController {
    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @Autowired
    private FileStrorageService fileStrorageService;

    @Autowired
    private UploadFileProperties uploadFileProperties;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/{title}/upload-file")
    public String uploadFile(@PathVariable String title, final Model model) {
        model.addAttribute("file", new FileModel());
        return "file/movie/upload_file";
    }

    @PostMapping("/{title}/upload-file")
    public String uploadFile(@PathVariable String title, @RequestParam("multipartFile") MultipartFile multipartFile,
            RedirectAttributes redirectAttributes, final Model model) throws FileStorageException {
        try {
            FileModel file = fileStrorageService.store(multipartFile);
            file.setUrl(fileDownloadUrl("/img/upload/", file.getFileName()));
            mangajutsuProxy.addMovieFile(file, title);
        } catch (FileStorageException e) {
            model.addAttribute("error",
                    messageSource.getMessage("error.store-file", null, LocaleContextHolder.getLocale()));
            return "file/movie/upload_file";

        } catch (FeignException e) {
            model.addAttribute("error",
                    messageSource.getMessage("error.upload-file", null, LocaleContextHolder.getLocale()));
            return "file/movie/upload_file";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("upload-file.success.msg", null, LocaleContextHolder.getLocale()));

        model.addAttribute("title", title);

        return "redirect:/movie/movie-details/{title}";
    }

    @GetMapping("/{title}/add-file")
    public String addFile(@PathVariable String title, final Model model) {
        List<String> fileTypes = mangajutsuProxy.getFileTypes();

        model.addAttribute("fileTypes", fileTypes);
        if (!model.containsAttribute("file")) {
            model.addAttribute("file", new FileModel());
        }
        return "file/movie/add_file";
    }

    @PostMapping("/{title}/add-file")
    public String addFile(@PathVariable String title, @Valid @ModelAttribute("file") FileModel file,
            BindingResult bindingResult, RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.file",
                    bindingResult);
            redirectAttributes.addFlashAttribute("file", file);
            return "redirect:/file/movie/{title}/add-file";
        }
        try {
            mangajutsuProxy.addMovieFile(file, title);
        } catch (FeignException e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("error.upload-file", null, LocaleContextHolder.getLocale()));
            redirectAttributes.addFlashAttribute("file", file);
            return "redirect:/file/movie/{title}/add-file";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("upload-file.success.msg", null, LocaleContextHolder.getLocale()));

        model.addAttribute("title", title);
        model.addAttribute("file", file);

        return "redirect:/movie/movie-details/{title}";
    }

    @GetMapping("/{title}/file-list")
    public String filesList(@PathVariable String title, final Model model) {
        List<FileModel> files = mangajutsuProxy.getMovieFiles(title);
        model.addAttribute("files", files);
        return "file/movie/file_list";
    }

    @GetMapping("{title}/update-file/{id}")
    public String updateFile(@PathVariable Integer id, final Model model) {
        FileModel file = mangajutsuProxy.getFileDetails(id);
        List<String> fileTypes = mangajutsuProxy.getFileTypes();

        model.addAttribute("fileTypes", fileTypes);
        if (!model.containsAttribute("file")) {
            model.addAttribute("file", file);
        }
        return "file/movie/update_file";
    }

    @PostMapping("{title}/update-file/{id}")
    public String updateFile(@PathVariable Integer id, @Valid @ModelAttribute("file") FileModel file,
            BindingResult bindingResult, RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.file",
                    bindingResult);
            redirectAttributes.addFlashAttribute("file", file);
            return "redirect:/file/movie/{title}/file-list";
        }
        try {
            mangajutsuProxy.updateFile(file, id);
        } catch (FeignException e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("error.update-file", null, LocaleContextHolder.getLocale()));
            redirectAttributes.addFlashAttribute("file", file);
            return "redirect:/file/movie/{title}/file-list";
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("update-file.success.msg", null, LocaleContextHolder.getLocale()));

        model.addAttribute("file", file);

        return "redirect:/file/movie/{title}/file-list";
    }

    @GetMapping("{title}/delete-file/{id}")
    public String deleteFile(@PathVariable Integer id, final Model model, RedirectAttributes redirectAttributes)
            throws IOException {
        FileModel file = mangajutsuProxy.getFileDetails(id);
        Path path = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize()
                .resolve(file.getFileName());
        try {
            Files.deleteIfExists(path);
            mangajutsuProxy.deleteFile(id);
        } catch (IOException e) {
            model.addAttribute("error",
                    messageSource.getMessage("error.delete-file", null, LocaleContextHolder.getLocale()));
            model.addAttribute("file", mangajutsuProxy.getFileDetails(id));
            return "file/movie/file_list";
        } catch (FeignException e) {
            model.addAttribute("error",
                    messageSource.getMessage("error.delete-file", null, LocaleContextHolder.getLocale()));
            model.addAttribute("file", mangajutsuProxy.getFileDetails(id));
        }
        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("delete-file.success.msg", null, LocaleContextHolder.getLocale()));

        model.addAttribute("file", mangajutsuProxy.getFileDetails(id));

        return "redirect:/file/movie/{title}/file-list";
    }

    public String fileDownloadUrl(final String baseURL, final String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(baseURL)
                .path(fileName).toUriString();
    }
}
