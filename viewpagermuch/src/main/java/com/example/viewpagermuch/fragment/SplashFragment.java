package com.example.viewpagermuch.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.viewpagermuch.R;

public class SplashFragment extends Fragment {

    private ImageView imageView;
    private int mResId;
    private static final String BUNDLE_KEY_RESOURCE_ID = "bundle key res id";

    public static SplashFragment newInstance(int mResId) {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_RESOURCE_ID, mResId);
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mResId = arguments.getInt(BUNDLE_KEY_RESOURCE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.iv_content);
        imageView.setImageResource(mResId);
    }
}
