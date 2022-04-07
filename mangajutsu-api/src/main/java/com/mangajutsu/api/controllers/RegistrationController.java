package com.mangajutsu.api.controllers;

import java.util.Objects;

import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.entities.VerifTokenEntity;
import com.mangajutsu.api.dao.repositories.UserRepository;
import com.mangajutsu.api.exceptions.InvalidTokenException;
import com.mangajutsu.api.exceptions.UserAlreadyExistException;
import com.mangajutsu.api.models.UserModel;
import com.mangajutsu.api.services.UserService;
import com.mangajutsu.api.services.VerifTokenService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/register", method = { RequestMethod.GET, RequestMethod.POST })
public class RegistrationController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VerifTokenService verifTokenService;

    @PostMapping
    public void userRegistration(@RequestBody UserModel userModel) throws UserAlreadyExistException {
        userService.register(userModel);
    }

    @GetMapping("/verify")
    public boolean verifyAccount(@RequestParam("token") String token) throws InvalidTokenException {
        VerifTokenEntity verifToken = verifTokenService.findByToken(token);
        if (Objects.isNull(verifToken) || !StringUtils.equals(token, verifToken.getToken()) || verifToken.isExpired()) {
            throw new InvalidTokenException("Token is not valid");
        }
        UserEntity user = userRepository.getById(verifToken.getUser().getId());
        if (Objects.isNull(user)) {
            return false;
        }
        user.setEnabled(true);
        userRepository.save(user);
        verifTokenService.removeVerifToken(verifToken);

        return true;
    }
}
