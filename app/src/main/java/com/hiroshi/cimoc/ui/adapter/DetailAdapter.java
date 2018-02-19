package com.hiroshi.cimoc.ui.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilderSupplier;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hiroshi.cimoc.R;
import com.hiroshi.cimoc.model.Chapter;
import com.hiroshi.cimoc.model.ComicDetail;
import com.hiroshi.cimoc.ui.widget.ChapterButton;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Hiroshi on 2016/7/2.
 */
public class DetailAdapter extends BaseAdapter<Chapter> {

    private PipelineDraweeControllerBuilderSupplier mControllerSupplier;

    private String title;
    private String cover;
    private String update;
    private String author;
    private String intro;
    private Boolean completed;

    private String chapterId;

    static class ChapterHolder extends BaseViewHolder {
        @BindView(R.id.item_chapter_button) ChapterButton chapterButton;

        ChapterHolder(View view) {
            super(view);
        }
    }

    class HeaderHolder extends BaseViewHolder {
        @BindView(R.id.item_header_comic_image) SimpleDraweeView mComicImage;
        @BindView(R.id.item_header_comic_title) TextView mComicTitle;
        @BindView(R.id.item_header_comic_intro) TextView mComicIntro;
        @BindView(R.id.item_header_comic_status) TextView mComicCompleted;
        @BindView(R.id.item_header_comic_update) TextView mComicUpdate;
        @BindView(R.id.item_header_comic_author) TextView mComicAuthor;

        HeaderHolder(View view) {
            super(view);
        }
    }

    public DetailAdapter(Context context, List<Chapter> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildLayoutPosition(view);
                if (position == 0) {
                    outRect.set(0, 0, 0, 10);
                } else {
                    int offset = parent.getWidth() / 40;
                    outRect.set(offset, 0, offset, (int) (offset * 1.5));
                }
            }
        };
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = mInflater.inflate(R.layout.item_chapter_header, parent, false);
            return new HeaderHolder(view);
        }
        View view = mInflater.inflate(R.layout.item_chapter, parent, false);
        return new ChapterHolder(view);
    }

    public void setDetail(ComicDetail detail, String chapterId) {
        this.cover = detail.getCover();
        this.title = detail.getTitle();
        this.intro = detail.getIntro();
        this.completed = detail.isCompleted();
        this.update = detail.getUpdate();
        this.author = detail.getAuthor();
        this.chapterId = chapterId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setChapterId(String chapterId) {
        if (chapterId == null || chapterId.equals(this.chapterId)) {
            return;
        }
        this.chapterId = chapterId;
        // TODO 不要 notify 全部
        notifyDataSetChanged();
    }

    public Chapter getLastChapter() {
        if (chapterId == null) {
            return mDataSet.get(mDataSet.size() - 1);
        }
        for (Chapter chapter : mDataSet) {
            if (chapterId.equals(chapter.getId())) {
                return chapter;
            }
        }
        return mDataSet.get(mDataSet.size() - 1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (position == 0) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            if (title != null) {
                if (cover != null) {
                    headerHolder.mComicImage.setController(mControllerSupplier.get().setUri(cover).build());
                }
                headerHolder.mComicTitle.setText(title);
                headerHolder.mComicIntro.setText(intro);
                headerHolder.mComicCompleted.setText(completed ? R.string.comic_detail_completed : R.string.comic_detail_ongoing);
                if (update != null) {
                    headerHolder.mComicUpdate.setText(mContext.getString(R.string.comic_detail_update, update));
                }
                headerHolder.mComicAuthor.setText(author);
            }
        } else {
            Chapter chapter = mDataSet.get(position - 1);
            ChapterHolder viewHolder = (ChapterHolder) holder;
            viewHolder.chapterButton.setText(chapter.getTitle());
            viewHolder.chapterButton.setDownload(chapter.isCompleted());
            if (chapterId != null && chapter.getId().equals(chapterId)) {
                viewHolder.chapterButton.setSelected(true);
            } else if (viewHolder.chapterButton.isSelected()) {
                viewHolder.chapterButton.setSelected(false);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? manager.getSpanCount() : 1;
            }
        });
    }

    public void setControllerSupplier(PipelineDraweeControllerBuilderSupplier supplier) {
        this.mControllerSupplier = supplier;
    }

}
