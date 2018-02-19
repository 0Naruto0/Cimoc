package com.hiroshi.cimoc.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hiroshi.cimoc.model.Source;

import java.util.List;

/**
 * Created by Hiroshi on 2017/8/23.
 */

@Dao
public interface SourceDao {

    @Query("SELECT * FROM source")
    List<Source> list();

    // TODO 这个之后删掉
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Source source);

    @Update
    void update(Source source);

    @Delete
    void delete(Source source);

}
