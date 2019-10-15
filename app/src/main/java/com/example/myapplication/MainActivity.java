package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Bean.DownloadBean;
import com.example.myapplication.DownInterface.ICallback;
import com.example.myapplication.DownInterface.IDownloadCallback;
import com.example.myapplication.DownInterface.UI.UpdateVersionShowDialog;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Button BtnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtnUpdate = findViewById(R.id.BtnUpdater);
        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            /**
             *
             "title":"4.5.0更新啦！",
             "content":"1. 优化了阅读体验；\n2. 上线了 hyman 的课程；\n3. 修复了一些已知问题。",
             "url":"http://59.110.162.30/v450_imooc_updater.apk",
             "md5":"14480fc08932105d55b9217c6d2fb90b",
             "versionCode":"450"
             }
             * @param v
             */
            @Override
            public void onClick(View v) {
                AppUpdater.getInstance().getiNetMangaer().get("http://59.110.162.30/app_updater_version.json", new ICallback() {
                    @Override
                    public void Success(String response) {
                        Log.d("hyman", "response===" + response);
                        final DownloadBean downloadBean = DownloadBean.parse(response);


                        UpdateVersionShowDialog.show(MainActivity.this, downloadBean);

                    }

                    @Override
                    public void Error(Throwable throwable) {

                    }
                }, MainActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUpdater.getInstance().getiNetMangaer().cancel(this);
    }
}
