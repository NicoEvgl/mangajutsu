package com.mangajutsu.webclient.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.mangajutsu.webclient.exceptions.FileStorageException;
import com.mangajutsu.webclient.models.FileModel;
import com.mangajutsu.webclient.services.FileStrorageService;
import com.mangajutsu.webclient.utils.UploadFileProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service("fileStorageService")
public class FileStorageServiceImpl implements FileStrorageService {

    @Autowired
    private UploadFileProperties uploadFileProperties;

    @Override
    public FileModel store(MultipartFile multipartFile) throws FileStorageException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            Path uploadDir = getUploadDirLocation().resolve(fileName);
            Files.copy(multipartFile.getInputStream(), uploadDir, StandardCopyOption.REPLACE_EXISTING);

            FileModel file = new FileModel();

            file.setFileName(fileName);
            file.setSize(multipartFile.getSize());
            file.setType(multipartFile.getContentType());

            return file;

        } catch (IOException e) {
            throw new FileStorageException("Unable to store file " + fileName);
        }
    }

    private Path getUploadDirLocation() {
        return Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize();
    }
}
