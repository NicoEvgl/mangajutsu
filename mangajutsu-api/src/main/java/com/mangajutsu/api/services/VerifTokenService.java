package com.mangajutsu.api.services;

import com.mangajutsu.api.dao.entities.VerifTokenEntity;

import org.springframework.stereotype.Service;

@Service
public interface VerifTokenService {
    VerifTokenEntity createVerifToken();

    void saveVerifToken(VerifTokenEntity token);

    VerifTokenEntity findByToken(String token);

    void removeVerifToken(VerifTokenEntity verifToken);
}