package com.hiroshi.cimoc.plugin;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Hiroshi on 2018/2/14.
 */

public class Util {

    static void putAll(JSONObject parent, String key, Map<String, String> map) {
        JSONObject object = parent.optJSONObject(key);
        if (object != null) {
            Iterator<String> it = object.keys();
            while (it.hasNext()) {
                String temp = it.next();
                map.put(temp, object.optString(temp));
            }
        }
    }

}
