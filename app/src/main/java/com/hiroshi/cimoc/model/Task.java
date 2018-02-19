package com.hiroshi.cimoc.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hiroshi on 2016/9/1.
 */

@Entity(tableName = "task")
public class Task implements Parcelable {

    public static final int STATE_FINISH = 0;
    public static final int STATE_PAUSE = 1;
    public static final int STATE_PARSE = 2;
    public static final int STATE_DOING = 3;
    public static final int STATE_WAIT = 4;
    public static final int STATE_ERROR = 5;

    @PrimaryKey(autoGenerate = true) private long id;
    @ColumnInfo(name = "comic_id") private long comicId;
    @ColumnInfo(name = "chapter_id") private String chapterId;
    @ColumnInfo(name = "title") private String title;
    @ColumnInfo(name = "progress") private int progress;
    @ColumnInfo(name = "max") private int max;

    @Ignore private int state;

    public Task(long id, long comicId, String chapterId, String title, int progress, int max) {
        this.id = id;
        this.comicId = comicId;
        this.chapterId = chapterId;
        this.title = title;
        this.progress = progress;
        this.max = max;
        this.state = STATE_WAIT;
    }

    @Ignore
    public Task(long comicId, String chapterId, String title) {
        this(0, comicId, chapterId, title, 0, 0);
    }

    @Ignore
    public Task(String chapterId, String title) {
        this(0, 0, chapterId, title, 0, 0);
    }

    @Ignore
    public Task(Parcel source) {
        this(source.readLong(), source.readLong(), source.readString(),
                source.readString(), source.readInt(), source.readInt());
        this.state = source.readInt();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getComicId() {
        return comicId;
    }

    public void setComicId(long comicId) {
        this.comicId = comicId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isCompleted() {
        return max != 0 && progress == max;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Task && ((Task) o).id == id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(comicId);
        dest.writeString(chapterId);
        dest.writeString(title);
        dest.writeInt(progress);
        dest.writeInt(max);
        dest.writeInt(state);
    }

    public final static Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

}
