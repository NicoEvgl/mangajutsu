package com.mangajutsu.webclient.services;

import org.springframework.stereotype.Service;

@Service
public interface UserAccountService {

    boolean loginDisabled(String username);
}
