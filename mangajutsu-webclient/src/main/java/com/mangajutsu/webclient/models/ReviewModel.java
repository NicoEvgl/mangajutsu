package com.mangajutsu.webclient.models;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;

public class ReviewModel implements Serializable {
    private Integer id;
    private float rating;
    @NotEmpty(message = "{add-review.validation.content}")
    private String content;
    private Timestamp releaseDate;
    private Timestamp updateDate;

    private UserModel user;
    private AnimeModel anime;

    // Getters & Setters //

    public Integer getId() {
        return id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public AnimeModel getAnime() {
        return anime;
    }

    public void setAnime(AnimeModel anime) {
        this.anime = anime;
    }
}
