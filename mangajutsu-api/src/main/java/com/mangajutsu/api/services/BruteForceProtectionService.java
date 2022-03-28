package com.mangajutsu.api.services;

import org.springframework.stereotype.Service;

@Service
public interface BruteForceProtectionService {
    void registerLoginFailure(String username);

    void resetBruteForceCounter(String username);

    boolean isBruteForceAttack(String username);
}
