package com.mangajutsu.api.dao.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "review", schema = "public")
public class ReviewEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "rating", nullable = false, columnDefinition = "float default 0")
    private float rating;
    @Column(name = "content", nullable = false, length = 3000)
    private String content;
    @CreationTimestamp
    @Column(name = "release_date", nullable = false, updatable = true)
    private Timestamp releaseDate;

    @ManyToOne
    @JoinColumn(name = "anime_id", referencedColumnName = "id", nullable = true)
    private AnimeEntity anime;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = true)
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = true)
    private UserEntity user;

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

    public AnimeEntity getAnime() {
        return anime;
    }

    public void setAnime(AnimeEntity anime) {
        this.anime = anime;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
