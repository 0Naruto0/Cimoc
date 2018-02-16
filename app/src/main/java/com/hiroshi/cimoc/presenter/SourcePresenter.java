package com.hiroshi.cimoc.presenter;

import com.hiroshi.cimoc.manager.SourceManager;
import com.hiroshi.cimoc.model.Source;
import com.hiroshi.cimoc.ui.view.SourceView;

/**
 * Created by Hiroshi on 2016/8/11.
 */
public class SourcePresenter extends BasePresenter<SourceView> {

    private SourceManager mSourceManager;

    @Override
    protected void onViewAttach() {
        mSourceManager = SourceManager.getInstance();
    }

    public void load() {
        mBaseView.onSourceLoadSuccess(mSourceManager.list());
    }

    public void update(Source source) {
        mSourceManager.update(source);
    }

}
