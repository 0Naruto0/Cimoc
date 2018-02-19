package com.hiroshi.cimoc.presenter;

import com.hiroshi.cimoc.core.Backup;
import com.hiroshi.cimoc.core.Download;
import com.hiroshi.cimoc.core.Manga;
import com.hiroshi.cimoc.manager.ComicManager;
import com.hiroshi.cimoc.manager.SourceManager;
import com.hiroshi.cimoc.manager.TagRefManager;
import com.hiroshi.cimoc.manager.TaskManager;
import com.hiroshi.cimoc.model.Chapter;
import com.hiroshi.cimoc.model.Comic;
import com.hiroshi.cimoc.model.ComicDetail;
import com.hiroshi.cimoc.model.Task;
import com.hiroshi.cimoc.rx.RxBus;
import com.hiroshi.cimoc.rx.RxEvent;
import com.hiroshi.cimoc.ui.view.DetailView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Hiroshi on 2016/7/4.
 */
public class DetailPresenter extends BasePresenter<DetailView> {

    private ComicManager mComicManager;
    private TaskManager mTaskManager;
    private TagRefManager mTagRefManager;
    private SourceManager mSourceManager;
    private Comic mComic;

    @Override
    protected void onViewAttach() {
        mComicManager = ComicManager.getInstance();
        mTaskManager = TaskManager.getInstance();
        mTagRefManager = TagRefManager.getInstance();
        mSourceManager = SourceManager.getInstance();
    }

    @Override
    protected void initSubscription() {
        addSubscription(RxEvent.EVENT_COMIC_UPDATE, new Action1<RxEvent>() {
            @Override
            public void call(RxEvent rxEvent) {
                // 可能存在多个详情界面
                if (rxEvent.getId() == mComic.getId()) {
                    mBaseView.onLastChapterUpdate(mComic.getLastChapterId());
                }
            }
        });
    }

    public void load(long id) {
        mComic = mComicManager.get(id);
        markAsRead();
        if (mComic.getDetail() != null) {
            updateChapterList(mComic.getDetail().getChapter());
            mBaseView.onDetailParseSuccess(mComic);
        } else {
            load();
        }
    }

    private void updateChapterList(List<Chapter> list) {
        List<Task> tasks = mTaskManager.list(mComic.getId());
        for (Chapter chapter : list) {
            for (Task task : tasks) {
                if (task.getChapterId().equals(chapter.getId())) {
                    chapter.setDownload(true);
                    chapter.setCount(task.getMax());
                    chapter.setCompleted(task.isCompleted());
                    break;
                }
            }
        }
    }

    private void load() {
        mCompositeSubscription.add(Manga.getComicInfo(mComic.getSourceId(), mComic.getRemoteId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ComicDetail>() {
                    @Override
                    public void call(ComicDetail detail) {
                        mComic.setTitle(mComic.getDetail().getTitle());
                        mComic.setCover(mComic.getDetail().getCover());
                        mComic.setUpdate(mComic.getDetail().getUpdate());
                        mComic.setCompleted(mComic.getDetail().isCompleted());

                        if (mComic.getId() > 0) {
                            mComicManager.update(mComic);
                        }

                        updateChapterList(detail.getChapter());
                        mBaseView.onDetailParseSuccess(mComic);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mBaseView.onDetailParseFailed();
                    }
                }));
    }

    private void markAsRead() {
        if (mComic.isUnread()) {
            mComic.setUnread(false);
            mComic.setFavoriteTime(System.currentTimeMillis());
            mComicManager.update(mComic);
            RxBus.getInstance().post(new RxEvent(RxEvent.EVENT_COMIC_MARK_AS_READ, mComic.getId()));
        }
    }

    public long updateComic(Chapter chapter) {
        if (mComic.getId() != 0) {
            mComic.setFavoriteTime(System.currentTimeMillis());
        }
        mComic.setHistoryTime(System.currentTimeMillis());

        if (!chapter.getId().equals(mComic.getLastChapterId())) {
            mComic.setLastChapterId(chapter.getId());
            mComic.setLastChapterTitle(chapter.getTitle());
            mComic.setLastPage(1);
        }

        if (mComic.getId() < 0)  {
            mComicManager.insert(mComic);
        } else {
            mComicManager.update(mComic);
        }

        RxBus.getInstance().post(new RxEvent(RxEvent.EVENT_COMIC_READ, mComic.getId()));
        return mComic.getId();
    }

    public Comic getComic() {
        return mComic;
    }

    public void backup() {
        // TODO: 重写
        mComicManager.listBackup()
                .doOnNext(new Action1<List<Comic>>() {
                    @Override
                    public void call(List<Comic> list) {
                        Backup.saveComicAuto(list);
                    }
                })
                .subscribe();
    }

    public void favoriteComic() {
        mComic.setFavoriteTime(System.currentTimeMillis());

        if (mComic.getId() < 0) {
            mComicManager.insert(mComic);
        } else {
            mComicManager.update(mComic);
        }

        RxBus.getInstance().post(new RxEvent(RxEvent.EVENT_COMIC_FAVORITE, mComic.getId()));
    }

    public void unfavoriteComic() {
        mComic.setFavoriteTime(0);
        // FIXME: 更新标签 tag ref
        mComicManager.update(mComic);
        RxBus.getInstance().post(new RxEvent(RxEvent.EVENT_COMIC_UNFAVORITE, mComic.getId()));
    }

    private ArrayList<Task> getTaskList(List<Chapter> list) {
        ArrayList<Task> result = new ArrayList<>(list.size());
        for (Chapter chapter : list) {
            Task task = new Task(null, -1, chapter.getPath(), chapter.getTitle(), 0, 0);
            task.setSource(mComic.getSource());
            task.setCid(mComic.getCid());
            task.setState(Task.STATE_WAIT);
            result.add(task);
        }
        return result;
    }

    /**
     * 添加任务到数据库
     * @param cList 所有章节列表，用于写索引文件
     * @param dList 下载章节列表
     */
    public void addTask(final List<Chapter> cList, final List<Chapter> dList) {
        mCompositeSubscription.add(Observable.create(new Observable.OnSubscribe<ArrayList<Task>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Task>> subscriber) {
                final ArrayList<Task> result = getTaskList(dList);
                mComic.setDownload(System.currentTimeMillis());
                mComicManager.runInTx(new Runnable() {
                    @Override
                    public void run() {
                        mComicManager.updateOrInsert(mComic);
                        for (Task task : result) {
                            task.setKey(mComic.getId());
                            mTaskManager.insert(task);
                        }
                    }
                });
                Download.updateComicIndex(cList, mComic);
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<Task>>() {
                    @Override
                    public void call(ArrayList<Task> list) {
                        RxBus.getInstance().post(new RxEvent(RxEvent.EVENT_TASK_INSERT, new MiniComic(mComic), list));
                        mBaseView.onTaskAddSuccess(list);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        mBaseView.onTaskAddFail();
                    }
                }));
    }

}
