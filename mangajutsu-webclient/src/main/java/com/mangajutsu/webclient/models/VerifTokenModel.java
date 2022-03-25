package com.mangajutsu.webclient.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class VerifTokenModel {
    private Integer id;
    private String token;
    private Timestamp createdAt;
    private LocalDateTime expireAt;

    private UserModel user;

    private transient boolean expired;

    // Getters & Setters //

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public String setToken(String token) {
        return this.token = token;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
