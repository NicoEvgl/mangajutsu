package com.mangajutsu.api.dao.repositories;

import java.util.Set;

import com.mangajutsu.api.dao.entities.AnimeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeRepository extends JpaRepository<AnimeEntity, Integer> {
    Set<AnimeEntity> findAllByUser_Id(Integer userId);

    AnimeEntity findByTitle(String title);
}
