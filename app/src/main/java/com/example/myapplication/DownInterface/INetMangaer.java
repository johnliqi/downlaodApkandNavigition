package com.example.myapplication.DownInterface;

import java.io.File;

public interface INetMangaer {
     void get(String url, ICallback mcllback,Object tag);
     void download(String url, File target, IDownloadCallback  callback,Object tag);
     void cancel(Object tag);
}
