package com.example.viewpagermuch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.example.viewpagermuch.fragment.SplashFragment;
import com.example.viewpagermuch.fragment.TabFragment;
import com.example.viewpagermuch.transformer.ScaleTransformer;
import com.example.viewpagermuch.view.Tabview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    ViewPager mVpMain;
    int mResId[] = {R.drawable.i1, R.drawable.i2,R.drawable.i3,R.drawable.i3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_main);
        mVpMain = findViewById(R.id.vp_main);
        mVpMain.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return SplashFragment.newInstance(mResId[i]);
            }

            @Override
            public int getCount() {
                return mResId.length;
            }
        });
        mVpMain.setPageTransformer(true,new ScaleTransformer());
    }


}
