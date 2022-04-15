package com.mangajutsu.webclient.models;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class MovieModel implements Serializable {
    private Integer id;
    @NotEmpty(message = "{add-movie.valid.title}")
    private String title;
    private String titleVo;
    private String origin;
    private float rating;
    @NotEmpty(message = "{add-movie.valid.release-date}")
    private String releaseDate;
    @NotEmpty(message = "{add-movie.valid.duration}")
    private String duration;
    @NotEmpty(message = "{add-movie.valid.type}")
    private String type;
    @NotEmpty(message = "{add-movie.valid.genre}")
    private String genre;
    @NotEmpty(message = "{add-movie.valid.director}")
    private String director;
    private String producer;
    @NotEmpty(message = "{add-movie.valid.studios}")
    private String studios;
    private String publisherVod;
    private String publisherVodUrl;
    private String dealerUrl;
    @NotEmpty(message = "{add-movie.valid.synopsis}")
    @Size(min = 1, max = 1000, message = "{add-anime.valid.synopsis-size}")
    private String synopsis;

    private UserModel user;
    private List<FileModel> files;
    private List<ReviewModel> reviews;

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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<FileModel> getFiles() {
        return files;
    }

    public void setFiles(List<FileModel> files) {
        this.files = files;
    }

    public List<ReviewModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewModel> reviews) {
        this.reviews = reviews;
    }
}
