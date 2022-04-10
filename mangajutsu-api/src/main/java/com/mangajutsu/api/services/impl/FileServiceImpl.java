package com.mangajutsu.api.services.impl;

import java.util.List;

import com.mangajutsu.api.dao.entities.AnimeEntity;
import com.mangajutsu.api.dao.entities.FileEntity;
import com.mangajutsu.api.dao.repositories.AnimeRepository;
import com.mangajutsu.api.dao.repositories.FileRepository;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;
import com.mangajutsu.api.services.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public void uploadFile(FileEntity file, String title) throws ResourceNotFoundException {
        if (file == null || title == null) {
            throw new ResourceNotFoundException("File or Anime not found");
        }
        AnimeEntity anime = animeRepository.findByTitle(title);
        file.setAnime(anime);
        anime.getFiles().add(file);
        fileRepository.save(file);
    }

    @Override
    public List<FileEntity> getAnimeFiles(String title) {
        List<FileEntity> files = fileRepository.findAllByAnime_Title(title, Sort.by(Sort.Direction.ASC, "fileName"));
        return files;
    }

    @Override
    public FileEntity getFileDetails(Integer id) {
        FileEntity file = fileRepository.findById(id).orElse(null);
        return file;
    }

    @Override
    public void updateFile(FileEntity file, Integer id) throws ResourceNotFoundException {
        FileEntity editedFile = fileRepository.findById(id).orElse(null);
        if (editedFile == null) {
            throw new ResourceNotFoundException("File not found for the id : " + id);
        }
        editedFile.setFileName(file.getFileName());
        editedFile.setType(file.getType());
        editedFile.setUrl(file.getUrl());
        editedFile.setSize(file.getSize());

        fileRepository.save(editedFile);
    }

    @Override
    public void deleteFile(Integer id) {
        FileEntity file = fileRepository.findById(id).orElse(null);
        fileRepository.delete(file);
    }
}
