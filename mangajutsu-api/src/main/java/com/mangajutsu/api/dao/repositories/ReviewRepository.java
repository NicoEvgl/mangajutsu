package com.mangajutsu.api.dao.repositories;

import java.util.Set;

import com.mangajutsu.api.dao.entities.ReviewEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    Set<ReviewEntity> findAllByAnime_Title(String title);
}
