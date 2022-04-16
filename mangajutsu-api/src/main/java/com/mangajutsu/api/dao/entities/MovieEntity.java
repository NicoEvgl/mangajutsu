package com.mangajutsu.api.dao.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "movie", schema = "public")
public class MovieEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "title", nullable = false, length = 150)
    private String title;
    @Column(name = "title_vo", nullable = true, length = 150)
    private String titleVo;
    @Column(name = "origin", nullable = true, length = 50)
    private String origin;
    @Column(name = "rating", nullable = false, columnDefinition = "float default 0")
    private float rating;
    @Column(name = "release_date_vo", nullable = true, length = 50)
    private String releaseDateVo;
    @Column(name = "release_date", nullable = false, length = 50)
    private String releaseDate;
    @Column(name = "duration", nullable = true, length = 20)
    private String duration;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "genre", nullable = false)
    private String genre;
    @Column(name = "director", nullable = false, length = 50)
    private String director;
    @Column(name = "producer", nullable = true, length = 50)
    private String producer;
    @Column(name = "studios", nullable = false, length = 50)
    private String studios;
    @Column(name = "publisher_vod", nullable = true, length = 300)
    private String publisherVod;
    @Column(name = "publisher_vod_url", nullable = true)
    private String publisherVodUrl;
    @Column(name = "dealer_url", nullable = true)
    private String dealerUrl;
    @Column(name = "synopsis", nullable = false, length = 1000)
    private String synopsis;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = true)
    private UserEntity user;

    @JsonIgnore
    @OneToMany(mappedBy = "movie")
    private List<FileEntity> files;

    @JsonIgnore
    @OneToMany(mappedBy = "movie")
    private List<ReviewEntity> reviews;

    // Getters & Setters //

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleVo() {
        return titleVo;
    }

    public void setTitleVo(String titleVo) {
        this.titleVo = titleVo;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReleaseDateVo() {
        return releaseDateVo;
    }

    public void setReleaseDateVo(String releaseDateVo) {
        this.releaseDateVo = releaseDateVo;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public String getPublisherVod() {
        return publisherVod;
    }

    public void setPublisherVod(String publisherVod) {
        this.publisherVod = publisherVod;
    }

    public String getPublisherVodUrl() {
        return publisherVodUrl;
    }

    public void setPublisherVodUrl(String publisherVodUrl) {
        this.publisherVodUrl = publisherVodUrl;
    }

    public String getDealerUrl() {
        return dealerUrl;
    }

    public void setDealerUrl(String dealerUrl) {
        this.dealerUrl = dealerUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }

    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }
}
