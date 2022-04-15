package com.mangajutsu.api.dao.repositories;

import com.mangajutsu.api.dao.entities.MovieEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
    MovieEntity findByTitle(String title);
}
