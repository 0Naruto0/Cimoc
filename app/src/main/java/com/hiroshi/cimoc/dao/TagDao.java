package com.hiroshi.cimoc.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.hiroshi.cimoc.model.Tag;

import java.util.List;

/**
 * Created by Hiroshi on 2017/8/23.
 */

@Dao
public interface TagDao {

    @Query("SELECT * FROM tag")
    List<Tag> list();

    @Query("SELECT * FROM tag WHERE id = :id LIMIT 1")
    Tag get(long id);

    @Query("SELECT * FROM tag WHERE name = :name LIMIT 1")
    Tag get(String name);

    @Insert
    long insert(Tag tag);

    @Delete
    void delete(Tag tag);

}
