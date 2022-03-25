package com.mangajutsu.api.services.impl;

import javax.mail.MessagingException;

import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.entities.VerifTokenEntity;
import com.mangajutsu.api.dao.repositories.UserRepository;
import com.mangajutsu.api.dao.repositories.VerifTokenRepository;
import com.mangajutsu.api.emails.AccountVerifEmailContext;
import com.mangajutsu.api.exceptions.UserAlreadyExistException;
import com.mangajutsu.api.models.UserModel;
import com.mangajutsu.api.services.EmailService;
import com.mangajutsu.api.services.RoleService;
import com.mangajutsu.api.services.UserService;
import com.mangajutsu.api.services.VerifTokenService;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private VerifTokenRepository verifTokenRepository;
    @Autowired
    private VerifTokenService verifTokenService;
    @Autowired
    private EmailService emailService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void encodePassword(UserModel source, UserEntity target) {
        target.setPassword(bCryptPasswordEncoder.encode(source.getPassword()));
    }

    @Value("${mangajutsu.base.url.http}")
    private String baseURL;

    @Override
    public void register(UserModel userModel) throws UserAlreadyExistException {
        if (checkIfUserExist(userModel.getUsername())) {
            throw new UserAlreadyExistException("Username already exist");
        }
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userModel, user);
        encodePassword(userModel, user);
        roleService.addCustomerRole(user);
        userRepository.save(user);
        sendAccountVerifEmail(user);
    }

    @Override
    public boolean checkIfUserExist(String username) {
        return userRepository.findByUsername(username) != null ? true : false;
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username) {
        UserEntity userEntity;
        try {
            userEntity = userRepository.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return userEntity;
    }

    @Override
    public void sendAccountVerifEmail(UserEntity user) {
        VerifTokenEntity verifToken = verifTokenService.createVerifToken();
        verifToken.setUser(user);
        verifTokenRepository.save(verifToken);
        AccountVerifEmailContext emailContext = new AccountVerifEmailContext();
        emailContext.init(user);
        emailContext.setToken(verifToken.getToken());
        emailContext.buildVerificationUrl(baseURL, verifToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}