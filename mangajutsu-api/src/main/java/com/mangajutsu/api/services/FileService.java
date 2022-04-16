package com.mangajutsu.api.services;

import java.util.List;

import com.mangajutsu.api.dao.entities.FileEntity;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;

import org.springframework.stereotype.Service;

@Service
public interface FileService {

    void addMangaFile(FileEntity file, String title) throws ResourceNotFoundException;

    void addAnimeFile(FileEntity file, String title) throws ResourceNotFoundException;

    void addMovieFile(FileEntity file, String title) throws ResourceNotFoundException;

    List<FileEntity> getMangaFiles(String title);

    List<FileEntity> getAnimeFiles(String title);

    List<FileEntity> getMovieFiles(String title);

    FileEntity getFileDetails(Integer id);

    void updateFile(FileEntity file, Integer id) throws ResourceNotFoundException;

    void deleteFile(Integer id);
}
