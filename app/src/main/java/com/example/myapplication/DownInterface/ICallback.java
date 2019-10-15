package com.example.myapplication.DownInterface;

import java.io.File;

public interface ICallback {
    public void Success(String url);

    public void Error(Throwable throwable);
}
