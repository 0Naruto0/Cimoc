package com.hiroshi.cimoc.misc;

import com.hiroshi.cimoc.manager.PreferenceManager;
import com.hiroshi.cimoc.model.Source;
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
import java.util.List;

/**
 * Created by Hiroshi on 2017/1/18.
 */

public class UpdateHelper {

    // TODO 用 versioncode

    public static void update(PreferenceManager manager) {
    }

    /**
     * 初始化图源
     */
    private static void initSource() {
        List<Source> list = new ArrayList<>(11);
        list.add(IKanman.getDefaultSource());
        list.add(Dmzj.getDefaultSource());
        list.add(HHAAZZ.getDefaultSource());
        list.add(CCTuku.getDefaultSource());
        list.add(U17.getDefaultSource());
        list.add(DM5.getDefaultSource());
        list.add(Webtoon.getDefaultSource());
        list.add(HHSSEE.getDefaultSource());
        list.add(MH57.getDefaultSource());
        list.add(Dmzjv2.getDefaultSource());
        list.add(MangaNel.getDefaultSource());
        // list.add(PuFei.getDefaultSource());
      //  session.getSourceDao().insertOrReplaceInTx(list);
    }

}
