package com.mangajutsu.webclient.models;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

public class AnimeModel implements Serializable {

    private Integer id;
    @NotEmpty(message = "{add-anime.valid.title}")
    private String title;
    private String titleVo;
    private String origin;
    @NotEmpty(message = "{add-anime.valid.status}")
    private String status;
    @NotEmpty(message = "{add-anime.valid.releaseDate}")
    private String releaseDate;
    private String endDate;
    private Integer nbEpisodes;
    private String duration;
    private String type;
    private String genre;
    @NotEmpty(message = "{add-anime.valid.mangaka}")
    private String mangaka;
    private String producer;
    @NotEmpty(message = "{add-anime.valid.studios}")
    private String studios;
    private String publisher;
    private String publisherUrl;
    private String publisherVod;
    private String publisherVodUrl;
    @NotEmpty(message = "{add-anime.valid.synopsis}")
    private String synopsis;

    private UserModel user;
    private List<FileModel> files;
    private Set<ReviewModel> reviews;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getNbEpisodes() {
        return nbEpisodes;
    }

    public void setNbEpisodes(Integer nbEpisodes) {
        this.nbEpisodes = nbEpisodes;
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

    public String getMangaka() {
        return mangaka;
    }

    public void setMangaka(String mangaka) {
        this.mangaka = mangaka;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherUrl() {
        return publisherUrl;
    }

    public void setPublisherUrl(String publisherUrl) {
        this.publisherUrl = publisherUrl;
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

    public Set<ReviewModel> getReviews() {
        return reviews;
    }

    public void setReviews(Set<ReviewModel> reviews) {
        this.reviews = reviews;
    }
}
