package com.mangajutsu.webclient.services;

import com.mangajutsu.webclient.models.UserModel;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MangajutsuProxy mangajutsuProxy;

    @Autowired
    public CustomUserDetailsService(MangajutsuProxy mangajutsuProxy) {
        this.mangajutsuProxy = mangajutsuProxy;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.trim().isEmpty()) {
            throw new UsernameNotFoundException("Le pseudo est vide");
        }
        final UserModel user = mangajutsuProxy.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Le pseudo " + username + " est inconnu");
        }

        return user;
    }
}
