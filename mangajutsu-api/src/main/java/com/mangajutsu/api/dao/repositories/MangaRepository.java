package com.mangajutsu.api.dao.repositories;

import java.util.List;

import com.mangajutsu.api.dao.entities.MangaEntity;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaRepository extends JpaRepository<MangaEntity, Integer> {
    List<MangaEntity> findAllByUser_Username(String username, Sort sort);

    MangaEntity findByTitle(String title);
}
