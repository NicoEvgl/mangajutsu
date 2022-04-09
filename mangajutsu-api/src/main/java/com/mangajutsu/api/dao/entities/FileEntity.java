package com.mangajutsu.api.dao.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "file", schema = "public")
public class FileEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "file_name", unique = true, nullable = false, length = 150)
    private String fileName;
    @Column(name = "type", nullable = false, length = 25)
    private String type;
    @Column(name = "size", nullable = true)
    private long size;
    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "anime_id", referencedColumnName = "id", nullable = true)
    private AnimeEntity anime;

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

    public AnimeEntity getAnime() {
        return anime;
    }

    public void setAnime(AnimeEntity anime) {
        this.anime = anime;
    }
}
