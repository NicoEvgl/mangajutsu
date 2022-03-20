package com.mangajutsu.api.services.impl;

import com.mangajutsu.api.dao.entities.RoleEntity;
import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.repositories.RoleRepository;
import com.mangajutsu.api.dao.repositories.UserRepository;
import com.mangajutsu.api.exceptions.UserAlreadyExistException;
import com.mangajutsu.api.models.UserModel;
import com.mangajutsu.api.services.RoleService;
import com.mangajutsu.api.services.UserService;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void register(UserModel user) throws UserAlreadyExistException {
        // check if userEntity already exist
        if (checkIfUserExist(user.getUsername())) {
            throw new UserAlreadyExistException("Username already exist");
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        encodePassword(userEntity, user);
        roleService.addCustomerRole(userEntity);
        userRepository.save(userEntity);
    }

    @Override
    public boolean checkIfUserExist(String username) {
        return userRepository.findByUsername(username) != null;
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

    private void encodePassword(UserEntity userEntity, UserModel user) {
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }
}
