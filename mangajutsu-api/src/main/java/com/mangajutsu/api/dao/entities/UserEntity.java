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
    @Column(name = "id", nullable = false, columnDefinition = "integer not null")
    private Integer id;
    @Column(name = "first_name", nullable = false, length = 50, columnDefinition = "varchar(50) not null")
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50, columnDefinition = "varchar(50) not null")
    private String lastName;
    @Column(name = "username", unique = true, nullable = false, length = 50, columnDefinition = "varchar(50) not null")
    private String username;
    @Column(name = "email", unique = true, nullable = false, length = 50, columnDefinition = "varchar(50) not null")
    private String email;
    @Column(name = "password", nullable = false, columnDefinition = "varchar not null")
    private String password;
    @Column(name = "enabled", columnDefinition = "boolean default false")
    private boolean enabled;

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
}