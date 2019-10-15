package com.example.viewpagermuch.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class RotateTransformer implements ViewPager.PageTransformer {
    private static final float MAX_R0TATE = 15;

    @Override
    public void transformPage(@NonNull View page, float position) {
        Log.d("hyman", page.getTag() + "position" + position);
        if (position < -1) {
            // [-1,1]
            page.setRotation(-MAX_R0TATE);
            page.setPivotX(page.getWidth());
            page.setPivotY(page.getHeight());
        } else if (position <= 1) {
            if (position < 0) {
                // a-> position :(0,-1)
                //[1,0.75f];
                page.setPivotY(page.getHeight());
                page.setPivotX(0.5f * page.getWidth() + 0.5f * (-position) * page.getWidth());
                page.setRotation(MAX_R0TATE*position);
                // b-->a  position:(-1,0)
                //[0.75f,1]
            } else {
                //a-->b
                // b,postion :(1,0)
                //[0.75f,1]
                page.setPivotY(page.getHeight());
                page.setPivotX(page.getWidth() * 0.5f * (1 - position));
                page.setRotation(MAX_R0TATE * position);
                //b-->a
                // b,postion :(0,1)
                //[1,0.75f]
            }
            // [1.]
        } else {
            page.setRotation(MAX_R0TATE);
            page.setPivotX(0);
            page.setPivotY(page.getHeight());
        }
    }
}
