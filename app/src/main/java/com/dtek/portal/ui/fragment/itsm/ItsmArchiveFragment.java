package com.dtek.portal.ui.fragment.itsm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.dtek.portal.models.itsm.ItService;
import com.dtek.portal.models.itsm.ItServiceList;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.it.BaseItsmAdapter;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;
import com.google.gson.Gson;

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
import static com.dtek.portal.Const.HTTP.ITSM_FILE_ARCHIVE;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_RESPONSE;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_URL;
import static com.dtek.portal.Const.HTTP.ITSM_URL_ARCHIVE;

public class ItsmArchiveFragment extends Fragment implements BaseItsmAdapter.ItServiceListener {

    private static final String TAG = "ItsmArchiveFragment";

    @BindView(R.id.swipeRefresh) SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.main_recycler) RecyclerView mRecyclerView;
    @BindView(R.id.main_progress) ProgressBar mProgressBar;
    @BindView(R.id.tv_empty_list) TextView mTvEmpty;
    private List<ItService> mItOrderList; // список заявок

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItOrderList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.layout_list, container, false);
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


        RestManager.getApi()
                .loadItStringJson(getHeaderMap())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
//                            case 400:
//                            case 401:
//                                Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
//                                startActivityForResult(intentLogin, REQUEST_CODE_LOGIN);
//                                break;
                            case 502:
                                new MyDialog(getString(R.string.text_error_itsm)).show(getFragmentManager(), "fragmentDialog");
                                break;
                        }
                        if (response.isSuccessful() && response.body() != null) {
                            String strJson = response.body();
                            Gson gson = new Gson();
                            ItServiceList mItServiceList = gson.fromJson(strJson, ItServiceList.class);
                            if (mItServiceList.getRoot() != null) {
                                mItOrderList = mItServiceList.getRoot().getOrdersList();
                                if (mItOrderList.size() <= 2) {
                                    mTvEmpty.setVisibility(View.VISIBLE);
                                } else {
                                    setItemsToList();
                                }
                            }
                        }
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    @NonNull
    protected Map<String, String> getHeaderMap() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        map.put(ITSM_KEY_URL, ITSM_URL_ARCHIVE + PreferenceUtils.getLogin());
        map.put(ITSM_KEY_RESPONSE, ITSM_FILE_ARCHIVE);
        return map;
    }

    private void setItemsToList() {
        // используем linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        // создаем адаптер
        BaseItsmAdapter allAdapter = new BaseItsmAdapter(mItOrderList);
        allAdapter.setItServiceListener(this); // слушатель интерфейса

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemViewCacheSize(100);
//        mRecyclerView.setDrawingCacheEnabled(true);
//        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        mRecyclerView.setAdapter(allAdapter);
    }

    @Override
    public void onItemClick(ItService currentOrder) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).switchToFragment(DetailItsmFragment.newInstance(currentOrder), false);
        }
    }
}
