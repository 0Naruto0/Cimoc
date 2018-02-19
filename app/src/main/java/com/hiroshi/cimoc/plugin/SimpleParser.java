package com.hiroshi.cimoc.plugin;

import com.hiroshi.cimoc.model.Comic;
import com.hiroshi.cimoc.parser.SearchIterator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by Hiroshi on 2018/2/14.
 */

public abstract class SimpleParser implements ParserPlugin {

    private String mHost;
    private Map<String, String> mHeader;

    private int mSearchMax;  // 最大页数
    private RequestBuilder mSearchRequest;

    private boolean isChapterRequestSameAsDetail;
    private boolean isUpdateRequestSameAsDetail;
    private RequestBuilder mDetailRequest;
    private RequestBuilder mChapterRequest;
    private RequestBuilder mUpdateRequest;

    private boolean isImageLazy;
    private RequestBuilder mImageRequest;
    private Map<String, String> mImageLazyHeader;

    private int mCategoryMax;
    private boolean isCategoryMulti;
    private RequestBuilder mCategoryRequest;
    private List<CategoryType> mCategoryType;

    private Map<String, String> mExtra;

    public SimpleParser() {
        mHeader = new HashMap<>();
        mExtra = new HashMap<>();
        mCategoryType = new ArrayList<>();
    }

    public boolean isChapterRequestSameAsDetail() {
        return isChapterRequestSameAsDetail;
    }

    public boolean isUpdateRequestSameAsDetail() {
        return isUpdateRequestSameAsDetail;
    }

    public int getSearchMaxPage() {
        return mSearchMax;
    }

    public int getCategoryMaxPage() {
        return mCategoryMax;
    }

    public String getExtra(String key) {
        return mExtra.get(key);
    }

    private boolean initDetail(JSONObject module) {
        if (module == null) {
            return false;
        }

        JSONObject request = module.optJSONObject("request");
        if (request != null) {
            mDetailRequest = new RequestBuilder(request, mHost);
        }

        JSONObject chapter = module.optJSONObject("chapter");
        if (chapter != null) {
            isChapterRequestSameAsDetail = chapter.optBoolean("requestSameAsDetail", true);
            if (!isChapterRequestSameAsDetail) {
                request = chapter.optJSONObject("request");
                if (request != null) {
                    mChapterRequest = new RequestBuilder(request, mHost);
                }
            }
        }

        JSONObject update = module.optJSONObject("update");
        if (chapter != null) {
            isUpdateRequestSameAsDetail = update.optBoolean("requestSameAsDetail", true);
            if (!isUpdateRequestSameAsDetail) {
                request = update.optJSONObject("request");
                if (request != null) {
                    mUpdateRequest = new RequestBuilder(request, mHost);
                }
            }
        }

        return true;
    }

    private boolean initImage(JSONObject module) {
        if (module == null) {
            return false;
        }

        JSONObject request = module.optJSONObject("request");
        if (request != null) {
            mImageRequest = new RequestBuilder(request, mHost);
        }

        JSONObject lazy = module.optJSONObject("lazy");
        if (lazy != null) {
            isImageLazy = lazy.optBoolean("enable", false);
            if (isImageLazy) {
                Util.putAll(lazy, "header", mImageLazyHeader);
            }
        } else {
            isImageLazy = false;
        }

        return true;
    }

    private void initSearch(JSONObject module) {
        if (module == null) {
            return;
        }

        mSearchMax = module.optInt("max", 0);
        JSONObject request = module.optJSONObject("request");
        if (request != null) {
            mSearchRequest = new RequestBuilder(request, mHost);
        }
    }

    private void initCategory(JSONObject module) {
        if (module == null) {
            return;
        }

        JSONArray array = module.optJSONArray("type");
        if (array != null) {
            for (int i = 0; i < array.length(); ++i) {
                JSONObject object = array.optJSONObject(i);
                if (object != null) {
                    CategoryType type = CategoryType.create(object);
                    if (type != null) {
                        mCategoryType.add(type);
                    }
                }
            }
            if (!mCategoryType.isEmpty()) {
                isCategoryMulti = module.optBoolean("multi", true);
                mCategoryMax = module.optInt("max", 0);

                JSONObject request = module.optJSONObject("request");
                if (request != null) {
                    mCategoryRequest = new RequestBuilder(request, mHost);
                }
            }
        }
    }

    public synchronized boolean init(JSONObject root) {
        mHost = root.optString("host");

        if (!initDetail(root.optJSONObject("detail"))) {
            return false;
        }

        if (!initImage(root.optJSONObject("image"))) {
            return false;
        }

        initSearch(root.optJSONObject("search"));
        initCategory(root.optJSONObject("category"));

        Util.putAll(root, "header", mHeader);
        Util.putAll(root, "extra", mExtra);

        return true;
    }

    @Override
    public Request getSearchRequest(String keyword, int page) throws Exception {
        Map<String, String> variable = new HashMap<>();
        variable.put("keyword", keyword);
        variable.put("page", String.valueOf(page));
        return mSearchRequest.build(mHeader, variable);
    }

    @Override
    public Request getDetailRequest(String comicId) throws Exception {
        Map<String, String> variable = new HashMap<>();
        variable.put("comicId", comicId);
        return mDetailRequest.build(mHeader, variable);
    }

    @Override
    public Request getChapterRequest(String comicId) throws Exception {
        Map<String, String> variable = new HashMap<>();
        variable.put("comicId", comicId);
        return mChapterRequest.build(mHeader, variable);
    }

    @Override
    public Request getUpdateRequest(String comicId) throws Exception {
        Map<String, String> variable = new HashMap<>();
        variable.put("comicId", comicId);
        return mUpdateRequest.build(mHeader, variable);
    }

    @Override
    public Request getImageRequest(String comicId, String chapterId) throws Exception {
        Map<String, String> variable = new HashMap<>();
        variable.put("comicId", comicId);
        variable.put("chapterId", chapterId);
        return mImageRequest.build(mHeader, variable);
    }

    @Override
    public Request getCategoryRequest(Map<String, String> args, int page) throws Exception {
        Map<String, String> variable = new HashMap<>(args);
        variable.put("page", String.valueOf(page));
        return mCategoryRequest.build(mHeader, variable);
    }

    @Override
    public Request getImageLazyRequest(String url) throws Exception {
        Map<String, String> header = new HashMap<>(mHeader);
        header.putAll(mImageLazyHeader);
        mImageLazyHeader = header;
        return RequestBuilder.build(url, header);
    }

    @Override
    public SearchIterator parseSearch(String content, int page) throws Exception {
        return null;
    }

    @Override
    public String parseUpdate(String html) throws Exception {
        return null;
    }

    @Override
    public String parseLazy(String html, String url) throws Exception {
        return null;
    }

    @Override
    public List<Comic> parseCategory(String html, int page) {
        return null;
    }

}
