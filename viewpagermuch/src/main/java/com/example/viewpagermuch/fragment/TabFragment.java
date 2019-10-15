package com.example.viewpagermuch.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viewpagermuch.R;

public class TabFragment extends Fragment {
    TextView mTvTitle;
    String mTitle;
    private static final String BUNDLE_KEY_TITLE = "key_title";
    OnTitleClickListener onTitleClickListener;
// 目的是为了解耦 fragment 向actvity 传值 .
    public static interface OnTitleClickListener {
        public void onClickListener(String Title);
    }

    public void setListener(OnTitleClickListener onTitleClickListener) {
        this.onTitleClickListener = onTitleClickListener;
    }

    public static TabFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_TITLE, title);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_KEY_TITLE, "");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    //view 的初始化操作
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);
        mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTitleClickListener != null) {
                    onTitleClickListener.onClickListener(""); //回到
                }
            }
        });
    }

    public void changeTitle(String title) {
        if (!isAdded()) {
            return;
        }
        mTvTitle.setText(title);
    }
}
