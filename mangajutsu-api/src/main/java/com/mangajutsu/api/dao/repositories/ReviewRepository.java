package com.mangajutsu.api.dao.repositories;

import java.util.List;

import com.mangajutsu.api.dao.entities.ReviewEntity;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    List<ReviewEntity> findAllByManga_Title(String title, Sort sort);

    List<ReviewEntity> findAllByAnime_Title(String title, Sort sort);

    List<ReviewEntity> findAllByMovie_Title(String title, Sort sort);
}
