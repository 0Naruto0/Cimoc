package com.hiroshi.cimoc.plugin;

import com.hiroshi.cimoc.model.Chapter;
import com.hiroshi.cimoc.model.Comic;
import com.hiroshi.cimoc.model.ImageUrl;
import com.hiroshi.cimoc.parser.SearchIterator;

import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by Hiroshi on 2018/2/14.
 */

public interface ParserPlugin {

    Request getSearchRequest(String keyword, int page) throws Exception;

    Request getDetailRequest(String comicId) throws Exception;

    Request getChapterRequest(String comicId) throws Exception;

    Request getUpdateRequest(String comicId) throws Exception;

    Request getImageRequest(String comicId, String chapterId) throws Exception;

    Request getImageLazyRequest(String url) throws Exception;

    Request getCategoryRequest(Map<String, String> args, int page) throws Exception;

    SearchIterator parseSearch(String content, int page) throws Exception;

    ComicDetail parseDetail(String content) throws Exception;

    List<Chapter> parseChapter(String content) throws Exception;

    String parseUpdate(String html) throws Exception;

    List<ImageUrl> parseImage(String html) throws Exception;

    String parseLazy(String html, String url) throws Exception;

    List<Comic> parseCategory(String html, int page);

}
