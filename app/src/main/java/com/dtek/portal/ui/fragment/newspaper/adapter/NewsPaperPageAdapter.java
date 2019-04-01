package com.dtek.portal.ui.fragment.newspaper.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dtek.portal.ui.fragment.newspaper.NewsPaperPageFragment;

import static com.dtek.portal.ui.activity.NewsPaperActivity.pdfRenderer;


public class NewsPaperPageAdapter extends FragmentStatePagerAdapter {

    public NewsPaperPageAdapter(FragmentManager fm ) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NewsPaperPageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return pdfRenderer.getPageCount();
    }



}

