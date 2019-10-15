package com.example.myapplication;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.myapplication.DownInterface.ICallback;
import com.example.myapplication.DownInterface.IDownloadCallback;
import com.example.myapplication.DownInterface.INetMangaer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpMangager implements INetMangaer {
    private static OkHttpClient sOkHttpClient ;
    private static Handler shandler = new Handler(Looper.getMainLooper());

    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        sOkHttpClient = builder.build();
       // builder.sslSocketFactory( )//https 自签名握手操作
    }

    @Override
    public void get(String url, final ICallback mcallback, Object tag) {
        Request.Builder builder = new Request.Builder();
        final Request request = builder.url(url).get().tag(tag).build();
        Call call = sOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                shandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mcallback.Error(e);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String string = response.body().string();
                    shandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mcallback.Success(string);
                        }
                    });
                } catch (Throwable exception) {
                    mcallback.Error(exception);
                }


            }
        });
    }

    @Override
    public void download(String url, final File target, final IDownloadCallback callback, Object tag) {
        if (!target.exists()) {
            target.getParentFile().mkdirs();
        }
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).get().tag(tag).build();
        Call call = sOkHttpClient.newCall(request);


        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                shandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                InputStream is = null;
                OutputStream os = null;
                final long totalLen = response.body().contentLength();
                is = response.body().byteStream();
                os = new FileOutputStream(target);
                byte[] buffer = new byte[8 * 1024];
                int curlen = 0;
                int bufferLen = 0;
                while (!call.isCanceled() && (bufferLen = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bufferLen);
                    os.flush();
                    curlen += bufferLen;
                    final long finalCurLen = curlen;
                    shandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.progress((int) (finalCurLen * 1f / totalLen * 100));
                        }
                    });
                }
                if (call.isCanceled()) {
                    return;
                }
                try {
                    target.setExecutable(true, false);
                    target.setReadable(true, false);
                    target.setWritable(true, false);
                    shandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.success(target);
                        }
                    });


                } catch (final Throwable throwable) {
                    if (call.isCanceled()) {
                        return;
                    }
                    throwable.printStackTrace();
                    shandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.failed(throwable);
                        }
                    });
                    ;
                } finally {
                    if (is != null) {
                        is.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                }



            }
        });
    }

    @Override
    public void cancel(Object tag) {
        List<Call> queueCalls = sOkHttpClient.dispatcher().queuedCalls();
        Log.d("hyman", "find queueCalls call=" + queueCalls.size());
        Log.d("hyman", "find runnigCalls call=" + queueCalls.size());
        if (queueCalls != null) {
            for (Call call : queueCalls) {
                if (tag.equals(call.request().tag())) {
                    Log.d("hyman", "find call=" + tag);
                    call.cancel();
                }
            }
        }
        List<Call> runnigCalls = sOkHttpClient.dispatcher().runningCalls();
        for (Call call : runnigCalls) {
            if (tag.equals(call.request().tag())) {
                Log.d("hyman", "find call=" + tag);
                call.cancel();
            }
        }
}
}
