package com.mangajutsu.api.dao.repositories;

import java.util.List;

import com.mangajutsu.api.dao.entities.FileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    FileEntity findByUrl(String url);

    List<FileEntity> findAllByAnime_Title(String title);
}
