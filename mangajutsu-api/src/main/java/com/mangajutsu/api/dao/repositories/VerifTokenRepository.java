package com.mangajutsu.api.dao.repositories;

import com.mangajutsu.api.dao.entities.VerifTokenEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifTokenRepository extends JpaRepository<VerifTokenEntity, Integer> {
    VerifTokenEntity findByToken(String token);
}
