package com.example.coverflowruff;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Bagwan Akib Rafiq on 10/29/2019.
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static float MIN_SCALE = 100f;
    private static final float MIN_ALPHA = 0.7f;


    public static final float SCALE_MIN = 0.3f;
    public static final float SCALE_MAX = 1f;
    public static final float MARGIN_MIN = 0f;
    public static final float MARGIN_MAX = 0f;
    public float scale  = 0f;

    private float pagerMargin = 0f;
    private float spaceValue = 0f;
    private float rotationX    = 70f;
    private float rotationY    = 60f;

    public ZoomOutPageTransformer(boolean isZoomEnable) {
        if (isZoomEnable) {
            MIN_SCALE = 0.85f;
        } else {
            MIN_SCALE = 1f;
        }
    }
    public ZoomOutPageTransformer() {
        MIN_SCALE = 0.85f;
    }
    public void transformPage(View view, float position) {
        if(rotationY!=0){
            float realRotationY = Math.min(rotationY,Math.abs(position * rotationY));
            view.setRotationY(position < 0f ? realRotationY : - realRotationY);
        }

        if (scale != 0f) {
            float realScale = getFloat(1 - Math.abs(position * scale),SCALE_MIN,SCALE_MAX);
            view.setScaleX(realScale);
            view.setScaleY(realScale);
        }

        if (pagerMargin != 0) {

            float realPagerMargin = position * (pagerMargin);

            if (spaceValue != 0) {
                float realSpaceValue = getFloat(Math.abs(position * spaceValue),MARGIN_MIN,MARGIN_MAX);
                realPagerMargin += (position > 0) ? realSpaceValue : - realSpaceValue;
            }

            view.setTranslationX(realPagerMargin);
        }



        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        float vertMargin = pageHeight * (1 - MIN_SCALE) / 2;
        float horzMargin = pageWidth * (1 - MIN_SCALE) / 2;
        view.setScaleX(MIN_SCALE);
        view.setScaleY(MIN_SCALE);
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(MIN_ALPHA);
            view.setTranslationX(horzMargin - vertMargin / 2);


        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            vertMargin = pageHeight * (1 - scaleFactor) / 2;
            horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                            (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(MIN_ALPHA);
            view.setTranslationX(-horzMargin + vertMargin / 2);

        }
    }

    public static float getFloat(float value,float minValue,float maxValue){
        return Math.min(maxValue, Math.max(minValue, value));
    }
}
