package com.dtek.portal.ui.fragment.hr_vacation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.hr_vacation.ResponseChief;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.BasePagerAdapter;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;
import com.google.gson.Gson;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.HR_IS_MANAGER;

public class TabVacationFragment extends BaseVacationFragment {

    public static final int TAB_LIMIT = 0;
    public static final int TAB_HISTORY = 1;
    public static final int TAB_SUBORDINATE = 2;

    @BindView(R.id.pager_hr_leave) ViewPager mViewPager;
    @BindView(R.id.tab_layout_hr_leave) TabLayout tabLayout;
    private int currentTab;
    private Boolean isChief = null;

    @SuppressLint("ValidFragment")
    public TabVacationFragment(int currentTab) {
        this.currentTab = currentTab;
    }

    public TabVacationFragment() {
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

        initViewPager();
        return view;
    }

    private void initViewPager() {
        if (isChief == null) {
            getData();
        }else{
            setupViewPager();
        }
    }

    private void getData() {
        if (Utils.isNetworkAvailable(mContext)) {
            loadData();
        } else {
            Toast.makeText(mContext, getString(R.string.error_msg_no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        waitDialog.show();

        RestManager.getApi()
                .loadHrStringJson(fillMapHeader(), HR_IS_MANAGER + PreferenceUtils.getLogin())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            ResponseChief chief = new Gson().fromJson(response.body(), ResponseChief.class);

                            if (chief != null) {
                                if (chief.isSuccess()) {
                                    isChief = chief.isVip();
                                    setupViewPager();
                                } else {
                                    new MyDialog(chief.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                                }
                            }
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                        waitDialog.dismiss();
                    }
                });

    }

    private void setupViewPager() {
        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new VacationLimitFragment(), getString(R.string.tab_title_limit));
        pagerAdapter.addFragment(new VacationHistoryFragment(), getString(R.string.tab_title_history));
        if (isChief) {
            pagerAdapter.addFragment(new VacationSubordinateFragment(), getString(R.string.tab_title_subordinate));
        }

//        mViewPager.setOffscreenPageLimit(2);// загружаем по 2 экрана с каждой стороны, default=1, меньше нельзя
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(currentTab); // запуск списка по умолчанию
//        if(Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Const.PUSH.JSON_BODY)!=null)
//            mViewPager.setCurrentItem(TAB_HISTORY); // если запустили с пуш нотификации

        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (getActivity() != null) {
            ((MainActivity) Objects.requireNonNull(getActivity())).showBurgerButton();
            ((MainActivity) getActivity()).mToolbarTitle.setText(getString(R.string.title_hr_vacation));
//        }
    }
}
