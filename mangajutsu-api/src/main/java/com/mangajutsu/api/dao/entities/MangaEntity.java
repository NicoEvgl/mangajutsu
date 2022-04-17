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
@Table(name = "manga", schema = "public")
public class MangaEntity implements Serializable {
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
    @Column(name = "rating", nullable = false, columnDefinition = "float default 0")
    private float rating;
    @Column(name = "release_date_vo", nullable = true, length = 50)
    private String releaseDateVo;
    @Column(name = "end_date_vo", nullable = true, length = 50)
    private String endDateVo;
    @Column(name = "release_date", nullable = false, length = 50)
    private String releaseDate;
    @Column(name = "end_date", nullable = true, length = 50)
    private String endDate;
    @Column(name = "nb_chapters", nullable = false)
    private Integer nbChapters;
    @Column(name = "nb_tomes", nullable = true)
    private Integer nbTomes;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "genre", nullable = false)
    private String genre;
    @Column(name = "mangaka", nullable = false, length = 50)
    private String mangaka;
    @Column(name = "editor", nullable = true, length = 300)
    private String editor;
    @Column(name = "editor_url", nullable = true, length = 500)
    private String editorUrl;
    @Column(name = "editor_vo", nullable = true, length = 300)
    private String editorVo;
    @Column(name = "synopsis", nullable = false, length = 1500)
    private String synopsis;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = true)
    private UserEntity user;

    @JsonIgnore
    @OneToMany(mappedBy = "manga")
    private List<FileEntity> files;

    @JsonIgnore
    @OneToMany(mappedBy = "manga")
    private List<ReviewEntity> reviews;

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
