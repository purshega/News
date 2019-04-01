package com.dtek.portal.ui.fragment.itsm.it.twoline;

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
import com.dtek.portal.ui.adapter.BasePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Tab2LineFragment extends Fragment {

    public static final int TAB_NEW = 0;
    public static final int TAB_IN_WORK = 1;
    public static final int TAB_POSTPONE = 2;

    @BindView(R.id.pager_hr_leave) ViewPager mViewPager;
    @BindView(R.id.tab_layout_hr_leave) TabLayout tabLayout;
    private int currentTab;

    @SuppressLint("ValidFragment")
    public Tab2LineFragment(int currentTab) {
        this.currentTab = currentTab;
    }

    public Tab2LineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //явно указываем FragmentManager что есть OptionsMenu
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hr_leave_tab, container, false);
        ButterKnife.bind(this, view);
        // Set up the ViewPager with the sections adapter.
        setupViewPager();
        tabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void setupViewPager() {
        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getChildFragmentManager());
        addChildFragment(pagerAdapter);

//        mViewPager.setOffscreenPageLimit(2);// загружаем по 2 экрана с каждой стороны, default=1, меньше нельзя
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(currentTab); // запуск списка по умолчанию
    }

    public void addChildFragment(BasePagerAdapter pagerAdapter) {
        pagerAdapter.addFragment(new ListTwoLineNewsFragment(), getString(R.string.tab_title_new));
        pagerAdapter.addFragment(new ListTwoLineInWorkFragment(), getString(R.string.tab_title_in_work));
        pagerAdapter.addFragment(new ListTwoLinePostponeFragment(), getString(R.string.tab_title_postpone));
    }

}
