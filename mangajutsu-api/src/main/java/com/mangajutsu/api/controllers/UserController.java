package com.mangajutsu.api.controllers;

import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.entities.VerifTokenEntity;
import com.mangajutsu.api.dao.repositories.UserRepository;
import com.mangajutsu.api.dao.repositories.VerifTokenRepository;
import com.mangajutsu.api.services.UserService;
import com.mangajutsu.api.services.VerifTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    VerifTokenService verifTokenService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VerifTokenRepository verifTokenRepository;

    @GetMapping("find-user/{username}")
    public UserEntity findUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @DeleteMapping("/deleteUnverifiedAccount")
    public void deleteUnverifiedAccount() {
        VerifTokenEntity verifToken = verifTokenRepository.findAll(Sort.by(Sort.Direction.DESC,
                "createdAt")).get(0);
        if (verifToken.isExpired()) {
            UserEntity unverifiedAccount = verifToken.getUser();
            verifTokenService.removeVerifToken(verifToken);
            userRepository.delete(unverifiedAccount);
        }
    }
}
