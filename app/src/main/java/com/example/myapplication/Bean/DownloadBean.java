package com.example.myapplication.Bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DownloadBean implements Serializable {
    public String title;
    public String content;
    public String Url;
    public String md5;
    public String versionCode;

    public static DownloadBean parse(String response) {
        try {
            JSONObject repJSON = new JSONObject(response);
            String title = repJSON.optString("title");
            String content = repJSON.optString("content");
            String url = repJSON.optString("url");
            String md5 = repJSON.optString("md5");
            String versionCode = repJSON.optString("versionCode");
            DownloadBean downloadBean = new DownloadBean();
            downloadBean.title = title;
            downloadBean.content = content;
            downloadBean.md5 = md5;
            downloadBean.Url = url;
            downloadBean.versionCode = versionCode;
            return downloadBean;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}
