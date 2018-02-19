package com.hiroshi.cimoc.model;

import java.util.List;

/**
 * Created by Hiroshi on 2017/10/7.
 */

public class ComicDetail {

    private String title;
    private String cover;
    private String update;
    private String author;
    private String intro;
    private boolean completed;
    private List<Chapter> chapter;

    public ComicDetail(String title, String cover, String update, String author, String intro, boolean completed) {
        this.title = title;
        this.cover = cover;
        this.update = update;
        this.author = author;
        this.intro = intro;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<Chapter> getChapter() {
        return chapter;
    }

    public void setChapter(List<Chapter> chapter) {
        this.chapter = chapter;
    }

}
