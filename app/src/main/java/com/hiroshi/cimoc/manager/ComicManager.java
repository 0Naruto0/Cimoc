package com.hiroshi.cimoc.manager;

import com.hiroshi.cimoc.App;
import com.hiroshi.cimoc.dao.ComicDao;
import com.hiroshi.cimoc.misc.IterableLongSparseArray;
import com.hiroshi.cimoc.model.Comic;
import com.hiroshi.cimoc.model.TagRef;

import java.util.Collection;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Hiroshi on 2016/7/9.
 */
public class ComicManager {

    private static ComicManager mInstance;

    private ComicDao mComicDao;
    private long mTempId = -1;

    // FIXME 可能存在并发修改
    private IterableLongSparseArray<Comic> mComicHash;

    private ComicManager() {
        mComicDao = App.getDatabase().getComicDao();
        Observable.from(mComicDao.list()).forEach(new Action1<Comic>() {
            @Override
            public void call(Comic comic) {
                mComicHash.put(comic.getId(), comic);
            }
        });
    }

    public Observable<List<Comic>> listFavorite() {
        return Observable.from(mComicHash).filter(new Func1<Comic, Boolean>() {
            @Override
            public Boolean call(Comic comic) {
                return comic.getFavoriteTime() != 0;
            }
        }).toList();
    }

    public Observable<List<Comic>> listOngoing() {
        return Observable.from(mComicHash).filter(new Func1<Comic, Boolean>() {
            @Override
            public Boolean call(Comic comic) {
                return comic.getFavoriteTime() != 0 && !comic.isCompleted();
            }
        }).toList();
    }

    public Observable<List<Comic>> listCompleted() {
        return Observable.from(mComicHash).filter(new Func1<Comic, Boolean>() {
            @Override
            public Boolean call(Comic comic) {
                return comic.getFavoriteTime() != 0 && comic.isCompleted();
            }
        }).toList();
    }

    public Observable<List<Comic>> listHistory() {
        return Observable.from(mComicHash).filter(new Func1<Comic, Boolean>() {
            @Override
            public Boolean call(Comic comic) {
                return comic.getHistoryTime() != 0;
            }
        }).toList();
    }

    public Observable<List<Comic>> listDownload() {
        return Observable.from(mComicHash).filter(new Func1<Comic, Boolean>() {
            @Override
            public Boolean call(Comic comic) {
                return comic.getDownloadTime() != 0;
            }
        }).toList();
    }

    public Observable<List<Comic>> listBackup() {
        return Observable.from(mComicHash).filter(new Func1<Comic, Boolean>() {
            @Override
            public Boolean call(Comic comic) {
                // 包括已删除漫画
                return comic.getDownloadTime() == 0;
            }
        }).toList();
    }

    public Observable<List<Comic>> listFavoriteByTagId(long id) {
        QueryBuilder<Comic> queryBuilder = mComicDao.queryBuilder();
        queryBuilder.join(TagRef.class, TagRefDao.Properties.Cid).where(TagRefDao.Properties.Tid.eq(id));
        return queryBuilder.orderDesc(Properties.Highlight, Properties.Favorite)
                .rx()
                .list();
    }

    public Observable<List<Comic>> listFavoriteNotIn(Collection<Long> collections) {
        return mComicDao.queryBuilder()
                .where(Properties.Favorite.isNotNull(), Properties.Id.notIn(collections))
                .rx()
                .list();
    }

    public long countBySourceId(final String sourceId) {
        long count = 0;
        for (Comic comic : mComicHash) {
            if (comic.getSourceId().equals(sourceId)) {
                ++count;
            }
        }
        return count;
    }

    public Comic get(long id) {
        return mComicHash.get(id);
    }

    public Comic get(String sourceId, String remoteId) {
        for (Comic comic : mComicHash) {
            if (comic.getSourceId().equals(sourceId) && comic.getRemoteId().equals(remoteId)) {
                return comic;
            }
        }
        return null;
    }

    // 修改操作

    public void insertTemp(Comic comic) {
        comic.setId(mTempId);
        mComicHash.put(mTempId, comic);
        --mTempId;
    }

    public void insert(Comic comic) {
        long id = mComicDao.insert(comic);
        comic.setId(id);
        mComicHash.put(id, comic);
    }

    public void update(Comic comic) {
        mComicDao.update(comic);
    }

    public void clearHistory() {
        mComicDao.clearHistory();
        Observable.from(mComicHash).forEach(new Action1<Comic>() {
            @Override
            public void call(Comic comic) {
                comic.setHistoryTime(0);
            }
        });
    }

    public void markAsRead() {
        mComicDao.markAsUnread();
        Observable.from(mComicHash).forEach(new Action1<Comic>() {
            @Override
            public void call(Comic comic) {
                comic.setUnread(false);
            }
        });
    }

    public static ComicManager getInstance() {
        if (mInstance == null) {
            synchronized (ComicManager.class) {
                if (mInstance == null) {
                    mInstance = new ComicManager();
                }
            }
        }
        return mInstance;
    }

}
