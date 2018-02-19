package com.hiroshi.cimoc.manager;

import com.hiroshi.cimoc.App;
import com.hiroshi.cimoc.dao.SourceDao;
import com.hiroshi.cimoc.model.Source;
import com.hiroshi.cimoc.parser.Parser;
import com.hiroshi.cimoc.source.CCTuku;
import com.hiroshi.cimoc.source.DM5;
import com.hiroshi.cimoc.source.Dmzj;
import com.hiroshi.cimoc.source.Dmzjv2;
import com.hiroshi.cimoc.source.HHAAZZ;
import com.hiroshi.cimoc.source.HHSSEE;
import com.hiroshi.cimoc.source.IKanman;
import com.hiroshi.cimoc.source.MH57;
import com.hiroshi.cimoc.source.MangaNel;
import com.hiroshi.cimoc.source.U17;
import com.hiroshi.cimoc.source.Webtoon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;

/**
 * Created by Hiroshi on 2016/8/11.
 */
public class SourceManager {

    private static SourceManager mInstance;

    private SourceDao mSourceDao;
    private Map<String, Parser> mParserHash = new HashMap<>();
    private Map<String, Source> mSourceHash = new HashMap<>();

    private SourceManager() {
        mSourceDao = App.getDatabase().getSourceDao();
        for (Source source : mSourceDao.list()) {
            mSourceHash.put(source.getId(), source);
        }
    }

    public List<Source> list() {
        List<Source> list = new ArrayList<>();
        for (String key : mSourceHash.keySet()) {
            list.add(mSourceHash.get(key));
        }
        return list;
    }

    public List<Source> listEnable() {
        List<Source> list = new ArrayList<>();
        for (String key : mSourceHash.keySet()) {
            Source source = mSourceHash.get(key);
            if (source.isEnable()) {
                list.add(mSourceHash.get(key));
            }
        }
        return list;
    }

    public Source get(String id) {
        return mSourceHash.get(id);
    }

    public void insert(Source source) {
        mSourceDao.insert(source);
        // TODO 考虑 id 相同的情况
        mSourceHash.put(source.getId(), source);
    }

    public void update(Source source) {
        mSourceDao.update(source);
    }

    public Parser getParser(String id) {
        Parser parser = mParserHash.get(id);
        if (parser == null) {
            switch (id) {
                case IKanman.ID:
                    parser = new IKanman();
                    break;
                case Dmzj.ID:
                    parser = new Dmzj();
                    break;
                case HHAAZZ.ID:
                    parser = new HHAAZZ();
                    break;
                case CCTuku.ID:
                    parser = new CCTuku();
                    break;
                case U17.ID:
                    parser = new U17();
                    break;
                case DM5.ID:
                    parser = new DM5();
                    break;
                case Webtoon.ID:
                    parser = new Webtoon();
                    break;
                case HHSSEE.ID:
                    parser = new HHSSEE();
                    break;
                case MH57.ID:
                    parser = new MH57();
                    break;
                case Dmzjv2.ID:
                    parser = new Dmzjv2();
                    break;
                case MangaNel.ID:
                    parser = new MangaNel();
                    break;
/*
                case PuFei.ID:
                    parser = new PuFei(source);
                    break;
*/
            }
            mParserHash.put(id, parser);
        }
        return parser;
    }

    public Headers getHeader(String id) {
        Parser parser = getParser(id);
        if (parser != null) {
            return parser.getHeader();
        }
        return null;
    }

    public static SourceManager getInstance() {
        if (mInstance == null) {
            synchronized (SourceManager.class) {
                if (mInstance == null) {
                    mInstance = new SourceManager();
                }
            }
        }
        return mInstance;
    }

}
