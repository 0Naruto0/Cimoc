package com.hiroshi.cimoc.plugin;

/**
 * Created by Hiroshi on 2018/2/15.
 */

public class ComicDetail {

    private String title;
    private String cover;
    private String update;
    private String summary;
    private String author;
    private boolean completed;

    public ComicDetail(String title, String cover, String update, String summary, String author, boolean completed) {
        this.title = title;
        this.cover = cover;
        this.update = update;
        this.summary = summary;
        this.author = author;
        this.completed = completed;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
