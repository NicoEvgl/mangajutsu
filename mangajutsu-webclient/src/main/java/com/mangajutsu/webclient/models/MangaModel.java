package com.mangajutsu.webclient.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MangaModel implements Serializable {
    private Integer id;
    @NotEmpty(message = "{add-manga.valid.title}")
    private String title;
    private String titleVo;
    private String origin;
    @NotEmpty(message = "{add-manga.valid.status}")
    private String status;
    private float rating;
    private String releaseDateVo;
    private String endDateVo;
    @NotEmpty(message = "{add-manga.valid.release-date}")
    private String releaseDate;
    private String endDate;
    @NotNull(message = "{add-manga.valid.nb-chapters}")
    private Integer nbChapters;
    private Integer nbTomes;
    @NotEmpty(message = "{add-manga.valid.type}")
    private String type;
    @NotEmpty(message = "{add-manga.valid.genre}")
    private String genre;
    @NotEmpty(message = "{add-manga.valid.mangaka}")
    private String mangaka;
    private String editor;
    private String editorUrl;
    private String editorVo;
    @NotEmpty(message = "{add-manga.valid.synopsis}")
    @Size(min = 1, max = 1000, message = "{add-manga.valid.synopsis-size}")
    private String synopsis;
    private Timestamp createdAt;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getEndDateVo() {
        return endDateVo;
    }

    public void setEndDateVo(String endDateVo) {
        this.endDateVo = endDateVo;
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

    public Integer getNbChapters() {
        return nbChapters;
    }

    public void setNbChapters(Integer nbChapters) {
        this.nbChapters = nbChapters;
    }

    public Integer getNbTomes() {
        return nbTomes;
    }

    public void setNbTomes(Integer nbTomes) {
        this.nbTomes = nbTomes;
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

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getEditorUrl() {
        return editorUrl;
    }

    public void setEditorUrl(String editorUrl) {
        this.editorUrl = editorUrl;
    }

    public String getEditorVo() {
        return editorVo;
    }

    public void setEditorVo(String editorVo) {
        this.editorVo = editorVo;
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
