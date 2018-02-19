package com.hiroshi.cimoc.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.hiroshi.cimoc.R;
import com.hiroshi.cimoc.fresco.ControllerBuilderSupplierFactory;
import com.hiroshi.cimoc.fresco.ImagePipelineFactoryBuilder;
import com.hiroshi.cimoc.global.Extra;
import com.hiroshi.cimoc.manager.PreferenceManager;
import com.hiroshi.cimoc.manager.SourceManager;
import com.hiroshi.cimoc.model.Chapter;
import com.hiroshi.cimoc.model.Comic;
import com.hiroshi.cimoc.model.Task;
import com.hiroshi.cimoc.presenter.BasePresenter;
import com.hiroshi.cimoc.presenter.DetailPresenter;
import com.hiroshi.cimoc.service.DownloadService;
import com.hiroshi.cimoc.ui.adapter.BaseAdapter;
import com.hiroshi.cimoc.ui.adapter.DetailAdapter;
import com.hiroshi.cimoc.ui.view.DetailView;
import com.hiroshi.cimoc.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.OnClick;

/**
 * Created by Hiroshi on 2016/7/2.
 */
public class DetailActivity extends CoordinatorActivity implements DetailView {

    public static final int REQUEST_CODE_DOWNLOAD = 0;

    private DetailAdapter mDetailAdapter;
    private DetailPresenter mPresenter;
    private ImagePipelineFactory mImagePipelineFactory;

    private boolean mAutoBackup;
    private int mBackupCount;

    @Override
    protected BasePresenter initPresenter() {
        mPresenter = new DetailPresenter();
        mPresenter.attachView(this);
        return mPresenter;
    }

    @Override
    protected BaseAdapter initAdapter() {
        mDetailAdapter = new DetailAdapter(this, new ArrayList<Chapter>());
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        return mDetailAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager initLayoutManager() {
        return new GridLayoutManager(this, 4);
    }

    @Override
    protected void initData() {
        mAutoBackup = mPreference.getBoolean(PreferenceManager.PREF_BACKUP_SAVE_COMIC, true);
        mBackupCount = mPreference.getInt(PreferenceManager.PREF_BACKUP_SAVE_COMIC_COUNT, 0);
        long id = getIntent().getLongExtra(Extra.EXTRA_ID, 0);
        mPresenter.load(id);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAutoBackup) {
            mPreference.putInt(PreferenceManager.PREF_BACKUP_SAVE_COMIC_COUNT, mBackupCount);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImagePipelineFactory != null) {
            mImagePipelineFactory.getImagePipeline().clearMemoryCaches();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        if (!isProgressBarShown()) {
            switch (item.getItemId()) {
                case R.id.detail_history:
                    startReader(mDetailAdapter.getLastChapter());
                    break;
                case R.id.detail_download:
                    intent = ChapterActivity.createIntent(this, new ArrayList<>(mDetailAdapter.getDateSet()));
                    startActivityForResult(intent, REQUEST_CODE_DOWNLOAD);
                    break;
                case R.id.detail_tag:
                    if (mPresenter.getComic().getFavoriteTime() != 0) {
                        intent = TagEditorActivity.createIntent(this, mPresenter.getComic().getId());
                        startActivity(intent);
                    } else {
                        showSnackbar(R.string.detail_tag_favorite);
                    }
                    break;
                case R.id.detail_search_title:
                    if (!StringUtils.isEmpty(mDetailAdapter.getTitle())) {
                        intent = ResultActivity.createIntent(this, mDetailAdapter.getTitle(), ResultActivity.LAUNCH_MODE_SEARCH);
                        startActivity(intent);
                    } else {
                        showSnackbar(R.string.common_keyword_empty);
                    }
                    break;
                case R.id.detail_search_author:
                    if (!StringUtils.isEmpty(mDetailAdapter.getAuthor())) {
                        intent = ResultActivity.createIntent(this, mDetailAdapter.getAuthor(), ResultActivity.LAUNCH_MODE_SEARCH);
                        startActivity(intent);
                    } else {
                        showSnackbar(R.string.common_keyword_empty);
                    }
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_DOWNLOAD:
                    showProgressDialog();
                    List<Chapter> list = data.getParcelableArrayListExtra(Extra.EXTRA_CHAPTER);
                    mPresenter.addTask(mDetailAdapter.getDateSet(), list);
                    break;
            }
        }
    }

    @OnClick(R.id.coordinator_action_button) void onActionButtonClick() {
        if (mPresenter.getComic().getFavoriteTime() != 0) {
            mPresenter.unfavoriteComic();
            increment();
            mActionButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
            showSnackbar(R.string.detail_unfavorite);
        } else {
            mPresenter.favoriteComic();
            increment();
            mActionButton.setImageResource(R.drawable.ic_favorite_white_24dp);
            showSnackbar(R.string.detail_favorite);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position != 0) {
            startReader(mDetailAdapter.getItem(position - 1));
        }
    }

    private void startReader(Chapter chapter) {
        long id = mPresenter.updateComic(chapter);
        mDetailAdapter.setChapterId(chapter.getId());
        int mode = mPreference.getInt(PreferenceManager.PREF_READER_MODE, PreferenceManager.READER_MODE_PAGE);
        Intent intent = ReaderActivity.createIntent(DetailActivity.this, id, mDetailAdapter.getDateSet(), mode);
        startActivity(intent);
    }

    @Override
    public void onLastChapterUpdate(String chapterId) {
        mDetailAdapter.setChapterId(chapterId);
    }

    @Override
    public void onTaskAddSuccess(ArrayList<Task> list) {
        Intent intent = DownloadService.createIntent(this, list);
        startService(intent);
        updateChapterList(list);
        showSnackbar(R.string.detail_download_queue_success);
        hideProgressDialog();
    }

    private void updateChapterList(List<Task> list) {
        Set<String> set = new HashSet<>();
        for (Task task : list) {
            set.add(task.getPath());
        }
        for (Chapter chapter : mDetailAdapter.getDateSet()) {
            if (set.contains(chapter.getPath())) {
                chapter.setDownload(true);
            }
        }
    }

    @Override
    public void onTaskAddFailed() {
        hideProgressDialog();
        showSnackbar(R.string.detail_download_queue_fail);
    }

    @Override
    public void onDetailParseSuccess(Comic comic) {
        hideProgressBar();

        // 保证 title != null
        // 保证 !chapter.isEmpty()
        mDetailAdapter.setDetail(comic.getDetail(), comic.getLastChapterId());
        mDetailAdapter.addAll(comic.getDetail().getChapter());

        mImagePipelineFactory = ImagePipelineFactoryBuilder.build(this,
                SourceManager.getInstance().getParser(comic.getSourceId()).getHeader(), false);
        mDetailAdapter.setControllerSupplier(ControllerBuilderSupplierFactory.get(this, mImagePipelineFactory));

        int resId = comic.getFavoriteTime() != 0 ? R.drawable.ic_favorite_white_24dp : R.drawable.ic_favorite_border_white_24dp;
        mActionButton.setImageResource(resId);
        mActionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDetailParseFailed() {
        hideProgressBar();
        showSnackbar(R.string.common_parse_error);
    }

    private void increment() {
        if (mAutoBackup && ++mBackupCount == 10) {
            mBackupCount = 0;
            mPreference.putInt(PreferenceManager.PREF_BACKUP_SAVE_COMIC_COUNT, 0);
            mPresenter.backup();
        }
    }

    @Override
    protected String getDefaultTitle() {
        return getString(R.string.detail);
    }

    public static Intent createIntent(Context context, Long id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Extra.EXTRA_ID, id);
        return intent;
    }

}
