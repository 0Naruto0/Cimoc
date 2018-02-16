package com.hiroshi.cimoc.model;

/**
 * Created by Hiroshi on 2018/2/15.
 */

public class SearchResult {

    private String sourceId;
    private String comicId;
    private String title;
    private String cover;
    private String update;
    private String author;

    public SearchResult(String sourceId, String comicId, String title, String cover, String update, String author) {
        this.sourceId = sourceId;
        this.comicId = comicId;
        this.title = title;
        this.cover = cover;
        this.update = update;
        this.author = author;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getComicId() {
        return comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
