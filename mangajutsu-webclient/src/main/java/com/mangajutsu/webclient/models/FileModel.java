package com.mangajutsu.webclient.models;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.springframework.core.io.Resource;

public class FileModel implements Serializable {
    private Integer id;
    @NotEmpty(message = "{upload-file.valid.fileName}")
    private String fileName;
    @NotEmpty(message = "{upload-file.valid.type}")
    private String type;
    private long size;
    @NotEmpty(message = "{upload-file.valid.url}")
    private String url;

    private Resource resource;
    private AnimeModel anime;
    private MovieModel movie;

    // Getters & Setters //

    public FileModel() {
    }

    public FileModel(String fileName, String type, long size, String url) {
        this.fileName = fileName;
        this.type = type;
        this.size = size;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public AnimeModel getAnime() {
        return anime;
    }

    public void setAnime(AnimeModel anime) {
        this.anime = anime;
    }

    public MovieModel getMovie() {
        return movie;
    }

    public void setMovie(MovieModel movie) {
        this.movie = movie;
    }
}
