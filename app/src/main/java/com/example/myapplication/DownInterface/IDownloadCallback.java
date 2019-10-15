package com.example.myapplication.DownInterface;

import java.io.File;

public interface IDownloadCallback {
    void success(File file);

    void progress(int progress);

    void failed(Throwable throwable);
}
