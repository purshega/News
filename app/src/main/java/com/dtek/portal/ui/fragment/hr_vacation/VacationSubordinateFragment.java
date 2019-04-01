package com.dtek.portal.ui.fragment.hr_vacation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.hr_vacation.BoosDenyRequest;
import com.dtek.portal.models.hr_vacation.ResponseChief;
import com.dtek.portal.models.hr_vacation.SubordinateList;
import com.dtek.portal.models.push.PushData;
import com.dtek.portal.ui.adapter.hr.HrVacationSubordinateAdapter;
import com.dtek.portal.ui.dialog.ConfirmDialog;
import com.dtek.portal.ui.dialog.EditTextDialog;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.ui.dialog.VacationApproveDialog;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.HR_VACATION_BOSS_AGREE;
import static com.dtek.portal.Const.HTTP.HR_VACATION_SUBORDINATE;
import static com.dtek.portal.ui.activity.MainActivity.REQUEST_CODE_LOGIN;

public class VacationSubordinateFragment extends BaseVacationFragment
        implements HrVacationSubordinateAdapter.HrLeaveSubordinateListener {

    private static final String TAG = "VacationHistoryFragment";
    private static final int REQUEST_DIALOG_CONFIRM = 2; // константа для кода запроса
    private static final int REQUEST_DIALOG_ET = 3; // константа для кода запроса
    private static final int REQUEST_DIALOG_COMMENT = 4; // константа для кода запроса

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.main_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_empty_list)
    TextView mTvEmpty;
    private List<SubordinateList.Subordinate> mSubordinateList; // список
    private Call call;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubordinateList = new ArrayList<>();
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

        call = RestManager.getApi()
//                .loadHrStringJson(getHeaderMap(), HR_VACATION_SUBORDINATE + App.prefs.getLogin())
                .loadHrStringJson(getHeaderMap(), HR_VACATION_SUBORDINATE + loginUser);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                mProgressBar.setVisibility(View.GONE);
                RestManager.printResponseLog(response);
                if (response.isSuccessful() && response.body() != null) {
                    SubordinateList mSubordinates = new Gson().fromJson(response.body(), SubordinateList.class);

                    if (mSubordinates != null) {
                        if (mSubordinates.isSuccess()) {
                            mSubordinateList = mSubordinates.getSubordinateList();
                            setItemsToList();
                            if (mSubordinateList.isEmpty()) {
                                mTvEmpty.setVisibility(View.VISIBLE);
                                mTvEmpty.setText(getString(R.string.txt_empty_list));
                            }
                        } else {
                            if (VacationSubordinateFragment.this.isResumed()) {
                                new MyDialog(mSubordinates.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                            } else {
                                mTvEmpty.setVisibility(View.VISIBLE);
                                mTvEmpty.setText(mSubordinates.getMessage());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                if (VacationSubordinateFragment.this.isResumed())
                new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
            }
        });
    }

    @NonNull
    private Map<String, String> getHeaderMap() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        return map;
    }

    private void setItemsToList() {
        // используем linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        // создаем адаптер
        HrVacationSubordinateAdapter adapter = new HrVacationSubordinateAdapter(mSubordinateList);
        adapter.setHrLeaveSubordinateListener(this); // слушатель интерфейса

        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setItemViewCacheSize(10);
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
                for (SubordinateList.Subordinate subordinate : mSubordinateList) {
                    if (subordinate.getId() == pushData.getRequest_id()) {
                        intent.removeExtra(Const.PUSH.JSON_BODY);
                        onItemClick(subordinate);
                    }
                }
            }
        }
    }

    int idOrder;

    @Override
    public void onItemClick(SubordinateList.Subordinate subordinate) {
        VacationApproveDialog approveDialog = new VacationApproveDialog(subordinate);
        approveDialog.setTargetFragment(VacationSubordinateFragment.this, REQUEST_DIALOG_CONFIRM); // назначаем Fragment целевым фрагментом экземпляра DatePickerDialog
        approveDialog.show(Objects.requireNonNull(getFragmentManager()), null);
        idOrder = subordinate.getId();
    }

    // Реакция на получение данных от диалогового окна
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_DIALOG_CONFIRM:
                    sendBossAgreeOrder(idOrder);
                    break;
                case REQUEST_DIALOG_ET:
                    String s = (String) data.getSerializableExtra(EditTextDialog.EXTRA_STRING);
                    if (TextUtils.isEmpty(s.trim())) {
//                        new MyDialog(getString(R.string.text_error_boss_deny)).show(getFragmentManager(), "fragmentDialog");
                        ConfirmDialog confirmDialog = new ConfirmDialog(getString(R.string.text_error_boss_deny));
                        confirmDialog.setTargetFragment(VacationSubordinateFragment.this, REQUEST_DIALOG_COMMENT); // назначаем Fragment целевым фрагментом
                        confirmDialog.show(Objects.requireNonNull(getFragmentManager()), null);
                    } else {
                        if (Utils.isNetworkAvailable(mContext)) {
                            sendBossDenyOrder(idOrder, s);
                        } else {
                            Toast.makeText(mContext, getString(R.string.error_msg_no_internet), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case REQUEST_DIALOG_COMMENT:
                    initEditTextDialog();
                    break;
                case REQUEST_CODE_LOGIN:
//                    getOrderId();
                    break;
            }

        }
        if (resultCode == Activity.RESULT_CANCELED) {
            switch (requestCode) {
                case REQUEST_DIALOG_CONFIRM:
                    initEditTextDialog();
                    break;
            }
        }
    }

    private void initEditTextDialog() {
        EditTextDialog dialog = new EditTextDialog(getString(R.string.text_title_boss_deny), getString(R.string.btn_name_boss_deny));
        dialog.setTargetFragment(VacationSubordinateFragment.this, REQUEST_DIALOG_ET); // назначаем Fragment целевым
        dialog.show(Objects.requireNonNull(getFragmentManager()), null);
    }

    private void sendBossAgreeOrder(int id) {
        waitDialog.show();

        RestManager.getApi()
//                .loadHrStringJson(getHeaderMap(), HR_VACATION_BOSS_AGREE + App.prefs.getLogin() +"/" + id )
                .loadHrStringJson(getHeaderMap(), HR_VACATION_BOSS_AGREE + loginUser + "/" + id)
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

    private void sendBossDenyOrder(int id, String s) {
        waitDialog.show();

        BoosDenyRequest boosDeny = new BoosDenyRequest();
//        boosDeny.setUserLogin(App.prefs.getLogin());
        boosDeny.setUserLogin(loginUser);
        boosDeny.setId(id);
        boosDeny.setComments(s);

        RestManager.getApi()
//                .loadHrStringJson(getHeaderMap(), HR_VACATION_BOSS_DENY + App.prefs.getLogin() +"/" + id )
                .sendBossDeny(getHeaderMap(), boosDeny)
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

    @Override
    public void onDetach() {
        super.onDetach();
        if(call!=null)
            call.cancel();
    }

}
