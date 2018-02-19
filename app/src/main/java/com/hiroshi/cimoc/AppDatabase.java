package com.hiroshi.cimoc;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.hiroshi.cimoc.dao.ComicDao;
import com.hiroshi.cimoc.dao.SourceDao;
import com.hiroshi.cimoc.model.Comic;
import com.hiroshi.cimoc.model.Source;
import com.hiroshi.cimoc.model.Tag;
import com.hiroshi.cimoc.model.Task;

/**
 * Created by Hiroshi on 2017/8/21.
 */

@Database(entities = {Comic.class, Tag.class, Task.class, Source.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "cimoc";

    public abstract ComicDao getComicDao();

    public abstract SourceDao getSourceDao();

    public static AppDatabase build(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public void runInTx(TxRunnable runnable) throws Exception {
        App.getDatabase().beginTransaction();
        try {
            runnable.run();
            App.getDatabase().setTransactionSuccessful();
        } finally {
            App.getDatabase().endTransaction();
        }
    }

    public interface TxRunnable {
        void run() throws Exception;
    }

}
