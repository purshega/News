package com.dtek.portal.ui.fragment.itsm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.itsm.ItDetail;
import com.dtek.portal.models.itsm.ItResponse;
import com.dtek.portal.models.itsm.ItService;
import com.dtek.portal.models.itsm.ItUpdate;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.dialog.ConfirmDialog;
import com.dtek.portal.ui.dialog.EditTextDialog;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.ITSM_CONTENT_TYPE_SOAP;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_CLOSE;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_CLOSE_RESP;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_GET;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_RATE;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_RATE_RESP;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_RETURN;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_RETURN_REASON;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_RETURN_REASON_RESP;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_RETURN_RESP;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_REVOKED;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_REVOKED_RESP;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_CONTENT_TYPE;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_REQUEST;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_RESPONSE;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_URL;
import static com.dtek.portal.Const.HTTP.ITSM_URL_CLOSE;
import static com.dtek.portal.Const.HTTP.ITSM_URL_CREATE;
import static com.dtek.portal.Const.HTTP.ITSM_URL_QUEUED;
import static com.dtek.portal.Const.HTTP.ITSM_URL_REVOKED;
import static com.dtek.portal.Const.HTTP.ITSM_URL_TICKET_ID;
import static com.dtek.portal.ui.adapter.it.BaseItsmAdapter.STATE_CLOSED;
import static com.dtek.portal.ui.adapter.it.BaseItsmAdapter.STATE_DISAPPROVED;
import static com.dtek.portal.ui.adapter.it.BaseItsmAdapter.STATE_NEW;
import static com.dtek.portal.ui.adapter.it.BaseItsmAdapter.STATE_RESOLVED;
import static com.dtek.portal.ui.adapter.it.BaseItsmAdapter.STATE_WAITING;
import static com.dtek.portal.ui.adapter.it.BaseItsmAdapter.STATE_WITHDRAWN;

public class DetailItsmFragment extends Fragment {

    private static final String TAG = "DetailItsmFragment";
    protected static final String ARG_IT_SERVICE = "it_service";
    private static final int REQUEST_DIALOG_ET = 1; // константа для кода запроса
    private static final int REQUEST_DIALOG_CONFIRM = 2; // константа для кода запроса
    private static final int REQUEST_CODE_LOGIN = 100;

    private Context mContext;
    private ProgressDialog waitDialog;
    @BindView(R.id.tv_number) TextView mTvNumber;
    @BindView(R.id.tv_status) TextView mTvStatus;
    @BindView(R.id.tv_initiator) TextView mTvInitiator;
    @BindView(R.id.tv_date_start) TextView mTvDateStart;
    @BindView(R.id.ll_date_plan) LinearLayout mLlDatePlan;
    @BindView(R.id.tv_date_plan) TextView mTvDatePlan;
    @BindView(R.id.ll_date_end) LinearLayout mLlDateEnd;
    @BindView(R.id.tv_date_end) TextView mTvDateEnd;
    @BindView(R.id.tv_name_affects) TextView mTvAffects;
    @BindView(R.id.tv_address) TextView mTvAddress;
    @BindView(R.id.tv_urgency) TextView mTvUrgency;
    @BindView(R.id.tv_subject) TextView mTvSubject;
    @BindView(R.id.tv_description) TextView mTvDescription;
    @BindView(R.id.tv_solution) TextView mTvSolution;
    @BindView(R.id.ll_rating) LinearLayout mLlRating;
    @BindView(R.id.btn_revoke) Button mBtnRevoke;
    @BindView(R.id.btn_rate) Button mBtnRate;
    @BindView(R.id.btn_return) Button mBtnReturn;
    @BindView(R.id.spinner_rating) Spinner mSpinnerRating;
    @BindView(R.id.et_comment_rating) EditText mEtCommentRating;
    @BindView(R.id.layout_solution) LinearLayout layoutSolution;
    protected ItService itService;
    protected ItDetail itDetail;

    public static DetailItsmFragment newInstance(ItService itService) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_IT_SERVICE, itService);

        DetailItsmFragment fragment = new DetailItsmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DetailItsmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_it_service_detail, container, false);
        ButterKnife.bind(this, v);

        initDialog(); // диалоги
        getOrderId();
        initRating();

        return v;
    }

    protected void initView(){
    }

    protected void initRating() {
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(mContext, R.array.rating_it, R.layout.item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerRating.setAdapter(adapter);
    }

    @Optional @OnItemSelected(R.id.spinner_rating)
    void spinnerItemSelected(int position) {
        if (position == 0) {
            mBtnReturn.setEnabled(true);
            mBtnReturn.setClickable(true);
        } else {
            mBtnReturn.setEnabled(false);
            mBtnReturn.setClickable(false);
        }
    }

    @Optional @OnTextChanged(R.id.et_comment_rating)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            mBtnReturn.setEnabled(false);
            mBtnReturn.setClickable(false);
        } else {
            mBtnReturn.setEnabled(true);
            mBtnReturn.setClickable(true);
        }
    }

    protected void getOrderId() { // перадача объекта со списка
        itService = new ItService(Parcel.obtain());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            itService = bundle.getParcelable(ARG_IT_SERVICE);
            if (Utils.isNetworkAvailable(mContext)) {
                loadOrderDetail();
            } else {
                Toast.makeText(mContext, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadOrderDetail() {
        waitDialog.show();

        RestManager.getApi()
                .loadItStringJson(getMapForOrderDetail())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            String strJson = response.body();
                            Gson gson = new Gson();
                            itDetail = gson.fromJson(strJson, ItDetail.class);
//                                itDetail.getRoot().setViewOfStatus(STATE_RESOLVED);
                            setViewDetail();
                            setViewOfStatus();
                            initView();
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                    }
                });
    }

    private void setViewDetail() {

        mTvNumber.setText(itDetail.getRoot().getIdOrder());
        mTvStatus.setText(itDetail.getRoot().getStatus());
        mTvInitiator.setText(itDetail.getRoot().getAuthor());
        mTvDateStart.setText(itDetail.getRoot().getCreateDate());
        mTvDatePlan.setText(itDetail.getRoot().getEndDateP());
        mTvDateEnd.setText(itDetail.getRoot().getEndDate());
        mTvAffects.setText(itDetail.getRoot().getUser());
        mTvAddress.setText(itDetail.getRoot().getAddress());
        if (!itDetail.getRoot().getUrgency().equals("")) {
            mTvUrgency.setText(getElement(getResources().getStringArray(R.array.urgency),
                    Integer.parseInt(itDetail.getRoot().getUrgency()) - 1));
        }
        mTvSubject.setText(itDetail.getRoot().getSubject());
        mTvDescription.setText(itDetail.getRoot().getDetails());
        if(!itDetail.getRoot().getSolution().equals("")) {
            layoutSolution.setVisibility(View.VISIBLE);
            mTvSolution.setText(itDetail.getRoot().getSolution());
        }
        mEtCommentRating.setText(itDetail.getRoot().getRateComment());
        if (!itDetail.getRoot().getRate().equals("")) {
            switch (itDetail.getRoot().getRate()) {
                case "4":
                    mSpinnerRating.setSelection(1);
                    break;
                case "3":
                    mSpinnerRating.setSelection(2);
                    break;
                case "2":
                    mSpinnerRating.setSelection(3);
                    break;
                case "1":
                    mSpinnerRating.setSelection(4);
                    break;
            }
        }
    }

    private String getElement(String[] arrayOfInts, int index) {
        return arrayOfInts[index];
    }

    private void setViewOfStatus() {
        switch (itDetail.getRoot().getStatus()) {
            case STATE_NEW:
                mLlDatePlan.setVisibility(View.GONE);
                break;
            case STATE_WAITING:
                mBtnRevoke.setVisibility(View.GONE);
                break;
            case STATE_RESOLVED:
                mLlRating.setVisibility(View.VISIBLE);
                mBtnRate.setVisibility(View.VISIBLE);
                if (itDetail.getRoot().getRate().equals("")) {
                    mBtnReturn.setVisibility(View.VISIBLE);
                }
                mLlDatePlan.setVisibility(View.GONE);
                mLlDateEnd.setVisibility(View.VISIBLE);
                mBtnRevoke.setVisibility(View.GONE);
                break;
            case STATE_DISAPPROVED:
                mBtnReturn.setVisibility(View.VISIBLE);
                mLlDatePlan.setVisibility(View.GONE);
                mLlDateEnd.setVisibility(View.VISIBLE);
                mBtnRevoke.setVisibility(View.GONE);
                break;
            case STATE_WITHDRAWN:
                mLlDatePlan.setVisibility(View.GONE);
                mLlDateEnd.setVisibility(View.VISIBLE);
                mBtnRevoke.setVisibility(View.GONE);
                break;
            case STATE_CLOSED:
                mLlDatePlan.setVisibility(View.GONE);
                mLlDateEnd.setVisibility(View.VISIBLE);
                mBtnRevoke.setVisibility(View.GONE);
                mLlRating.setVisibility(View.VISIBLE);
                mSpinnerRating.setEnabled(false);
                mSpinnerRating.setClickable(false);
                mEtCommentRating.setEnabled(false);
                mEtCommentRating.setClickable(false);
                break;
        }
    }

    @OnClick({R.id.btn_revoke, R.id.btn_rate, R.id.btn_return})
    void onButtonClick(View v) {
        if (Utils.isNetworkAvailable(mContext)) {
            switch (v.getId()) {
                case R.id.btn_revoke:
                    ConfirmDialog confirmDialog = new ConfirmDialog(getString(R.string.text_confirm));
                    confirmDialog.setTargetFragment(DetailItsmFragment.this, REQUEST_DIALOG_CONFIRM); // назначаем Fragment целевым фрагментом экземпляра DatePickerDialog
                    confirmDialog.show(Objects.requireNonNull(getFragmentManager()), null);
                    break;
                case R.id.btn_rate:
                    validationRate();
                    break;
                case R.id.btn_return:
                    EditTextDialog dialog = new EditTextDialog(getString(R.string.text_reason_return), getString(R.string.btn_it_return_ok));
                    dialog.setTargetFragment(DetailItsmFragment.this, REQUEST_DIALOG_ET); // назначаем Fragment целевым фрагментом экземпляра DatePickerDialog
                    dialog.show(Objects.requireNonNull(getFragmentManager()), null);
                    break;
            }
        } else {
            Snackbar.make(v, R.string.error_msg_no_internet, Snackbar.LENGTH_LONG).show();
        }
    }

    private void validationRate() {
        switch (mSpinnerRating.getSelectedItemPosition()) {
            case 1:
            case 2:
                sendRateOrder();
                break;
            case 3:
            case 4:
                if (mEtCommentRating.getText().toString().equals("")) {
                    new MyDialog(getString(R.string.text_error_rate_1_2))
                            .show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                } else {
                    sendRateOrder();
                }
                break;
            default:
                new MyDialog(getString(R.string.text_error_rate_0))
                        .show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                break;
        }
    }

    @NonNull
    private Map<String, String> mapHeader(String itsmUrl, String itsmFile, String itsmFileResp) {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        map.put(ITSM_KEY_URL, itsmUrl);
        map.put(ITSM_KEY_REQUEST, itsmFile);
        map.put(ITSM_KEY_RESPONSE, itsmFileResp);
        map.put(ITSM_KEY_CONTENT_TYPE, ITSM_CONTENT_TYPE_SOAP);
        return map;
    }

    @NonNull
    protected Map<String, String> getMapForOrderDetail() {
        Timber.d("MAP FROM IT -------------------------------- ");
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        map.put(ITSM_KEY_URL, ITSM_URL_TICKET_ID + itService.getIdOrder());
        map.put(ITSM_KEY_RESPONSE, ITSM_FILE_GET);
        return map;
    }

    private void restError(@NonNull Response<String> response) {
        RestManager.printResponseLog(response);
        switch (response.code()) {
            case 400:
            case 401:
                ((MainActivity) Objects.requireNonNull(getActivity())).logout();
                break;
            case 502:
                waitDialog.dismiss();
                new MyDialog(getString(R.string.text_error_itsm))
                        .show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                break;
        }
    }

    private void sendRevokeOrder() {
        waitDialog.show();

        ItUpdate itRevoke = new ItUpdate();
        ItUpdate.Root root = new ItUpdate.Root();
        root.setIdOrder(itDetail.getRoot().getIdOrder());
        itRevoke.setRoot(root);

        Gson gson = new Gson();
        String strItRevoke = gson.toJson(itRevoke);
        Timber.d(strItRevoke);

        RestManager.getApi()
                .sendItOrder(mapHeader(ITSM_URL_REVOKED, ITSM_FILE_REVOKED, ITSM_FILE_REVOKED_RESP), strItRevoke)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            String strJson = response.body();
                            Gson gson = new Gson();
                            ItResponse itResponse = gson.fromJson(strJson, ItResponse.class);
                            if (itResponse.getRoot() != null && itResponse.getRoot().isResult()) {
                                if (getActivity() != null) {
                                    getActivity().onBackPressed();
                                }
                            }
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                    }
                });
    }

    private void sendRateOrder() {
        waitDialog.show();

        ItUpdate itUpdate = new ItUpdate();
        ItUpdate.Root root = new ItUpdate.Root();
        root.setIdOrder(itDetail.getRoot().getIdOrder());
        root.setRate(String.valueOf(mSpinnerRating.getSelectedItem().toString().charAt(0)));
        root.setComment(mEtCommentRating.getText().toString());
        root.setLoginId(PreferenceUtils.getLogin());
        itUpdate.setRoot(root);

        Gson gson = new Gson();
        String strItRate = gson.toJson(itUpdate);
        Timber.d(strItRate);

        RestManager.getApi()
                .sendItOrder(mapHeader(ITSM_URL_CREATE, ITSM_FILE_RATE, ITSM_FILE_RATE_RESP), strItRate)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            String strJson = response.body();
                            Gson gson = new Gson();
                            ItResponse itResponse = gson.fromJson(strJson, ItResponse.class);
                            if (itResponse.getRoot() != null && itResponse.getRoot().isResult()) {
                                sendRateClose();
                            }else{
                                waitDialog.dismiss();
                            }
                        }else{
                            waitDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                    }
                });

    }

    private void sendRateClose() {
        waitDialog.show();

        ItUpdate itRevoke = new ItUpdate();
        ItUpdate.Root root = new ItUpdate.Root();
        root.setIdOrder(itDetail.getRoot().getIdOrder());
        itRevoke.setRoot(root);

        Gson gson = new Gson();
        String strItRevoke = gson.toJson(itRevoke);
        Timber.d(strItRevoke);

        RestManager.getApi()
                .sendItOrder(mapHeader(ITSM_URL_CLOSE, ITSM_FILE_CLOSE, ITSM_FILE_CLOSE_RESP), strItRevoke)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            String strJson = response.body();
                            Gson gson = new Gson();
                            ItResponse itResponse = gson.fromJson(strJson, ItResponse.class);
                            if (itResponse.getRoot() != null && itResponse.getRoot().isResult()) {
                                if (getActivity() != null) {
                                    getActivity().onBackPressed();
                                }
                            }
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                    }
                });

    }

    private void sendReasonReturnOrder(String s) {
        waitDialog.show();

        ItUpdate itReason = new ItUpdate();
        ItUpdate.Root root = new ItUpdate.Root();
        root.setIdOrder(itDetail.getRoot().getIdOrder());
        root.setComment(s);
        root.setLoginId(PreferenceUtils.getLogin());
        itReason.setRoot(root);

        Gson gson = new Gson();
        String strItRate = gson.toJson(itReason);
        Timber.d(strItRate);

        RestManager.getApi()
                .sendItOrder(mapHeader(ITSM_URL_CREATE, ITSM_FILE_RETURN_REASON, ITSM_FILE_RETURN_REASON_RESP), strItRate)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            String strJson = response.body();
                            Gson gson = new Gson();
                            ItResponse itResponse = gson.fromJson(strJson, ItResponse.class);
                            if (itResponse.getRoot() != null && itResponse.getRoot().isResult()) {
                                sendReturnOrder();
                            }else{
                                waitDialog.dismiss();
                            }
                        }else{
                            waitDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                    }
                });

    }

    private void sendReturnOrder() {
        waitDialog.show();

        ItUpdate itUpdate = new ItUpdate();
        ItUpdate.Root root = new ItUpdate.Root();
        root.setIdOrder(itDetail.getRoot().getIdOrder());
        itUpdate.setRoot(root);

        Gson gson = new Gson();
        String strItRate = gson.toJson(itUpdate);
        Timber.d(strItRate);

        RestManager.getApi()
                .sendItOrder(mapHeader(ITSM_URL_QUEUED, ITSM_FILE_RETURN, ITSM_FILE_RETURN_RESP), strItRate)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            String strJson = response.body();
                            Gson gson = new Gson();
                            ItResponse itResponse = gson.fromJson(strJson, ItResponse.class);
                            if (itResponse.getRoot() != null && itResponse.getRoot().isResult()) {
                                if (getActivity() != null) {
                                    getActivity().onBackPressed();
                                }
                            }
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                    }
                });

    }

    protected void initDialog() {
        waitDialog = WaitDialog.showDialog(mContext, getString(R.string.dialog_wait));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showUpButton();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showBurgerButton();
        }
    }

    // Реакция на получение данных от диалогового окна
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_DIALOG_ET:
                    String s = (String) data.getSerializableExtra(EditTextDialog.EXTRA_STRING);
                    if (TextUtils.isEmpty(s.trim())) {
                        new MyDialog(getString(R.string.text_error_reason_return_no))
                                .show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                    } else {
                        if (Utils.isNetworkAvailable(mContext)) {
                            sendReasonReturnOrder(s);
                        } else {
                            Toast.makeText(mContext, getString(R.string.error_msg_no_internet), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case REQUEST_DIALOG_CONFIRM:
                    sendRevokeOrder();
                    break;
                case REQUEST_CODE_LOGIN:
                    getOrderId();
                    break;
            }
        }
    }
}
