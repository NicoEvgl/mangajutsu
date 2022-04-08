package com.mangajutsu.api.services.impl;

import java.util.List;

import com.mangajutsu.api.dao.entities.AnimeEntity;
import com.mangajutsu.api.dao.entities.FileEntity;
import com.mangajutsu.api.dao.repositories.AnimeRepository;
import com.mangajutsu.api.dao.repositories.FileRepository;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;
import com.mangajutsu.api.services.FileService;

import org.springframework.beans.factory.annotation.Autowired;
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
        List<FileEntity> files = fileRepository.findAllByAnime_Title(title);
        return files;
    }
}
