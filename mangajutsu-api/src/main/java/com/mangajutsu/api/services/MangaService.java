package com.mangajutsu.api.services;

import java.util.List;

import com.mangajutsu.api.dao.entities.MangaEntity;
import com.mangajutsu.api.exceptions.ResourceAlreadyExistException;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;

import org.springframework.stereotype.Service;

@Service
public interface MangaService {
    List<MangaEntity> getMangaList();

    MangaEntity getMangaDetails(String title);

    List<MangaEntity> getUserMangas(String username);

    void addManga(MangaEntity manga, String username) throws ResourceAlreadyExistException;

    boolean checkIfMangaExist(String title);

    void updateManga(MangaEntity manga, String title) throws ResourceNotFoundException;

    void deleteManga(String title);
}
