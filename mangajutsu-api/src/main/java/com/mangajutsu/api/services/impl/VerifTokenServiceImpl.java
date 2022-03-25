package com.mangajutsu.api.services.impl;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

import com.mangajutsu.api.dao.entities.VerifTokenEntity;
import com.mangajutsu.api.dao.repositories.VerifTokenRepository;
import com.mangajutsu.api.services.VerifTokenService;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

@Service("verifTokenService")
public class VerifTokenServiceImpl implements VerifTokenService {

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");

    @Autowired
    VerifTokenRepository verifTokenRepository;

    @Value("${mangajutsu.verif.token.validity}")
    private int tokenValidityInSeconds;

    public int getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }

    @Override
    public VerifTokenEntity createVerifToken() {
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII);
        VerifTokenEntity verifToken = new VerifTokenEntity();
        verifToken.setToken(tokenValue);
        verifToken.setExpireAt(LocalDateTime.now().plusSeconds(getTokenValidityInSeconds()));
        return verifToken;
    }

    @Override
    public void saveVerifToken(VerifTokenEntity token) {
        verifTokenRepository.save(token);
    }

    @Override
    public VerifTokenEntity findByToken(String token) {
        return verifTokenRepository.findByToken(token);
    }

    @Override
    public void removeVerifToken(VerifTokenEntity token) {
        verifTokenRepository.delete(token);
    }
}