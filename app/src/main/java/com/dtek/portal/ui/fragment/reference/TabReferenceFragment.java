package com.dtek.portal.ui.fragment.reference;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtek.portal.R;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.BasePagerAdapter;
import com.dtek.portal.utils.BusinessTrip;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;


public class TabReferenceFragment extends Fragment {

    public static final int TAB_ALL = 0;
    public static final int TAB_ACTIVE = 1;
    public static final int TAB_ARCHIVE = 2;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private int currentTab;

    @SuppressLint("ValidFragment")
    public TabReferenceFragment(int currentTab) {
        this.currentTab = currentTab;
    }

    public TabReferenceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout_fb, container, false);
        ButterKnife.bind(this, view);
        // Set up the ViewPager with the sections adapter.
        tabLayout.setupWithViewPager(mViewPager);
        setupViewPager();
        return view;
    }

    @Optional
    @OnClick(R.id.fb_btn)
    void onClick() {
        if (getActivity() != null) {
            BusinessTrip.getInstance().createNewBusinessTrip();
            ((MainActivity) getActivity()).switchToFragment(new AddReferenceFragment(), false);
        }
    }

    private void setupViewPager() {

        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getChildFragmentManager());

        pagerAdapter.addFragment(new ReferenceAllListFragment(), getString(R.string.tab_title_all));
        pagerAdapter.addFragment(new ReferenceActiveListFragment(), getString(R.string.tab_title_active));
        pagerAdapter.addFragment(new ReferenceArchiveListFragment(), getString(R.string.tab_title_archive));

//        mViewPager.setOffscreenPageLimit(2);// загружаем по 2 экрана с каждой стороны, default=1, меньше нельзя
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(currentTab); // запуск списка по умолчанию
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showBurgerButton();
            ((MainActivity) getActivity()).mToolbarTitle.setText(getString(R.string.title_references));
        }
    }
}

