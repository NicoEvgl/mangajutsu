package com.mangajutsu.api.dao.repositories;

import java.util.List;

import com.mangajutsu.api.dao.entities.MovieEntity;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
    List<MovieEntity> findAllByUser_Username(String username, Sort sort);

    MovieEntity findByTitle(String title);
}
