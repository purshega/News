package com.dtek.portal.ui.fragment.gallery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.dtek.portal.models.gallery.Photo;
import com.dtek.portal.ui.fragment.gallery.PhotoFragment;

import java.util.List;

public class PhotoPagerAdapter extends FragmentPagerAdapter {

    private List<Photo> photoList;

    public PhotoPagerAdapter(FragmentManager fm, List<Photo> photoList) {
        super(fm);
        this.photoList = photoList;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoFragment.newInstance(photoList.get(position));
    }

    @Override
    public int getCount() {
        return photoList.size();
    }
}
