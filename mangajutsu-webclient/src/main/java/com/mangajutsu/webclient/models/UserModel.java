package com.mangajutsu.webclient.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserModel implements UserDetails {
    private Integer userId;
    @NotEmpty(message = "{registration.validation.firstName}")
    private String firstName;
    @NotEmpty(message = "{registration.validation.lastName}")
    private String lastName;
    @NotEmpty(message = "{registration.validation.username}")
    private String username;
    @NotEmpty(message = "{registration.validation.email}")
    @Email(message = "{registration.validation.email}")
    private String email;
    @NotEmpty(message = "{registration.validation.password}")
    private String password;

    private Set<RoleModel> userRoles;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserModel(Integer userId, @NotEmpty(message = "{registration.validation.firstName}") String firstName,
            @NotEmpty(message = "{registration.validation.lastName}") String lastName,
            @NotEmpty(message = "{registration.validation.username}") String username,
            @NotEmpty(message = "{registration.validation.email}") @Email(message = "{registration.validation.email}") String email,
            @NotEmpty(message = "{registration.validation.password}") String password,
            Set<RoleModel> userRoles,
            Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRoles = userRoles;
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<RoleModel> userRoles = getUserRoles();
        Collection<GrantedAuthority> authorities = new ArrayList<>(userRoles.size());
        for (RoleModel userRole : userRoles) {
            authorities.add(new SimpleGrantedAuthority(userRole.getCode().toUpperCase()));
        }
        return authorities;
    }

    // Getters and Setters //

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleModel> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<RoleModel> userRoles) {
        this.userRoles = userRoles;
    }

    // to String() //

    @Override
    public String toString() {
        return "User [authorities=" + authorities + ", email=" + email + ", firstName=" + firstName + ", lastName="
                + lastName + ", password=" + password + ", userId=" + userId + ", userRoles=" + userRoles
                + ", username=" + username + "]";
    }
}