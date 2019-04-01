package com.dtek.portal.ui.fragment.car;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.car.CarOrder;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.car.BaseCarAdapter;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.CAR_CAT_ARCHIVE;

public class CarArchiveFragment extends Fragment implements BaseCarAdapter.OrderCarListener {

    private static final String TAG = "CarArchiveFragment";

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.main_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_empty_list)
    TextView mTvEmpty;

    private List<CarOrder> mCarOrderList; // список заявок

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCarOrderList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_list, container, false);
        ButterKnife.bind(this, v);

        getData(v);
        initSwipe(v);
        return v;
    }

    private void initSwipe(View v) {
        mSwipeRefresh.setOnRefreshListener(() -> {
            getData(v);
            mSwipeRefresh.setRefreshing(false);
        });
    }

    private void getData(View v) {
        if (Utils.isNetworkAvailable(getContext())) {
            loadData();
        } else {
            Snackbar.make(v, R.string.error_msg_no_internet, Snackbar.LENGTH_LONG).show();
        }
    }

    private void loadData() {
        mProgressBar.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        RestManager.getApi()
                .getListOrderCar(map, CAR_CAT_ARCHIVE)
                .enqueue(new Callback<List<CarOrder>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<CarOrder>> call, @NonNull Response<List<CarOrder>> response) {
                        RestManager.printResponseLog(response);
//                        switch (response.code()) {
//                        case 400:
//                            case 401:
//                                Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
//                                startActivityForResult(intentLogin, REQUEST_CODE_LOGIN);
//                                break;
//                        }
                        if (response.isSuccessful() && response.body() != null) {
                            mCarOrderList = response.body();
                            if (mCarOrderList == null || mCarOrderList.isEmpty()) {
                                mTvEmpty.setVisibility(View.VISIBLE);
                            } else {
                                Log.i(TAG, "onResponse: " + mCarOrderList.toString());
                                setItemsToList();
                            }
                        }
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<CarOrder>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void setItemsToList() {
        // используем linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        // создаем адаптер
        BaseCarAdapter archiveAdapter = new BaseCarAdapter(mCarOrderList);
        archiveAdapter.setOrderCarListener(this); // слушатель интерфейса
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemViewCacheSize(50);
//        mRecyclerView.setDrawingCacheEnabled(true);
//        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        mRecyclerView.setAdapter(archiveAdapter);
    }

    @Override
    public void onItemClick(CarOrder currentOrder) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).switchToFragment(AddDetailCarFragment.newInstance(currentOrder), false);
        }
    }
}
