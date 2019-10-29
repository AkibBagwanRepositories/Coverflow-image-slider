package com.example.coverflowruff;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Bagwan Akib Rafiq on 10/29/2019.
 */
public class ItemFragment extends Fragment {

    private static final String POSITON = "position";
    private static final String SCALE = "scale";
    private static final String DRAWABLE_RESOURE = "resource";
    private int screenWidth;
    private int screenHeight;

    private int[] imageArray = new int[]{R.mipmap.ic_launcher_round,R.mipmap.ic_cover_1,R.mipmap.ic_cover_2,
            R.mipmap.ic_cover_4,R.mipmap.ic_cover_5,R.mipmap.ic_cover_6,R.mipmap.ic_cover_7,
            R.mipmap.ic_cover_4,R.mipmap.ic_cover_5,R.mipmap.ic_cover_6,R.mipmap.ic_cover_7,R.mipmap.ic_launcher_round};

    public static Fragment newInstance(MainActivity context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt(POSITON, pos);
        b.putFloat(SCALE, scale);
        return Fragment.instantiate(context, ItemFragment.class.getName(), b);
    }



    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWidthAndHeight();
    }



    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        final int postion = this.getArguments().getInt(POSITON);
        float scale = this.getArguments().getFloat(SCALE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth / 2, screenHeight / 2);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragmentimage, container, false);

        TextView textView = (TextView) linearLayout.findViewById(R.id.position_text);
        CarouselLinearLayout root = (CarouselLinearLayout) linearLayout.findViewById(R.id.root_container);
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.pagerImg);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(imageArray[postion]);
        textView.setText(postion+"");

        //handling click event
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageDetailsActivity.class);
                intent.putExtra(DRAWABLE_RESOURE, imageArray[postion]);
                startActivity(intent);
            }
        });
        root.setScaleBoth(scale);
        return linearLayout;
    }



    /**
     * Get device screen width and height
     */

    private void getWidthAndHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }

}