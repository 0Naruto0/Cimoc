package com.hiroshi.cimoc.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Hiroshi on 2016/7/20.
 */

@Entity(tableName = "comic")
public class Comic {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") private long id;
    @ColumnInfo(name = "source_id") private String sourceId;
    @ColumnInfo(name = "remote_id") private String remoteId;
    @ColumnInfo(name = "title") private String title;
    @ColumnInfo(name = "cover") private String cover;
    @ColumnInfo(name = "last_chapter_id") private String lastChapterId;
    @ColumnInfo(name = "last_chapter_title") private String lastChapterTitle;
    @ColumnInfo(name = "last_page") private int lastPage;

    @ColumnInfo(name = "favorite_time") private long favoriteTime;
    @ColumnInfo(name = "update") private String update;
    @ColumnInfo(name = "completed") private boolean completed;
    @ColumnInfo(name = "unread") private boolean unread;

    @ColumnInfo(name = "history_time") private long historyTime;
    @ColumnInfo(name = "download_time") private long downloadTime;

    @Ignore private ComicDetail detail;

    public Comic(String sourceId, String remoteId) {
        this.sourceId = sourceId;
        this.remoteId = remoteId;
    }

    public Comic(long id, String sourceId, String remoteId, String title, String cover,
                 String lastChapterId, String lastChapterTitle, int lastPage,
                 long favoriteTime, String update, boolean completed, boolean unread,
                 long historyTime, long downloadTime) {
        this.id = id;
        this.sourceId = sourceId;
        this.remoteId = remoteId;
        this.title = title;
        this.cover = cover;
        this.lastChapterId = lastChapterId;
        this.lastChapterTitle = lastChapterTitle;
        this.lastPage = lastPage;
        this.favoriteTime = favoriteTime;
        this.update = update;
        this.completed = completed;
        this.unread = unread;
        this.historyTime = historyTime;
        this.downloadTime = downloadTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
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

    public String getLastChapterId() {
        return lastChapterId;
    }

    public void setLastChapterId(String lastChapterId) {
        this.lastChapterId = lastChapterId;
    }

    public String getLastChapterTitle() {
        return lastChapterTitle;
    }

    public void setLastChapterTitle(String lastChapterTitle) {
        this.lastChapterTitle = lastChapterTitle;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public long getFavoriteTime() {
        return favoriteTime;
    }

    public void setFavoriteTime(long favoriteTime) {
        this.favoriteTime = favoriteTime;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public long getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(long historyTime) {
        this.historyTime = historyTime;
    }

    public long getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(long downloadTime) {
        this.downloadTime = downloadTime;
    }

    public ComicDetail getDetail() {
        return detail;
    }

    public void setDetail(ComicDetail detail) {
        this.detail = detail;
    }

}
