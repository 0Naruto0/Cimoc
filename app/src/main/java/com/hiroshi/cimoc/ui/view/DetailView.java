package com.hiroshi.cimoc.ui.view;

import com.hiroshi.cimoc.model.Comic;
import com.hiroshi.cimoc.model.Task;

import java.util.ArrayList;

/**
 * Created by Hiroshi on 2016/8/21.
 */
public interface DetailView extends BaseView {

    void onDetailParseSuccess(Comic comic);

    void onDetailParseFailed();

    void onLastChapterUpdate(String chapterId);

    void onTaskAddSuccess(ArrayList<Task> list);

    void onTaskAddFailed();

}
