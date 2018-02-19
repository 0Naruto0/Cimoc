package com.hiroshi.cimoc.plugin;

import android.content.Context;
import android.util.Log;

import com.hiroshi.cimoc.App;
import com.hiroshi.cimoc.saf.DocumentFile;
import com.hiroshi.cimoc.utils.DocumentUtils;

import java.io.File;

import dalvik.system.DexClassLoader;

/**
 * Created by Hiroshi on 2018/2/14.
 */

public class PluginLoader {

    public String[] listPlugin(Context context) {
        DocumentFile root = App.getDocumentFile();
        DocumentFile dir = DocumentUtils.getOrCreateSubDirectory(root, "plugin");
        return DocumentUtils.listFilesWithSuffix(dir, "zip");
    }

    public void load(Context context, String name) {
        File dexTemp = context.getDir("dex_temp", 0);
        File dex = context.getDir("dex", 0);
        DexClassLoader loader = new DexClassLoader(dexTemp.getAbsolutePath(), dex.getAbsolutePath(), null, context.getClassLoader());
        Class clazz;
        ParserPlugin plugin;
        try {
            clazz = loader.loadClass(name);
            plugin = (ParserPlugin) clazz.newInstance();
            if (plugin == null) {
                Log.e("-----------", "------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
