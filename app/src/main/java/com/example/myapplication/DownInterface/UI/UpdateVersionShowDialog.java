package com.example.myapplication.DownInterface.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AppUpdater;
import com.example.myapplication.AppUtils;
import com.example.myapplication.Bean.DownloadBean;
import com.example.myapplication.DownInterface.IDownloadCallback;
import com.example.myapplication.R;

import java.io.File;
import java.io.IOException;

public class UpdateVersionShowDialog extends DialogFragment {
    private static final String KEY_DOWNLOADBEAN = "Downloadbean";
    private DownloadBean mDownLoadBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mDownLoadBean = (DownloadBean) arguments.getSerializable(KEY_DOWNLOADBEAN);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_layout, container, false);
        bindEvent(view);
        return view;
    }

    private void bindEvent(View view) {
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_content = view.findViewById(R.id.tv_content);
        final TextView tv_updater = view.findViewById(R.id.tv_update);
        tv_title.setText(mDownLoadBean.title);
        tv_content.setText(mDownLoadBean.content);
        tv_updater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setEnabled(false);
                final File targetFile = new File(getActivity().getCacheDir(), "targetFile.apk");
                AppUpdater.getInstance().getiNetMangaer().download(mDownLoadBean.Url, targetFile, new IDownloadCallback() {
                    @Override
                    public void success(File file) {
                        Log.d("hyman", file.getAbsolutePath());
                        v.setEnabled(true);
                        String fileMd5 = null;
                        try {
                            fileMd5 = AppUtils.getFileMD5(targetFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (fileMd5 != null && fileMd5.equals(mDownLoadBean.md5)) {
                            AppUtils.installApk(getActivity(), file);
                        } else {
                            Toast.makeText(getActivity(), "Md5 检测缺少", Toast.LENGTH_LONG).show();
                        }

                    }


                    @Override
                    public void progress(int progress) {
                        Log.d("hyman", "progress+=" + progress);
                        tv_updater.setText(progress + "%");
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        v.setEnabled(true);
                        Log.d("hyman", "throwable+=" + throwable);
                    }
                },UpdateVersionShowDialog.this);
            }
        });
    }

    public static void show(FragmentActivity Activity, DownloadBean downloadBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DOWNLOADBEAN, downloadBean);
        UpdateVersionShowDialog dialog = new UpdateVersionShowDialog();
        dialog.setArguments(bundle);
        dialog.show(Activity.getSupportFragmentManager(), "updateVersionShowDialog");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d("hyman","ondismiss");
        AppUpdater.getInstance().getiNetMangaer().cancel(this);
    }
}
