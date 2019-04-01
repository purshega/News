package com.dtek.portal.ui.fragment.business_trip;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.business_trips.BTPreview;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.BasePagerAdapter;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.utils.BTUtils;
import com.dtek.portal.utils.BusinessTrip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.BusinessTrips.API_BT;
import static com.dtek.portal.Const.BusinessTrips.BT_GET_ALL;
import static com.dtek.portal.Const.HTTP.API_AUTHORITY;

public class TabBusinessTripFragment extends Fragment {

    public static final int TAB_ALL = 0;
    public static final int TAB_ACTIVE = 1;
    public static final int TAB_ARCHIVE = 2;

    private Dialog waitDialog;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private int currentTab;

    @SuppressLint("ValidFragment")
    public TabBusinessTripFragment(int currentTab) {
        this.currentTab = currentTab;
    }

    public TabBusinessTripFragment() {
        // Required empty public constructor
    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true); //явно указываем FragmentManager что есть OptionsMenu
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout_fb, container, false);
        ButterKnife.bind(this, view);
        // Set up the ViewPager with the sections adapter.
        initDialog();
        getBTList();
        tabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void getBTList(){
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, Const.BusinessTrips.TEST_TOKEN);

        RestManager.getApi()
                .getAllBT(map, API_BT + BT_GET_ALL)
                .enqueue(new Callback<List<BTPreview>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BTPreview>> call, @NonNull Response<List<BTPreview>> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 200:
                                if (response.isSuccessful() && response.body() != null) {
                                    List <BTPreview> btList;
                                    btList = response.body();
                                    setupViewPager(btList);
                                }
                                break;
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getContext())).logout();
                                break;
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BTPreview>> call, Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                    }
                });

    }

    @Optional
    @OnClick(R.id.fb_btn)
    void onClick() {
        if (getActivity() != null) {
            BusinessTrip.getInstance().createNewBusinessTrip();
            ((MainActivity) getActivity()).switchToFragment(new AddDetailBusinessTripsFragment(), false);
        }
    }

    private void setupViewPager(List<BTPreview> btList) {

        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getChildFragmentManager());

        pagerAdapter.addFragment(new BTFragment(btList , TAB_ALL ), getString(R.string.tab_title_all_ukr));
        pagerAdapter.addFragment(new BTFragment(BTUtils.getActiveList(btList), TAB_ACTIVE), getString(R.string.tab_title_active_ukr));
        pagerAdapter.addFragment(new BTFragment(BTUtils.getArchiveList(btList), TAB_ARCHIVE), getString(R.string.tab_title_archive_ukr));

//        mViewPager.setOffscreenPageLimit(2);// загружаем по 2 экрана с каждой стороны, default=1, меньше нельзя
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(currentTab); // запуск списка по умолчанию
//        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showBurgerButton();
            ((MainActivity) getActivity()).mToolbarTitle.setText(getString(R.string.title_business_trip));
        }
    }
    private void initDialog(){
        waitDialog = WaitDialog.setDialog(getContext());
    }
}
