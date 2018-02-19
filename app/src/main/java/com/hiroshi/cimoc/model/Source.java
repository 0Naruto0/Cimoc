package com.hiroshi.cimoc.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Hiroshi on 2016/8/11.
 */

@Entity(tableName = "source")
public class Source {

    @PrimaryKey @NonNull @ColumnInfo(name = "id") private String id;
    @ColumnInfo(name = "name") private String name;
    @ColumnInfo(name = "enable") private boolean enable;

    public Source(@NonNull String id, String name, boolean enable) {
        this.id = id;
        this.name = name;
        this.enable = enable;
    }

    @Ignore
    public Source(@NonNull String id, String name) {
        this(id, name, true);
    }

    public @NonNull String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
