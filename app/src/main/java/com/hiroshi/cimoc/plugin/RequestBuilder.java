package com.hiroshi.cimoc.plugin;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by Hiroshi on 2018/2/14.
 */

public class RequestBuilder {

    private String mMethod;
    private String mHost;
    private String mUri;
    private boolean isHttps;

    private Map<String, String> mArgs;
    private Map<String, String> mHeader;

    public RequestBuilder(JSONObject object, String host) {
        mMethod = object.optString("method", "GET");
        mHost = object.optString("host", host);
        mUri = object.optString("mUri", "/");
        isHttps = object.optBoolean("https", false);
        mArgs = new HashMap<>();
        Util.putAll(object, "args", mArgs);
        mHeader = new HashMap<>();
        Util.putAll(object, "header", mHeader);
    }

    private String replace(String value, Map<String, String> variable) {
        Matcher matcher = Pattern.compile("<(.*?)>").matcher(value);
        while (matcher.find()) {
            value = value.replace(matcher.group(), variable.get(matcher.group(1)));
        }
        return value;
    }

    public Request build(Map<String, String> header, Map<String, String> variable) {
        Request.Builder builder = new Request.Builder();

        for (String key : header.keySet()) {
            builder.addHeader(key, header.get(key));
        }
        for (String key : mHeader.keySet()) {
            builder.addHeader(key, mHeader.get(key));
        }

        StringBuilder sb = new StringBuilder(isHttps ? "https" : "http");
        sb.append("://").append(mHost).append(mUri);
        switch (mMethod) {
            default:
            case "GET":
                if (!mArgs.isEmpty()) {
                    sb.append('?');
                    for (String key : mArgs.keySet()) {
                        sb.append(key).append('=').append(replace(mArgs.get(key), variable));
                    }
                }
                builder.get();
                break;
            case "POST":
                FormBody.Builder body = new FormBody.Builder();
                for (String key : mArgs.keySet()) {
                    body.add(key, replace(mArgs.get(key), variable));
                }
                builder.post(body.build());
                break;
        }

        return builder.url(sb.toString()).build();
    }

    public static Request build(String url, Map<String, String> header) {
        Request.Builder builder = new Request.Builder();
        for (String key : header.keySet()) {
            builder.addHeader(key, header.get(key));
        }
        return builder.url(url).build();
    }

}
