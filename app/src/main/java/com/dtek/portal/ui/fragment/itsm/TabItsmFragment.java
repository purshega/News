package com.dtek.portal.ui.fragment.itsm;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class TabItsmFragment extends Fragment {

    public static final int TAB_ALL = 0;
    public static final int TAB_ACTIVE = 1;
    public static final int TAB_ARCHIVE = 2;

    @BindView(R.id.pager_it_service) ViewPager mViewPager;
    @BindView(R.id.tab_it_service_layout) TabLayout tabLayout;
    private int currentTab;

    @SuppressLint("ValidFragment")
    public TabItsmFragment(int currentTab) {
        this.currentTab = currentTab;
    }

    public TabItsmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //явно указываем FragmentManager что есть OptionsMenu
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_it_service_tab, container, false);
        ButterKnife.bind(this, view);
        // Set up the ViewPager with the sections adapter.
        setupViewPager();
        tabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    @Optional
    @OnClick(R.id.fab_it_service_add)
    protected void onClickFab (){
        if (getActivity() != null) {
            ((MainActivity) getActivity()).switchToFragment(new CreateItsmFragment(), false);
        }
    }

    private void setupViewPager() {
        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getChildFragmentManager());
        addChildFragment(pagerAdapter);

//        mViewPager.setOffscreenPageLimit(2);// загружаем по 2 экрана с каждой стороны, default=1, меньше нельзя
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(currentTab); // запуск списка по умолчанию
    }

    public void addChildFragment(BasePagerAdapter pagerAdapter) {
        pagerAdapter.addFragment(new ItsmAllFragment(), getString(R.string.tab_title_all));
        pagerAdapter.addFragment(new ItsmActiveFragment(), getString(R.string.tab_title_active));
        pagerAdapter.addFragment(new ItsmArchiveFragment(), getString(R.string.tab_title_archive));
    }
}
