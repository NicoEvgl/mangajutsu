package com.mangajutsu.api.dao.entities;

import java.io.Serializable;
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

@Entity
@Table(name = "anime", schema = "public")
public class AnimeEntity implements Serializable {
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
    @Column(name = "status", nullable = false, length = 25)
    private String status;
    @Column(name = "release_date", nullable = false, length = 50)
    private String releaseDate;
    @Column(name = "end_date", nullable = true, length = 50)
    private String endDate;
    @Column(name = "nb_episodes", nullable = false)
    private Integer nbEpisodes;
    @Column(name = "duration", nullable = true, length = 20)
    private String duration;
    @Column(name = "type", nullable = true)
    private String type;
    @Column(name = "genre", nullable = true)
    private String genre;
    @Column(name = "mangaka", nullable = false, length = 50)
    private String mangaka;
    @Column(name = "producer", nullable = true, length = 50)
    private String producer;
    @Column(name = "studios", nullable = false, length = 50)
    private String studios;
    @Column(name = "synopsis", nullable = false, length = 1000)
    private String synopsis;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private UserEntity user;

    @JsonIgnore
    @OneToMany(mappedBy = "anime")
    private List<FileEntity> files;

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

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
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
}
