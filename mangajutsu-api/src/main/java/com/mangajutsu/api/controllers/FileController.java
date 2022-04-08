package com.mangajutsu.api.controllers;

import java.util.List;

import com.mangajutsu.api.dao.entities.FileEntity;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;
import com.mangajutsu.api.services.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/{title}/add-file")
    public void uploadFile(@RequestBody FileEntity file, @PathVariable String title) throws ResourceNotFoundException {
        fileService.uploadFile(file, title);
    }

    @GetMapping("/{title}/anime-files")
    public List<FileEntity> getAnimeFiles(@PathVariable String title) {
        List<FileEntity> files = fileService.getAnimeFiles(title);
        return files;
    }
}
