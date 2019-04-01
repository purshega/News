package com.dtek.portal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.dtek.portal.R;
import com.dtek.portal.models.gallery.Photo;
import com.dtek.portal.ui.fragment.gallery.adapter.PhotoPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity {

    public static final String EXTRA_PHOTO = "EXTRA_PHOTO";
    public static final String EXTRA_LIST_PHOTO = "EXTRA_LIST_PHOTO";

//    private static final String KEY_CURRENT_POSITION = "com.google.samples.gridtopager.key.currentPosition";
//    public static int currentPosition;

//    public static List<Photo> photoList;
    @BindView(R.id.container)
    ViewPager viewPager;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    public static Intent createIntent(Context context, int position, List<Photo> photoList) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra(EXTRA_PHOTO, position);
        intent.putParcelableArrayListExtra(EXTRA_LIST_PHOTO, (ArrayList<? extends Parcelable>) photoList);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

//        if (savedInstanceState != null) {
//            currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION, 0);
//        } else {
//            currentPosition = getIntent().getIntExtra(EXTRA_PHOTO, 0);
//        }
        int currentPosition = getIntent().getIntExtra(EXTRA_PHOTO, 0);
        List<Photo> photoList = getIntent().getParcelableArrayListExtra(EXTRA_LIST_PHOTO);


        PhotoPagerAdapter photoPagerAdapter = new PhotoPagerAdapter(getSupportFragmentManager(), photoList);
        viewPager.setAdapter(photoPagerAdapter);
        viewPager.setCurrentItem(currentPosition);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        }catch (Exception e){
            e.printStackTrace();
            return true;
        }
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt(KEY_CURRENT_POSITION, currentPosition);
//    }

}
