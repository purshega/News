package com.dtek.portal.ui.fragment.hr_vacation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.hr_vacation.HistoryList;
import com.dtek.portal.models.hr_vacation.OrderVacationResponse;
import com.dtek.portal.models.hr_vacation.ResponseChief;
import com.dtek.portal.models.push.PushData;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.hr.HrVacationHistoryAdapter;
import com.dtek.portal.ui.dialog.ConfirmDialog;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.HR_VACATION_GET;
import static com.dtek.portal.Const.HTTP.HR_VACATION_HISTORY;
import static com.dtek.portal.Const.HTTP.HR_VACATION_USER_DENY;

public class VacationHistoryFragment extends BaseVacationFragment
        implements HrVacationHistoryAdapter.HistoryClickListener {
    private static final int REQUEST_DIALOG_CONFIRM = 20; // константа для кода запроса

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.main_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_empty_list)
    TextView mTvEmpty;
    private List<HistoryList.History> mHistoryList; // список заявок
    private Call call;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHistoryList = new ArrayList<>();
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
        if (Utils.isNetworkAvailable(mContext)) {
            loadData();
        } else {
            Snackbar.make(v, R.string.error_msg_no_internet, Snackbar.LENGTH_LONG).show();
        }
    }

    private void loadData() {
        mProgressBar.setVisibility(View.VISIBLE);
        mTvEmpty.setVisibility(View.GONE);

        call = RestManager.getApi().loadHrStringJson(fillMapHeader(), HR_VACATION_HISTORY + loginUser);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                RestManager.printResponseLog(response);
                if (response.isSuccessful() && response.body() != null) {
                    HistoryList mHistory = new Gson().fromJson(response.body(), HistoryList.class);
                    if (mHistory != null) {
                        if (mHistory.isSuccess()) {
                            mHistoryList = mHistory.getHistoryList();
                            setItemsToList();
                            if (mHistoryList.isEmpty()) {
                                if (getActivity() != null) {
                                    mTvEmpty.setVisibility(View.VISIBLE);
                                    mTvEmpty.setText(getString(R.string.txt_empty_list));
                                }
                            }
                        } else {
                            if (VacationHistoryFragment.this.isResumed()) { // фрагмент отображается пользователю?
                                new MyDialog(mHistory.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                            } else {
                                mTvEmpty.setVisibility(View.VISIBLE);
                                mTvEmpty.setText(mHistory.getMessage());
                            }
                        }
                    }
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                if (VacationHistoryFragment.this.isResumed()) {
                    new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                }
            }
        });
    }

    private void setItemsToList() {
        // используем linear layout manager
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        // создаем адаптер
//        HrVacationHistoryAdapter adapter = new HrVacationHistoryAdapter(mContext, mHistoryList);
        HrVacationHistoryAdapter adapter = new HrVacationHistoryAdapter();
        adapter.setItems(mHistoryList);
        adapter.setHistoryClickListener(this); // слушатель интерфейса

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRecyclerView.setItemViewCacheSize(50);
//        mRecyclerView.setDrawingCacheEnabled(true);
//        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        mRecyclerView.setAdapter(adapter);

        checkHistoryList();
    }

    private void checkHistoryList() {
        Intent intent = getActivity().getIntent();
        String jsonBody = intent.getStringExtra(Const.PUSH.JSON_BODY);
        Gson gson = new Gson();
        PushData pushData = gson.fromJson(jsonBody, PushData.class);
        if (pushData != null) {
            if (pushData.getAction().equals(Const.PUSH.TYPE_OPEN)) {
                findRequest(pushData);
                intent.removeExtra(Const.PUSH.JSON_BODY);
            }
        }
    }

    private void findRequest(PushData pushData) {
        int request_number = pushData.getRequest_id();
        for (HistoryList.History history : mHistoryList) {
            if (history.getId() == request_number) {
                loadOrderDetail(request_number);
            }
        }
    }

    int idOrder;

    @Override
    public void onItemClick(HistoryList.History currentVacation) {
        idOrder = currentVacation.getId();
        if (currentVacation.isCanRejected()) {
            ConfirmDialog confirmDialog = new ConfirmDialog(getString(R.string.text_confirm));
            confirmDialog.setTargetFragment(VacationHistoryFragment.this, REQUEST_DIALOG_CONFIRM); // назначаем Fragment целевым фрагментом экземпляра DatePickerDialog
            confirmDialog.show(Objects.requireNonNull(getFragmentManager()), null);
        }
        if (currentVacation.isCanEdit()) {
            if (Utils.isNetworkAvailable(mContext)) {
                loadOrderDetail(idOrder);
            } else {
                Toast.makeText(mContext, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Реакция на получение данных от диалогового окна
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_DIALOG_CONFIRM:
//                    Toast.makeText(mContext, "отменить " + idOrder, Toast.LENGTH_SHORT).show();
                    userCancel(idOrder);
                    break;
            }
        }
    }

    private void userCancel(int id) {
        waitDialog.show();

        RestManager.getApi()
//                .loadHrStringJson(fillMapHeader(), HR_VACATION_USER_DENY + App.prefs.getLogin() +"/" + id )
                .loadHrStringJson(fillMapHeader(), HR_VACATION_USER_DENY + loginUser + "/" + id)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        waitDialog.dismiss();
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            ResponseChief mResponse = new Gson().fromJson(response.body(), ResponseChief.class);

                            if (mResponse != null) {
                                if (mResponse.isSuccess()) {
                                    if (Utils.isNetworkAvailable(mContext)) {
                                        loadData();
                                    } else {
                                        Toast.makeText(mContext, getString(R.string.error_msg_no_internet), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    new MyDialog(mResponse.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                    }
                });
    }

    private void loadOrderDetail(int id) {
        waitDialog.show();

        RestManager.getApi()
//                .loadHrStringJson(fillMapHeader(), HR_VACATION_GET + App.prefs.getLogin() + "/" + id)
                .loadHrStringJson(fillMapHeader(), HR_VACATION_GET + loginUser + "/" + id)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        waitDialog.dismiss();
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            OrderVacationResponse mVacationResponse = new Gson().fromJson(response.body(), OrderVacationResponse.class);

                            if (mVacationResponse != null) {
                                if (mVacationResponse.isSuccess()) {
                                    ((MainActivity) Objects.requireNonNull(getActivity())).switchToFragment(CreateVacationFragment.newInstance(mVacationResponse), false);
                                } else {
                                    new MyDialog(mVacationResponse.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                    }
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(call!=null)
            call.cancel();
    }
}
