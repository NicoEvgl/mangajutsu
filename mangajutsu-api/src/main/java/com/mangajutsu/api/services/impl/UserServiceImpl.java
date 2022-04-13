package com.mangajutsu.api.services.impl;

import java.util.List;

import javax.mail.MessagingException;

import com.mangajutsu.api.dao.entities.RoleEntity;
import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.entities.VerifTokenEntity;
import com.mangajutsu.api.dao.repositories.UserRepository;
import com.mangajutsu.api.dao.repositories.VerifTokenRepository;
import com.mangajutsu.api.emails.AccountVerifEmailContext;
import com.mangajutsu.api.emails.ForgotPasswordEmailContext;
import com.mangajutsu.api.exceptions.UnknownIdentifierException;
import com.mangajutsu.api.exceptions.UserAlreadyExistException;
import com.mangajutsu.api.models.UserModel;
import com.mangajutsu.api.services.EmailService;
import com.mangajutsu.api.services.RoleService;
import com.mangajutsu.api.services.UserService;
import com.mangajutsu.api.services.VerifTokenService;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
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

    @Value("${mangajutsu.base.url.http}")
    private String baseURL;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
    public UserEntity getUserDetails(Integer id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public List<UserEntity> getUserList() {
        List<UserEntity> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return users;
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

    @Override
    public void sendResetPasswordEmail(UserEntity user) {
        VerifTokenEntity verifToken = verifTokenService.createVerifToken();
        verifToken.setUser(user);
        verifTokenRepository.save(verifToken);
        ForgotPasswordEmailContext emailContext = new ForgotPasswordEmailContext();
        emailContext.init(user);
        emailContext.setToken(verifToken.getToken());
        emailContext.buildVerificationUrl(baseURL, verifToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean loginDisabled(String username) {
        UserEntity user = userRepository.findByUsername(username);
        return user != null ? user.isLoginDisabled() : false;
    }

    @Override
    public void updateUser(UserEntity user, Integer id) throws UnknownIdentifierException {
        UserEntity editedUser = userRepository.getById(id);
        if (editedUser == null) {
            throw new UnknownIdentifierException("Unknown user with id : " + id);
        }
        editedUser.setFirstName(user.getFirstName());
        editedUser.setLastName(user.getLastName());

        userRepository.save(editedUser);
    }

    @Override
    public void addRole(RoleEntity role, Integer id) {
        UserEntity user = userRepository.getById(id);
        if (!user.getUserRoles().isEmpty()) {
            user.getUserRoles().removeAll(user.getUserRoles());
        }
        user.getUserRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void accountLocked(Integer id) {
        UserEntity user = userRepository.getById(id);
        if (user != null) {
            user.setLoginDisabled(true);
            userRepository.save(user);
        }
    }

    @Override
    public void accountNonLocked(Integer id) {
        UserEntity user = userRepository.getById(id);
        if (user != null) {
            user.setLoginDisabled(false);
            userRepository.save(user);
        }
    }

    private void encodePassword(UserModel source, UserEntity target) {
        target.setPassword(bCryptPasswordEncoder.encode(source.getPassword()));
    }
}
