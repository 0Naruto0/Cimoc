package com.hiroshi.cimoc.rx;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Hiroshi on 2016/8/21.
 */
public class RxEvent {

    public static final int EVENT_COMIC_FAVORITE = 1;
    public static final int EVENT_COMIC_UNFAVORITE = 2;
    public static final int EVENT_COMIC_READ = 3;
    public static final int EVENT_COMIC_UPDATE = 4;
    public static final int EVENT_COMIC_RESTORE = 5;
    public static final int EVENT_COMIC_MARK_AS_READ = 6;

    public static final int EVENT_TASK_PARSE = 21;
    public static final int EVENT_TASK_INIT = 22;
    public static final int EVENT_TASK_ERROR = 23;
    public static final int EVENT_TASK_PAUSE = 24;
    public static final int EVENT_TASK_PROCESS = 25;
    public static final int EVENT_TASK_INSERT = 26;
    public static final int EVENT_TASK_DELETE = 27;

    public static final int EVENT_DOWNLOAD_REMOVE = 41;

    public static final int EVNET_LOCAL_DELETE = 61;
    public static final int EVNET_LOCAL_CHAPTER_DELETE = 62;

    public static final int EVENT_TAG_UPDATE = 81;
    public static final int EVENT_TAG_RESTORE = 82;

    public static final int EVENT_DIALOG_PROGRESS = 101;

    public static final int EVENT_PICTURE_PAGING = 121;

    public static final int EVENT_SWITCH_NIGHT = 141;

    @IntDef({EVENT_COMIC_FAVORITE, EVENT_COMIC_UNFAVORITE, EVENT_COMIC_READ, EVENT_COMIC_UPDATE,
            EVENT_COMIC_RESTORE, EVENT_COMIC_MARK_AS_READ, EVENT_TASK_PARSE, EVENT_TASK_INIT, EVENT_TASK_ERROR, EVENT_TASK_PAUSE, EVENT_TASK_PROCESS,
            EVENT_TASK_INSERT, EVENT_DOWNLOAD_REMOVE, EVENT_TAG_UPDATE,
            EVENT_TAG_RESTORE, EVENT_DIALOG_PROGRESS, EVENT_PICTURE_PAGING, EVENT_SWITCH_NIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {}

    private int type;
    private long id;
    private String msg;

    public RxEvent(@EventType int type) {
        this(type,0, null);
    }

    public RxEvent(@EventType int type, String msg) {
        this(type,0, msg);
    }

    public RxEvent(@EventType int type, long id) {
        this(type, id, null);
    }

    public RxEvent(@EventType int type, long id, String msg) {
        this.type = type;
        this.id = id;
        this.msg = msg;
    }

    public @EventType int getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

}
