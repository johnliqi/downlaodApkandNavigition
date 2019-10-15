package com.example.viewpagermuch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.viewpagermuch.fragment.SplashFragment;
import com.example.viewpagermuch.transformer.RotateTransformer;
import com.example.viewpagermuch.transformer.ScaleTransformer;

public class BannerActivity extends AppCompatActivity {
    ViewPager mVpMain;
    private int mResId[] = new int[]{0xffff0000, 0xff00ff00, 0xfff000ff};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        mVpMain = findViewById(R.id.vp_main);
        mVpMain.setOffscreenPageLimit(3);
        mVpMain.setPageMargin(40);
        mVpMain.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mResId.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = new View(container.getContext());
                view.setBackgroundColor(mResId[position]);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        //mVpMain.setPageTransformer(true, new ScaleTransformer());
        mVpMain.setPageTransformer(true, new RotateTransformer());
    }


}
