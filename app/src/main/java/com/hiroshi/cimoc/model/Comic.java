package com.hiroshi.cimoc.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by Hiroshi on 2016/7/20.
 */
@Entity
public class Comic {

    @Id(autoincrement = true) private Long id;
    @NotNull private String sourceId;
    @NotNull private String cid;
    @NotNull private String title;
    @NotNull private String cover;
    @NotNull private boolean highlight;
    @NotNull private boolean local;
    private String update;
    private Boolean finish;
    private Long favorite;
    private Long history;
    private Long download;
    private String last;
    private Integer page;
    private String chapter;

    @Transient private String intro;
    @Transient private String author;

    public Comic(String sourceId, String cid, String title, String cover, String update, String author) {
        this(null, sourceId, cid, title, cover == null ? "" : cover, false, false, update,
                null, null, null, null, null, null, null);
        this.author = author;
    }

    public Comic(String sourceId, String cid) {
        this.sourceId = sourceId;
        this.cid = cid;
    }

    public Comic(String sourceId, String cid, String title, String cover, long download) {
        this(null, sourceId, cid, title, cover == null ? "" : cover, false, false, null,
                null, null, null, download, null, null, null);
    }

    @Generated(hash = 133873703)
    public Comic(Long id, @NotNull String sourceId, @NotNull String cid, @NotNull String title,
            @NotNull String cover, boolean highlight, boolean local, String update, Boolean finish, Long favorite,
            Long history, Long download, String last, Integer page, String chapter) {
        this.id = id;
        this.sourceId = sourceId;
        this.cid = cid;
        this.title = title;
        this.cover = cover;
        this.highlight = highlight;
        this.local = local;
        this.update = update;
        this.finish = finish;
        this.favorite = favorite;
        this.history = history;
        this.download = download;
        this.last = last;
        this.page = page;
        this.chapter = chapter;
    }

    @Generated(hash = 1347984162)
    public Comic() {
    }



    @Override
    public boolean equals(Object o) {
        return o instanceof Comic && ((Comic) o).id.equals(id);
    }

    public void setInfo(String title, String cover, String update, String intro, String author, boolean finish) {
        if (title != null) {
            this.title = title;
        }
        if (cover != null) {
            this.cover = cover;
        }
        if (update != null) {
            this.update = update;
        }
        this.intro = intro;
        if (author != null) {
            this.author = author;
        }
        this.finish = finish;
        this.highlight = false;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Long getHistory() {
        return history;
    }

    public void setHistory(Long history) {
        this.history = history;
    }

    public Long getFavorite() {
        return favorite;
    }

    public void setFavorite(Long favorite) {
        this.favorite = favorite;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSource() {
        return sourceId;
    }

    public void setSource(String sourceId) {
        this.sourceId = sourceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public Long getDownload() {
        return download;
    }

    public void setDownload(Long download) {
        this.download = download;
    }

    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    public boolean getLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

}
