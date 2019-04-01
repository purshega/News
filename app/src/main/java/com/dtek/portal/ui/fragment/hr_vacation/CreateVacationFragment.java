package com.dtek.portal.ui.fragment.hr_vacation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.hr_vacation.LocationList;
import com.dtek.portal.models.hr_vacation.OrderVacationRequest;
import com.dtek.portal.models.hr_vacation.OrderVacationResponse;
import com.dtek.portal.models.hr_vacation.TotalDaysRequest;
import com.dtek.portal.models.hr_vacation.TotalDaysResponse;
import com.dtek.portal.models.hr_vacation.VacationList;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.SpinAdapter;
import com.dtek.portal.ui.dialog.DatePickerDialog;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.utils.PickerUtils;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.TextUtils.isDigitsOnly;
import static android.text.TextUtils.isEmpty;
import static com.dtek.portal.utils.ConvertTime.dateToString;
import static com.dtek.portal.utils.ConvertTime.sdfDate;
import static com.dtek.portal.utils.ConvertTime.stringToDate;

public class CreateVacationFragment extends BaseVacationFragment {
    private static final String TAG = "CreateVacationFragment";
    private static final int REQUEST_CODE_LOGIN = 100;
    private static final String ARG_LEAVE_CURRENT = "leaveCurrent";
    private static final int REQUEST_DATE_START = 1; // константа для кода запроса
    private static final int REQUEST_DATE_END = 2; // константа для кода запроса

    @BindView(R.id.spinner_location)
    Spinner mSpinnerLocation;
    @BindView(R.id.spinner_vacation)
    Spinner mSpinnerVacation;
    @BindView(R.id.tv_balance_day)
    TextView mTvBalanceDay;
    @BindView(R.id.ll_prepaid_day)
    LinearLayout mLlPrepaidDay;
    @BindView(R.id.tv_prepaid_day)
    TextView mTvPrepaidDay;
    @BindView(R.id.et_date_start)
    EditText mEtDateStart;
    @BindView(R.id.et_date_end)
    EditText mEtDateEnd;
    @BindView(R.id.tv_total_days)
    TextView mTvTotalDays;
    @BindView(R.id.cb_skip_chief)
    CheckBox mCbSkipChief;
    @BindView(R.id.et_description)
    EditText mEtDescription;

    private List<LocationList.Location> mLocationList;
    private List<VacationList.Vacation> mVacationList;
    private LocationList.Location mLocation;
    private VacationList.Vacation mVacation;
    private OrderVacationResponse mCurrentVacation;
    private Date dateStart = null;
    private Date dateEnd = null;

    public static CreateVacationFragment newInstance(OrderVacationResponse currentVacation) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_LEAVE_CURRENT, currentVacation);

        CreateVacationFragment fragment = new CreateVacationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public CreateVacationFragment() {
        // Required empty public constructor
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            mCurrentVacation = bundle.getParcelable(ARG_LEAVE_CURRENT);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readBundle(getArguments());
        mVacationList = PreferenceUtils.getLeave();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hr_leave_create, container, false);
        ButterKnife.bind(this, v);

        setViewDetail();
        getData();
        initSpinnerVacation();
        return v;
    }

    private void getData() {
        if (mLocationList == null || mLocationList.isEmpty()) {
            if (Utils.isNetworkAvailable(mContext)) {
                loadLocation();
            } else {
                Toast.makeText(mContext, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
            }
        } else {
            setCurrentLocation(mCurrentVacation.getIdLocation());
        }

    }

    private void loadLocation() {
        waitDialog.show();

        RestManager.getApi()
                .loadHrStringJson(fillMapHeader(), "")
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        waitDialog.dismiss();
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            LocationList mLocations = new Gson().fromJson(response.body(), LocationList.class);

                            if (mLocations != null) {
                                if (mLocations.isSuccess()) {
                                    mLocationList = mLocations.getLocations();
//                                    LocationList.BTLocation location = new LocationList.BTLocation(0, "нет в списке");
//                                    mLocationList.add(0, location);
                                    initSpinnerLocation();
                                } else {
                                    new MyDialog(mLocations.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
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

    private void setViewDetail() {
        if (mCurrentVacation != null) {
            setCurrentLocation(mCurrentVacation.getIdLocation());
            setCurrentVacation(mCurrentVacation.getIdVacation());

            mTvBalanceDay.setText(String.valueOf(mCurrentVacation.getDays()));
            mTvPrepaidDay.setText(String.valueOf(mCurrentVacation.getPrepaymentDays()));

            try {
                dateStart = sdfDate.parse(mCurrentVacation.getStartDate());
                dateEnd = sdfDate.parse(mCurrentVacation.getEndDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mEtDateStart.setText(mCurrentVacation.getStartDate());
            mEtDateEnd.setText(mCurrentVacation.getEndDate());
            mTvTotalDays.setText(String.valueOf(mCurrentVacation.getSummDays()));
            mCbSkipChief.setChecked(mCurrentVacation.isSkipChief());
            mEtDescription.setText(mCurrentVacation.getComment());
        }
    }

    private void initSpinnerLocation() {
        SpinAdapter<LocationList.Location> adapter = new SpinAdapter<>(
                mContext,
                android.R.layout.simple_spinner_dropdown_item,
                mLocationList);
        mSpinnerLocation.setAdapter(adapter);

        if (mCurrentVacation != null && mCurrentVacation.getNameLocation() != null) {
            setCurrentLocation(mCurrentVacation.getIdLocation());
        }
    }

    private void setCurrentLocation(int idLocation) {
        if (mLocationList != null && !mLocationList.isEmpty()) {
            int position = -1;
            for (int i = 0; i < mLocationList.size(); i++) {
                if (mLocationList.get(i).getId() == idLocation) {
                    position = i;
                    break;  // uncomment to get the first instance
                }
            }
            mSpinnerLocation.setSelection(position);
        }
    }

    private void initSpinnerVacation() {
        SpinAdapter<VacationList.Vacation> adapter = new SpinAdapter<>(
                mContext,
                android.R.layout.simple_spinner_dropdown_item,
                mVacationList);
        mSpinnerVacation.setAdapter(adapter);

        if (mCurrentVacation != null && mCurrentVacation.getNameVacation() != null) {
            setCurrentVacation(mCurrentVacation.getIdVacation());
        }
    }

    private void setCurrentVacation(int idVacation) {
        if (mVacationList != null && !mVacationList.isEmpty()) {
            int position = -1;
            for (int i = 0; i < mVacationList.size(); i++) {
                if (mVacationList.get(i).getId() == idVacation) {
                    position = i;
                    break;  // uncomment to get the first instance
                }
            }
            mSpinnerVacation.setSelection(position);
        }
    }

    @OnItemSelected({R.id.spinner_location, R.id.spinner_vacation})
    public void spinnerItemSelected(Spinner spinner, int position) {
        switch (spinner.getId()) {
            case R.id.spinner_location:
                mLocation = mLocationList.get(position);
                break;
            case R.id.spinner_vacation:
                mVacation = mVacationList.get(position);
                if (mVacation.getDays() != null) {
                    mTvBalanceDay.setText(String.valueOf(mVacation.getDays()));
                } else {
                    mTvBalanceDay.setText("");
                }
                setPrepaidDay();
                break;
        }
    }

    @OnClick(R.id.btn_send)
    void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                OrderVacationRequest orderRequest = fillOrderRequest(); // наполняем модель заявки
//                new MyDialog(orderRequest.toString()).show(getFragmentManager(), "fragmentDialog");
                if (Utils.isNetworkAvailable(mContext) && isValidate()) {
                    sendOrder(orderRequest);
                }
                break;
        }
    }

    private OrderVacationRequest fillOrderRequest() {
        OrderVacationRequest orderRequest = new OrderVacationRequest();
//        orderRequest.setUserLogin(App.prefs.getLogin());
        orderRequest.setUserLogin(loginUser);
        if (mCurrentVacation != null && mCurrentVacation.getId() != null) {
            orderRequest.setIdVacation(mCurrentVacation.getId());
        }
        orderRequest.setDestinationID(mLocation.getId());
        orderRequest.setVacationTypeId(mVacation.getId());
        orderRequest.setDateStart(dateStart);
        orderRequest.setDateFinish(dateEnd);
        orderRequest.setSkipChief(mCbSkipChief.isChecked());
        orderRequest.setComments(mEtDescription.getText().toString());
        return orderRequest;
    }

    private boolean isValidate() {
        boolean error = true;
        if (mEtDateStart.getText().toString().isEmpty()) {
//            mEtDateStart.setError("");
            error = false;
        }
        if (mEtDateEnd.getText().toString().isEmpty()) {
//            mEtDateEnd.setError("");
            error = false;
        }
//        if (mCbSkipChief.isChecked() && mEtDescription.getText().toString().trim().isEmpty()) {
//            mEtDescription.setError("");
//            error = false;
//        }
        String sTotalDays = mTvTotalDays.getText().toString();
        if (!sTotalDays.isEmpty() && TextUtils.isDigitsOnly(sTotalDays)) {
            if (Integer.valueOf(sTotalDays) == 0) {
//                mEtDateStart.setError("");
//                mEtDateEnd.setError("");
                error = false;
            }
        }
        return error;
    }

//    public static boolean isNumeric(String str) {
//        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
////        return str.matches("[+-]?\\d*(\\.\\d+)?");
//    }

    OrderVacationRequest mResponse;

    private void sendOrder(OrderVacationRequest orderVacation) {
        waitDialog.show();

        RestManager.getApi()
                .sendOrderVacationBeta(fillMapHeader(), orderVacation)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        waitDialog.dismiss();
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            mResponse = new Gson().fromJson(response.body(), OrderVacationRequest.class);
                            if (CreateVacationFragment.this.isResumed()) {
                                openNewFragment();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        if (CreateVacationFragment.this.isResumed()) {
                            new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                        }
                    }
                });
    }

    private void openNewFragment() {
        if (mResponse != null) {
            if (mResponse.isSuccess()) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            } else {
                if (CreateVacationFragment.this.isResumed()) {
                    new MyDialog(mResponse.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                }
            }
        }
    }

    @OnClick({R.id.et_date_start, R.id.et_date_end})
    void onDateClick(View view) {
        switch (view.getId()) {
            case R.id.et_date_start:
                Date dateStartCurrent = stringToDate(mEtDateStart.getText().toString());
//                Date dateStartMin = addDaysToDate(new Date(), 1);
                Date dateStartMin = new Date();
                Date dateStartMax = null;
                if (!TextUtils.isEmpty(mEtDateEnd.getText().toString())) {
                    if (dateStartMin.before(stringToDate(mEtDateEnd.getText().toString()))) {
                        dateStartMax = stringToDate(mEtDateEnd.getText().toString());
                    } else {
                        mEtDateEnd.setText("");
                        dateStartCurrent = dateStartMin;
                    }
                }
                getDatePicker(dateStartCurrent, dateStartMin, dateStartMax, REQUEST_DATE_START);
                break;
            case R.id.et_date_end:
                Date dateEndCurrent = stringToDate(mEtDateEnd.getText().toString());
                Date dateEndMin = new Date();
                if (!TextUtils.isEmpty(mEtDateStart.getText().toString())) {
                    dateEndCurrent = stringToDate(mEtDateStart.getText().toString());
//                    dateEndMin = stringToDate(mEtDateStart.getText().toString());
                }
                if (dateEndCurrent.before(new Date())) {
                    dateEndCurrent = dateEndMin;
                }
                dateEndMin = dateEndCurrent;

                getDatePicker(dateEndCurrent, dateEndMin, null, REQUEST_DATE_END);
                break;
        }
    }

    private void getDatePicker(Date dateCurrent, Date dateMin, Date dateMax, int request) {
        PickerUtils.initDatePicker(CreateVacationFragment.this,
                dateCurrent,
                dateMin,
                dateMax,
                request);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_DATE_START:
                    dateStart = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    mEtDateStart.setText(dateToString(dateStart));
                    mEtDateStart.setError(null); // убираем иконку незаполненого поля
                    getTotalDays(dateStart, dateEnd);
                    break;
                case REQUEST_DATE_END:
                    dateEnd = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    mEtDateEnd.setText(dateToString(dateEnd));
                    mEtDateEnd.setError(null);
                    getTotalDays(dateStart, dateEnd);
                    break;
                case REQUEST_CODE_LOGIN:
//                    loadDefaultInfo();
                    break;
            }
        }
    }

    private void getTotalDays(Date dStart, Date dEnd) {
        String sDateStart = mEtDateStart.getText().toString();
        String sDateEnd = mEtDateEnd.getText().toString();
        if (!isEmpty(sDateStart) && !isEmpty(sDateEnd)) {
            loadTotalDays(dStart, dEnd);
        } else {
            mTvTotalDays.setText(getString(R.string.text_error_total_days));
        }
    }

    private void loadTotalDays(Date dateStart, Date dateEnd) {
        waitDialog.show();

        TotalDaysRequest totalDays = new TotalDaysRequest();
        totalDays.setIdVacation(mVacation.getId());
        totalDays.setStartDate(dateStart);
        totalDays.setFinishDate(dateEnd);

        RestManager.getApi()
                .getCountDays(fillMapHeader(), totalDays)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        waitDialog.dismiss();
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            TotalDaysResponse mResponse = new Gson().fromJson(response.body(), TotalDaysResponse.class);

                            if (mResponse != null) {
                                if (!mResponse.isSuccess()) {
                                    new MyDialog(mResponse.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                                }
                                setTotalDays(mResponse);
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

    private void setTotalDays(TotalDaysResponse mResponse) {
        if (mResponse.getCountDays() != null) {
            mTvTotalDays.setText(String.valueOf(mResponse.getCountDays()));
        } else {
            mTvTotalDays.setText("");
        }
        setPrepaidDay();
//        if (mResponse.getCountDays() > 0) {
//            mEtDateStart.setError(null);
//            mEtDateEnd.setError(null);
//        }
    }

    private void setPrepaidDay() {
        String sTotalDays = mTvTotalDays.getText().toString();
        String sBalanceDays = mTvBalanceDay.getText().toString();
        if (!isEmpty(sBalanceDays) && !isEmpty(sTotalDays) && isDigitsOnly(sBalanceDays) && isDigitsOnly(sTotalDays)) {
            int balanceDay = Integer.parseInt(sBalanceDays);
            int totalDays = Integer.parseInt(sTotalDays);
            if (mVacation.getName().equals(mVacationList.get(0).getName()) && totalDays > balanceDay) {
                mLlPrepaidDay.setVisibility(View.VISIBLE);
                int prepaidDay = totalDays - balanceDay;
                mTvPrepaidDay.setText(String.valueOf(prepaidDay));
            } else {
                mLlPrepaidDay.setVisibility(View.GONE);
                mTvPrepaidDay.setText("");
            }
        } else {
            mLlPrepaidDay.setVisibility(View.GONE);
            mTvPrepaidDay.setText("");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (getActivity() != null) {
        ((MainActivity) Objects.requireNonNull(getActivity())).showUpButton();
//        }
        Handler uiHandler = new Handler();
        uiHandler.postDelayed(this::openNewFragment, 500);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (getActivity() != null) {
        ((MainActivity) Objects.requireNonNull(getActivity())).showBurgerButton();
//        }
    }
}
