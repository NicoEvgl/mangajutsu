package com.mangajutsu.api.dao.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer", schema = "public")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;
    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "enabled", columnDefinition = "boolean default false")
    private boolean enabled;
    @Column(name = "failed_login_attempts", columnDefinition = "integer default 0")
    private int failedLoginAttempts;
    @Column(name = "login_disabled", columnDefinition = "boolean default false")
    private boolean loginDisabled;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<RoleEntity> userRoles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<VerifTokenEntity> tokens;

    public void addToken(final VerifTokenEntity token) {
        tokens.add(token);
    }

    // Getters & Setters //

    public Integer getId() {
        return id;
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

    public Set<RoleEntity> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<RoleEntity> userRoles) {
        this.userRoles = userRoles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<VerifTokenEntity> getTokens() {
        return tokens;
    }

    public void setTokens(Set<VerifTokenEntity> tokens) {
        this.tokens = tokens;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public boolean isLoginDisabled() {
        return loginDisabled;
    }

    public void setLoginDisabled(boolean loginDisabled) {
        this.loginDisabled = loginDisabled;
    }
}