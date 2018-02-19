package com.hiroshi.cimoc.plugin;

import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiroshi on 2018/2/14.
 */

public class CategoryType {

    private String title;
    private String key;
    private List<Pair<String, String>> item;

    public CategoryType(String title, String key, List<Pair<String, String>> item) {
        this.title = title;
        this.key = key;
        this.item = item;
    }

    public String getTitle() {
        return title;
    }

    public String getKey() {
        return key;
    }

    public List<Pair<String, String>> getItem() {
        return item;
    }

    public static CategoryType create(JSONObject object) {
        try {
            String title = object.getString("title");
            String key = object.getString("key");
            JSONArray array = object.getJSONArray("item");
            List<Pair<String, String>> list = new ArrayList<>(array.length());
            for (int i = 0; i < array.length(); ++i) {
                JSONObject item = array.getJSONObject(i);
                list.add(Pair.create(item.getString("name"), item.getString("value")));
            }
            return new CategoryType(title, key, list);
        } catch (JSONException e) {
            return null;
        }
    }

}
