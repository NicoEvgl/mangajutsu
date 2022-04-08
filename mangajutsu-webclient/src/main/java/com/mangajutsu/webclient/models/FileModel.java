package com.mangajutsu.webclient.models;

import javax.validation.constraints.NotEmpty;

public class FileModel {
    private Integer id;
    @NotEmpty(message = "{upload-file.validation.fileName}")
    private String fileName;
    @NotEmpty(message = "{upload-file.validation.type}")
    private String type;
    private long size;
    @NotEmpty(message = "{upload-file.validation.url}")
    private String url;

    private AnimeModel anime;

    // Getters & Setters //

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

    public AnimeModel getAnime() {
        return anime;
    }

    public void setAnime(AnimeModel anime) {
        this.anime = anime;
    }
}
