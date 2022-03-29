package com.mangajutsu.api.services.impl;

import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.services.UserAccountService;
import com.mangajutsu.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserService userService;

    @Override
    public boolean loginDisabled(String username) {
        UserEntity user = userService.findByUsername(username);
        return user != null ? user.isLoginDisabled() : false;
    }
}
