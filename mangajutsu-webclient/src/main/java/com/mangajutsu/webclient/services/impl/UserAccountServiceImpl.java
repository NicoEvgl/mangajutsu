package com.mangajutsu.webclient.services.impl;

import com.mangajutsu.webclient.models.UserModel;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;
import com.mangajutsu.webclient.services.UserAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @Override
    public boolean loginDisabled(String username) {
        UserModel user = mangajutsuProxy.findUserByUsername(username);
        return user != null ? user.isLoginDisabled() : false;
    }
}
