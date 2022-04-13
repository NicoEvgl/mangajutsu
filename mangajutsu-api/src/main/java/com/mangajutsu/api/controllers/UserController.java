package com.mangajutsu.api.controllers;

import java.util.List;

import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.entities.VerifTokenEntity;
import com.mangajutsu.api.dao.repositories.UserRepository;
import com.mangajutsu.api.dao.repositories.VerifTokenRepository;
import com.mangajutsu.api.exceptions.UnknownIdentifierException;
import com.mangajutsu.api.services.BruteForceProtectionService;
import com.mangajutsu.api.services.UserService;
import com.mangajutsu.api.services.VerifTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerifTokenService verifTokenService;

    @Autowired
    private BruteForceProtectionService bruteForceProtectionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerifTokenRepository verifTokenRepository;

    @GetMapping("/find-user/{username}")
    public UserEntity findUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/user-details/{id}")
    public UserEntity getUserDetails(@PathVariable Integer id) {
        return userService.getUserDetails(id);
    }

    @DeleteMapping("/delete-unverified-account")
    public void deleteUnverifiedAccount(VerifTokenEntity verifTokenEntity) {
        if (verifTokenEntity != null) {
            List<VerifTokenEntity> verifTokens = verifTokenRepository.findAll();
            for (VerifTokenEntity verifToken : verifTokens) {
                if (verifToken.isExpired()) {
                    UserEntity unverifiedAccount = verifToken.getUser();
                    verifTokenService.removeVerifToken(verifToken);
                    if (!unverifiedAccount.isEnabled()) {
                        userRepository.delete(unverifiedAccount);
                    }
                }
            }
        }
    }

    @PostMapping("/register-login-failure")
    public void registerLoginFailure(@RequestBody String username) {
        bruteForceProtectionService.registerLoginFailure(username);
    }

    @PostMapping("/reset-bruteforce-counter")
    public void resetBruteForceCounter(@RequestBody String username) {
        bruteForceProtectionService.resetBruteForceCounter(username);
    }

    @GetMapping("/login-disabled/{username}")
    public boolean loginDisabled(@PathVariable String username) {
        return userService.loginDisabled(username);
    }

    @PostMapping("/update-user/{id}")
    public void updateUser(@RequestBody UserEntity user, @PathVariable Integer id) throws UnknownIdentifierException {
        userService.updateUser(user, id);
    }
}
