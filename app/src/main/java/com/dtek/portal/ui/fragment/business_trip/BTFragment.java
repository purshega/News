package com.dtek.portal.ui.fragment.business_trip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.business_trips.BTPreview;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.businees_trips.BaseBTAdapter;
import com.dtek.portal.utils.BTUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.BusinessTrips.API_BT;
import static com.dtek.portal.Const.BusinessTrips.BT_GET_ALL;
import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.ui.fragment.business_trip.TabBusinessTripFragment.TAB_ACTIVE;
import static com.dtek.portal.ui.fragment.business_trip.TabBusinessTripFragment.TAB_ALL;
import static com.dtek.portal.ui.fragment.business_trip.TabBusinessTripFragment.TAB_ARCHIVE;

@SuppressLint("ValidFragment")
public class BTFragment extends Fragment implements BaseBTAdapter.BTForListListener {

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.main_recycler)
    RecyclerView mRecyclerView;

    private BaseBTAdapter activeAdapter;
    private List<BTPreview> btList; // список заявок
    private int tabNumber;

    public BTFragment() {
    }

    @SuppressLint("ValidFragment")
    public BTFragment(List<BTPreview> btList, int tabNumber) {
        this.btList = btList;
        this.tabNumber = tabNumber;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_list_bt, container, false);
        ButterKnife.bind(this, v);

        setItemsToList();
        initSwipe(v);
        return v;
    }

    private void setItemsToList() {
        // используем linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        // создаем адаптер
        activeAdapter = new BaseBTAdapter();
        activeAdapter.setBTForListListener(this); // слушатель интерфейса
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(activeAdapter);

        setItems();
    }

    private void setItems(){
        activeAdapter.setItems(btList);
    }

    @Override
    public void onItemClick(BTPreview currentBT) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).switchToFragment(new AddDetailBusinessTripsFragment(currentBT), false);
        }
    }

    private void initSwipe(View v) {
        mSwipeRefresh.setOnRefreshListener(() -> {
            getBTList();
        });
    }

    private void getBTList() {

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
                                    List<BTPreview> btList = response.body();
                                    getList(btList);
                                }
                                break;
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getContext())).logout();
                                break;
                        }
                        mSwipeRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BTPreview>> call, Throwable t) {
                        t.printStackTrace();
                        mSwipeRefresh.setRefreshing(false);
                    }
                });
    }

    private void getList(List<BTPreview> list) {
        btList.clear();
        switch (tabNumber) {
            case TAB_ALL:
                btList.addAll(list);
                break;
            case TAB_ACTIVE:
                btList.addAll(BTUtils.getActiveList(list));
                break;
            case TAB_ARCHIVE:
                btList.addAll(BTUtils.getArchiveList(list));
                break;
        }
        setItems();
    }

}