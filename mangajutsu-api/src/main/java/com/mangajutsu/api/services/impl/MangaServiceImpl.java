package com.mangajutsu.api.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mangajutsu.api.dao.entities.FileEntity;
import com.mangajutsu.api.dao.entities.MangaEntity;
import com.mangajutsu.api.dao.entities.ReviewEntity;
import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.repositories.MangaRepository;
import com.mangajutsu.api.exceptions.ResourceAlreadyExistException;
import com.mangajutsu.api.exceptions.ResourceNotFoundException;
import com.mangajutsu.api.services.FileService;
import com.mangajutsu.api.services.MangaService;
import com.mangajutsu.api.services.ReviewService;
import com.mangajutsu.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("mangaService")
public class MangaServiceImpl implements MangaService {

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ReviewService reviewService;

    @Override
    public List<MangaEntity> getMangaList() {
        List<MangaEntity> mangas = mangaRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
        for (MangaEntity manga : mangas) {
            List<FileEntity> files = fileService.getMangaFiles(manga.getTitle());
            manga.setFiles(files);
        }
        return mangas;
    }

    @Override
    public List<MangaEntity> getTopMangaList() {
        List<MangaEntity> mangas = mangaRepository.findAll(Sort.by(Sort.Direction.DESC, "rating"));
        for (MangaEntity manga : mangas) {
            List<FileEntity> files = fileService.getMangaFiles(manga.getTitle());
            manga.setFiles(files);
        }
        return mangas;
    }

    @Override
    public List<MangaEntity> getLastMangaList() {
        List<MangaEntity> mangas = mangaRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        for (MangaEntity manga : mangas) {
            List<FileEntity> files = fileService.getMangaFiles(manga.getTitle());
            manga.setFiles(files);
        }
        return mangas;
    }

    @Override
    public MangaEntity getMangaDetails(String title) {
        MangaEntity manga = mangaRepository.findByTitle(title);
        if (manga != null) {
            SetMangaRating(manga);
        }
        return manga;
    }

    @Override
    public List<MangaEntity> getUserMangas(String username) {
        List<MangaEntity> mangas = mangaRepository.findAllByUser_Username(username,
                Sort.by(Sort.Direction.DESC, "createdAt"));
        return mangas;
    }

    @Override
    public void addManga(MangaEntity manga, String username) throws ResourceAlreadyExistException {
        if (checkIfMangaExist(manga.getTitle())) {
            throw new ResourceAlreadyExistException("Manga already exist");
        }
        addUserToManga(manga, username);
        mangaRepository.save(manga);

    }

    @Override
    public boolean checkIfMangaExist(String title) {
        return mangaRepository.findByTitle(title) != null ? true : false;
    }

    @Override
    public void updateManga(MangaEntity manga, String title) throws ResourceNotFoundException {
        MangaEntity editedManga = mangaRepository.findByTitle(title);
        if (editedManga == null) {
            throw new ResourceNotFoundException("Manga not found for the title : " + title);
        }
        editedManga.setTitle(manga.getTitle());
        editedManga.setTitleVo(manga.getTitleVo());
        editedManga.setOrigin(manga.getOrigin());
        editedManga.setStatus(manga.getStatus());
        editedManga.setReleaseDate(manga.getReleaseDate());
        editedManga.setEndDate(manga.getEndDate());
        editedManga.setNbChapters(manga.getNbChapters());
        editedManga.setNbTomes(manga.getNbTomes());
        editedManga.setType(manga.getType());
        editedManga.setGenre(manga.getGenre());
        editedManga.setMangaka(manga.getMangaka());
        editedManga.setEditor(manga.getEditor());
        editedManga.setEditorUrl(manga.getEditorUrl());
        editedManga.setEditorVo(manga.getEditorVo());
        editedManga.setSynopsis(manga.getSynopsis());

        mangaRepository.save(editedManga);

    }

    @Override
    public void deleteManga(String title) {
        MangaEntity manga = mangaRepository.findByTitle(title);
        if (!manga.getReviews().isEmpty()) {
            for (ReviewEntity review : manga.getReviews()) {
                reviewService.deleteReview(review.getId());
            }
        }
        for (FileEntity file : manga.getFiles()) {
            fileService.deleteFile(file.getId());
        }
        mangaRepository.delete(manga);
    }

    public void addUserToManga(MangaEntity manga, String username) {
        UserEntity user = userService.findByUsername(username);
        manga.setUser(user);
        user.getMangas().add(manga);
    }

    public void SetMangaRating(MangaEntity manga) {
        if (!manga.getReviews().isEmpty()) {
            Collection<Float> ratings = new ArrayList<>(manga.getReviews().size());
            for (ReviewEntity review : manga.getReviews()) {
                ratings.add(review.getRating());
            }
            float averageRating = (float) ratings.stream().mapToDouble(Float::intValue).sum() / ratings.size();
            if (averageRating != manga.getRating()) {
                manga.setRating(averageRating);
                mangaRepository.save(manga);
            }
        } else if (manga.getReviews().isEmpty() && manga.getRating() != 0) {
            manga.setRating(0);
            mangaRepository.save(manga);
        }
    }
}
