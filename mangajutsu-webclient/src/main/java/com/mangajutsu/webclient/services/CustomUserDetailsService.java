package com.mangajutsu.webclient.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import com.mangajutsu.webclient.models.RoleModel;
import com.mangajutsu.webclient.models.UserModel;
import com.mangajutsu.webclient.proxies.MangajutsuProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
            throw new UsernameNotFoundException("Username is empty");
        }
        final UserModel user = mangajutsuProxy.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " is unknown");
        }

        UserDetails userDetails = new UserModel(user.getId(), user.getFirstName(), user.getLastName(),
                user.getUsername(), user.getEmail(), user.getPassword(), user.getUserRoles(),
                getAuthorities(user.getUserRoles()));

        return userDetails;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Set<RoleModel> userRoles) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleModel role : userRoles) {
            authorities.add(new SimpleGrantedAuthority(role.getNameRole().toUpperCase()));
        }
        return authorities;
    }
}
