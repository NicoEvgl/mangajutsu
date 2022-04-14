package com.mangajutsu.webclient.services;

import com.mangajutsu.webclient.exceptions.FileStorageException;
import com.mangajutsu.webclient.models.FileModel;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileStrorageService {
    FileModel store(MultipartFile multipartFile) throws FileStorageException;
}
