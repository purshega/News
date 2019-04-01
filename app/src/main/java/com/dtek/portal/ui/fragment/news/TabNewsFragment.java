package com.dtek.portal.ui.fragment.news;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtek.portal.R;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.BasePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabNewsFragment extends Fragment {
    private static final String TAG = "TabNewsFragment";

    public static final int TAB_NEWS_DTEK = 0;
    public static final int TAB_NEWS_NO_MISS = 1;
    public static final int TAB_NEWS_COMPANY = 2;

    @BindView(R.id.pager_news) ViewPager mViewPager;
    @BindView(R.id.tab_news_layout) TabLayout tabLayout;
    private int currentTab;

    @SuppressLint("ValidFragment")
    public TabNewsFragment(int currentTab) {
        this.currentTab = currentTab;
    }

    public TabNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //явно указываем FragmentManager что есть OptionsMenu
//        setRetainInstance(true); // сохранение состояния фрагмента ###
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_tab, container, false);
        ButterKnife.bind(this, view);
        // Set up the ViewPager with the sections adapter.
        setupViewPager();
        tabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void setupViewPager() {
        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new NewsDtekListFragment(), getString(R.string.title_news_dtek));
        pagerAdapter.addFragment(new NewsNoMissListFragment(), getString(R.string.title_not_miss));
        pagerAdapter.addFragment(new NewsCompanyListFragment(), getString(R.string.title_news_company));

        mViewPager.setOffscreenPageLimit(2);// загружаем по 2 экрана с каждой стороны, default=1, меньше нельзя
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(currentTab); // запуск списка по умолчанию
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showBurgerButton();
            ((MainActivity) getActivity()).mToolbarTitle.setText(getString(R.string.title_news));
        }
    }
}
