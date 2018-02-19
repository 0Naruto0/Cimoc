package com.hiroshi.cimoc.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Hiroshi on 2016/10/10.
 */

@Entity(tableName = "tag")
public class Tag {

    @PrimaryKey(autoGenerate = true) private long id;
    @ColumnInfo(name = "name") private String name;

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    public Tag(String name) {
        this(0, name);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Tag && ((Tag) o).id == id;
    }

}
