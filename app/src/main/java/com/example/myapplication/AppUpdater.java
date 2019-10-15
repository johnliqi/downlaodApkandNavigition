package com.example.myapplication;

import com.example.myapplication.DownInterface.INetMangaer;

public class AppUpdater {
    static AppUpdater updater= new AppUpdater();
    private INetMangaer iNetMangaer = new OkhttpMangager();

    public INetMangaer getiNetMangaer() {
        return iNetMangaer;
    }

    //网络请求的能力
    public static AppUpdater getInstance() {
        return updater;
    }

}
