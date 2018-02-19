package com.hiroshi.cimoc.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hiroshi.cimoc.model.Comic;

import java.util.List;

/**
 * Created by Hiroshi on 2017/8/21.
 */

@Dao
public interface ComicDao {

    @Query("SELECT * FROM comic")
    List<Comic> list();

    @Insert
    long insert(Comic comic);

    @Update
    void update(Comic comic);

    @Query("UPDATE comic SET history_time = 0 WHERE history_time != 0")
    void clearHistory();

    @Query("UPDATE comic SET unread = 0 WHERE unread = 1")
    void markAsUnread();

}
