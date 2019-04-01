package com.dtek.portal.ui.fragment.hr_vacation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.hr_vacation.VacationList;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.hr.HrVacationLimitsAdapter;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.HR_VACATION_LIMITS;


public class VacationLimitFragment extends BaseVacationFragment {
    private static final String TAG = "VacationLimitFragment";

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.main_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_empty_list)
    TextView mTvEmpty;
    @BindView(R.id.fab_hr_leave_create)
    FloatingActionButton mFabCreate;

    private List<VacationList.Vacation> mLimitsListDays; // список с днями
    private List<VacationList.Vacation> mVacationList;
    private Call call;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLimitsListDays = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.layout_list_fab, container, false);
        ButterKnife.bind(this, v);

        getData(v);
        initSwipe(v);
        return v;
    }

    @Optional
    @OnClick(R.id.fab_hr_leave_create)
    void onClick() {
        ((MainActivity) Objects.requireNonNull(getActivity())).switchToFragment(CreateVacationFragment.newInstance(null), false);
    }

    private void initSwipe(View v) {
        mSwipeRefresh.setOnRefreshListener(() -> {
            getData(v);
            mSwipeRefresh.setRefreshing(false);
        });
    }

    private void getData(View v) {
        if (Utils.isNetworkAvailable(mContext)) {
            loadData();
        } else {
            Snackbar.make(v, R.string.error_msg_no_internet, Snackbar.LENGTH_LONG).show();
        }
    }

    private void loadData() {
        mProgressBar.setVisibility(View.VISIBLE);
        mTvEmpty.setVisibility(View.GONE);
        mFabCreate.setVisibility(View.GONE);

        call = RestManager.getApi()
//                .loadHrStringJson(fillMapHeader(), HR_VACATION_LIMITS + App.prefs.getLogin())
                .loadHrStringJson(fillMapHeader(), HR_VACATION_LIMITS + loginUser);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                restError(response);
                if (response.isSuccessful() && response.body() != null) {
                    VacationList mVacations = new Gson().fromJson(response.body(), VacationList.class);

                    if (mVacations != null) {
                        if (mVacations.isSuccess()) {
                            mVacationList = mVacations.getLimitsList();
                            if (mVacationList.isEmpty()) {
                                mTvEmpty.setVisibility(View.VISIBLE);
                                mTvEmpty.setText(getString(R.string.txt_empty_list));
                            } else {
                                PreferenceUtils.saveLeave(mVacationList);
                                filterVacation();
                                mFabCreate.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (VacationLimitFragment.this.isResumed()) {
                                new MyDialog(mVacations.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                            } else {
                                mTvEmpty.setVisibility(View.VISIBLE);
                                mTvEmpty.setText(mVacations.getMessage());
                            }
                        }
                    }
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                if (VacationLimitFragment.this.isResumed()) { // фрагмент отображается пользователю?
                    new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                }
                mProgressBar.setVisibility(View.GONE);
            }
        });

    }

    private void filterVacation() {
        mLimitsListDays.clear();

        for (VacationList.Vacation vacation : mVacationList) {
            if (vacation.getDays() != null) {
                mLimitsListDays.add(vacation);
            }
        }

        if (mLimitsListDays.isEmpty()) {
            mTvEmpty.setVisibility(View.VISIBLE);
        } else {
            setItemsToList();
        }
    }

    private void setItemsToList() {
        // используем linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        // создаем адаптер
        HrVacationLimitsAdapter adapter = new HrVacationLimitsAdapter(mLimitsListDays);
//        adapter.setHrLeaveListener(this); // слушатель интерфейса

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemViewCacheSize(10);
//        mRecyclerView.setDrawingCacheEnabled(true);
//        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(call!=null)
            call.cancel();
    }

}
