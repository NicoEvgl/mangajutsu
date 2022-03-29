package com.mangajutsu.api.services;

import org.springframework.stereotype.Service;

@Service
public interface UserAccountService {

    boolean loginDisabled(String username);
}
