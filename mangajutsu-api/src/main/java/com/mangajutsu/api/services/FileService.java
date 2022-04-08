package com.mangajutsu.api.services;

import java.util.List;

import com.mangajutsu.api.dao.entities.FileEntity;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;

import org.springframework.stereotype.Service;

@Service
public interface FileService {

    void uploadFile(FileEntity file, String title) throws ResourceNotFoundException;

    List<FileEntity> getAnimeFiles(String title);
}
