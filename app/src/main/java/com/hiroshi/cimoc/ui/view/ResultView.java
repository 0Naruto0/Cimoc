package com.hiroshi.cimoc.ui.view;

import com.hiroshi.cimoc.model.SearchResult;

import java.util.List;

/**
 * Created by Hiroshi on 2016/8/21.
 */
public interface ResultView extends BaseView {

    void onSearchError();

    void onSearchSuccess(SearchResult result);

    void onLoadSuccess(List<SearchResult> list);

    void onLoadFail();

}
