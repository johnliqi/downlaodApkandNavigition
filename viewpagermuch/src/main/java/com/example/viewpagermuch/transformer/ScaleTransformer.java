package com.example.viewpagermuch.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.security.cert.TrustAnchor;

import javax.xml.transform.Transformer;

public class ScaleTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        Log.d("hyman", page.getTag() + "position" + position);
        if (position < -1) {
            // [-1,1]
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(MIN_ALPHA);
        } else if (position <= 1) {
            if (position < 0) {
                // a-> position :(0,-1)
                //[1,0.75f];
                float scaleA = MIN_SCALE + (1 - MIN_SCALE) * (1 + position);
                page.setScaleX(scaleA);
                page.setScaleY(scaleA);
                float alphaA = MIN_ALPHA + (1 - MIN_ALPHA) * (1 + position);
                page.setAlpha(alphaA);
                // b-->a  position:(-1,0)
                //[0.75f,1]
            } else {
                //a-->b
                // b,postion :(1,0)
                //[0.75f,1]
                float scaleB = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
                page.setScaleY(scaleB);
                page.setScaleX(scaleB);
                float alphaB=MIN_ALPHA+(1-MIN_ALPHA)*(1-position);
                page.setAlpha(alphaB);
                //b-->a
                // b,postion :(0,1)
                //[1,0.75f]
            }
            // [1.]
        } else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(MIN_ALPHA);
        }
    }
}

