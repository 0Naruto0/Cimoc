package com.hiroshi.cimoc.model;

/**
 * Created by Hiroshi on 2016/7/2.
 */
public class Chapter {

    private String id;
    private String title;
    private int count;
    private boolean completed;
    private boolean download;

    public Chapter(String id, String title, int count, boolean completed, boolean download) {
        this.id = id;
        this.title = title;
        this.count = count;
        this.completed = completed;
        this.download = download;
    }

    public Chapter(String id, String title) {
        // FIXME 这里反过来了 记得改啊！！！！！！！！！
        this(id, title, 0, false, false);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

}
