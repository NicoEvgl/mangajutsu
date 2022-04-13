package com.mangajutsu.api.controllers;

import java.util.Objects;

import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.entities.VerifTokenEntity;
import com.mangajutsu.api.dao.repositories.UserRepository;
import com.mangajutsu.api.exceptions.InvalidTokenException;
import com.mangajutsu.api.exceptions.UnknownIdentifierException;
import com.mangajutsu.api.models.ForgotPasswordModel;
import com.mangajutsu.api.services.UserService;
import com.mangajutsu.api.services.VerifTokenService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/password", method = { RequestMethod.GET, RequestMethod.POST })
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerifTokenService verifTokenService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/reset")
    public void resetPassword(final ForgotPasswordModel forgotPassword) throws UnknownIdentifierException {
        UserEntity user = userRepository.findByEmail(forgotPassword.getEmail());
        userService.sendResetPasswordEmail(user);
        if (user == null || !user.isEnabled()) {
            throw new UnknownIdentifierException("Unknown account or unverified account");
        }
    }

    @PostMapping("/change")
    public void changePassword(final ForgotPasswordModel forgotPassword)
            throws InvalidTokenException, UnknownIdentifierException {
        VerifTokenEntity verifToken = verifTokenService.findByToken(forgotPassword.getToken());
        if (Objects.isNull(verifToken) || !StringUtils.equals(forgotPassword.getToken(), verifToken.getToken())
                || verifToken.isExpired()) {
            throw new InvalidTokenException("Token is not valid");
        }
        UserEntity user = userRepository.getById(verifToken.getUser().getId());
        if (Objects.isNull(user)) {
            throw new UnknownIdentifierException("Unable to find user for the token");
        }
        user.setPassword(bCryptPasswordEncoder.encode(forgotPassword.getPassword()));
        userRepository.save(user);
        verifTokenService.removeVerifToken(verifToken);
    }
}
