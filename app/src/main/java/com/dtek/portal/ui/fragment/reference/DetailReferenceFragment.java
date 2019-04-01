package com.dtek.portal.ui.fragment.reference;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.dtek.portal.models.itsm.ItResponse;
import com.dtek.portal.models.itsm.ItService;
import com.dtek.portal.models.itsm.ItUpdate;
import com.dtek.portal.models.reference.Param;
import com.dtek.portal.models.reference.ReferenceDetail;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.reference.ReferenceParamAdapter;
import com.dtek.portal.ui.dialog.ConfirmDialog;
import com.dtek.portal.ui.dialog.EditTextDialog;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.ITSM_CONTENT_TYPE_SOAP;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_CLOSE;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_CLOSE_RESP;
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
import static com.dtek.portal.Const.References.REFERENCE_FILE_GET_DETAIL;
import static com.dtek.portal.Const.References.REFERENCE_URL_GET_DETAIL;
import static com.dtek.portal.ui.adapter.it.BaseItsmAdapter.STATE_CLOSED;
import static com.dtek.portal.ui.adapter.it.BaseItsmAdapter.STATE_DISAPPROVED;
import static com.dtek.portal.ui.adapter.it.BaseItsmAdapter.STATE_NEW;
import static com.dtek.portal.ui.adapter.it.BaseItsmAdapter.STATE_RESOLVED;
import static com.dtek.portal.ui.adapter.it.BaseItsmAdapter.STATE_WAITING;
import static com.dtek.portal.ui.adapter.it.BaseItsmAdapter.STATE_WITHDRAWN;


public class DetailReferenceFragment extends Fragment {

    private static final String ARG_REFERENCE_SERVICE = "reference_service";
    private static final int REQUEST_DIALOG_ET = 1;
    private static final int REQUEST_DIALOG_CONFIRM = 2;

    private Context mContext;
    private Dialog waitDialog;
    private ItService itService;
    private ReferenceDetail referenceDetail;

    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_initiator)
    TextView tvInitiator;
    @BindView(R.id.tv_date_create)
    TextView tvDateCreate;
    @BindView(R.id.tv_date_end)
    TextView tvDateEnd;
    @BindView(R.id.tv_date_plan)
    TextView tvDatePlan;
    @BindView(R.id.tv_name_affects)
    TextView tvNameAffects;
    @BindView(R.id.tv_name_organization)
    TextView tvNameOrganization;
    @BindView(R.id.tv_personnel_number)
    TextView tvPersonnelNumber;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_urgency)
    TextView tvUrgency;
    @BindView(R.id.tv_subject)
    TextView tvSubject;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.layout_solution)
    LinearLayout layoutSolution;
    @BindView(R.id.tv_solution)
    TextView tvSolution;
    @BindView(R.id.rv_reference_params)
    RecyclerView rvReferenceParams;
    @BindView(R.id.btn_revoke)
    Button mBtnRevoke;
    @BindView(R.id.btn_rate)
    Button mBtnRate;
    @BindView(R.id.btn_return)
    Button mBtnReturn;
    @BindView(R.id.ll_rating)
    LinearLayout mLlRating;
    @BindView(R.id.ll_date_end)
    LinearLayout mLlDateEnd;
    @BindView(R.id.ll_date_plan)
    LinearLayout mLlDatePlan;
    @BindView(R.id.spinner_rating)
    Spinner mSpinnerRating;
    @BindView(R.id.et_comment_rating)
    EditText mEtCommentRating;

    public static DetailReferenceFragment newInstance(ItService itService) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_REFERENCE_SERVICE, itService);

        DetailReferenceFragment fragment = new DetailReferenceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            itService = bundle.getParcelable(ARG_REFERENCE_SERVICE);
        }
    }

    public DetailReferenceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        readBundle(getArguments());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reference_service_detail, container, false);
        ButterKnife.bind(this, v);

        initDialog(); // диалоги
        initData();
        initRating();
        return v;
    }


    @OnClick({R.id.btn_revoke, R.id.btn_rate, R.id.btn_return})
    void onButtonClick(View v) {
        if (Utils.isNetworkAvailable(mContext)) {
            switch (v.getId()) {
                case R.id.btn_revoke:
                    ConfirmDialog confirmDialog = new ConfirmDialog(getString(R.string.text_confirm));
                    confirmDialog.setTargetFragment(DetailReferenceFragment.this, REQUEST_DIALOG_CONFIRM);
                    confirmDialog.show(Objects.requireNonNull(getFragmentManager()), null);
                    break;
                case R.id.btn_rate:
                    validationRate();
                    break;
                case R.id.btn_return:
                    EditTextDialog dialog = new EditTextDialog(getString(R.string.text_reason_return), getString(R.string.btn_it_return_ok));
                    dialog.setTargetFragment(DetailReferenceFragment.this, REQUEST_DIALOG_ET); // назначаем Fragment целевым фрагментом экземпляра DatePickerDialog
                    dialog.show(Objects.requireNonNull(getFragmentManager()), null);
                    break;
            }
        } else {
            Snackbar.make(v, R.string.error_msg_no_internet, Snackbar.LENGTH_LONG).show();
        }
    }

    private void initRating() {
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(mContext, R.array.rating_it, R.layout.item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerRating.setAdapter(adapter);
    }

    private void initData(){
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        map.put(ITSM_KEY_URL, REFERENCE_URL_GET_DETAIL + itService.getIdOrder());
        map.put(ITSM_KEY_RESPONSE, REFERENCE_FILE_GET_DETAIL);

        RestManager.getApi()
                .getReference(map)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            String strJson = response.body();
                            Timber.d("RESPONSE ==========        " + strJson);
                            Gson gson = new Gson();
                            referenceDetail = gson.fromJson(strJson, ReferenceDetail.class);
                            initView(referenceDetail);
                            setViewOfStatus();
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        new MyDialog(t.getMessage()).show(getFragmentManager(), "fragmentDialog");
                    }
                });
    }

    private void sendRevokeOrder() {
        waitDialog.show();

        ItUpdate itRevoke = new ItUpdate();
        ItUpdate.Root root = new ItUpdate.Root();
        root.setIdOrder(referenceDetail.getRoot().getIdOrder());
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
        root.setIdOrder(referenceDetail.getRoot().getIdOrder());
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
        root.setIdOrder(referenceDetail.getRoot().getIdOrder());
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
        root.setIdOrder(referenceDetail.getRoot().getIdOrder());
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
        root.setIdOrder(referenceDetail.getRoot().getIdOrder());
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

    private void setViewOfStatus() {
        switch (referenceDetail.getRoot().getStatus()) {
            case STATE_NEW:
                tvDatePlan.setVisibility(View.GONE);
                break;
            case STATE_WAITING:
                mBtnRevoke.setVisibility(View.GONE);
                break;
            case STATE_RESOLVED:
                mLlRating.setVisibility(View.VISIBLE);
                mBtnRate.setVisibility(View.VISIBLE);
                if (referenceDetail.getRoot().getRate().equals("")) {
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


    private void initView(ReferenceDetail referenceDetail){
        ReferenceDetail.Root root = referenceDetail.getRoot();
        tvNumber.setText(root.getIdOrder());
        tvStatus.setText(root.getStatus());
        tvInitiator.setText(root.getAuthor());
        tvDateCreate.setText(root.getCreateDate());
        tvDatePlan.setText(root.getEndDatePlan());
        tvDateEnd.setText(root.getEndDate());
        tvNameAffects.setText(root.getUser());
        tvNameOrganization.setText(root.getOrganization());
        tvPersonnelNumber.setText(root.getTabNum());
        tvPhoneNumber.setText(root.getPhoneNumber());
        tvAddress.setText(root.getAddress());
        tvUrgency.setText(root.getUrgency());
        tvSubject.setText(root.getSubject());
        tvDescription.setText(root.getDetails());
        if(!root.getSolution().equals("")) {
            layoutSolution.setVisibility(View.VISIBLE);
            tvSolution.setText(root.getSolution());
        }
        initParams(root.getParams());
        if (!root.getRate().equals("")) {
            switch (root.getRate()) {
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

    private void initParams(List<Param> params){
        params.remove(params.size()-1);
        params.remove(params.size()-1);
        ReferenceParamAdapter referenceAttributeAdapter = new ReferenceParamAdapter();
        rvReferenceParams.setLayoutManager(new LinearLayoutManager(mContext));
        rvReferenceParams.setAdapter(referenceAttributeAdapter);
        referenceAttributeAdapter.setItems(params);
    }

    protected void restError(@NonNull Response<String> response) {
        RestManager.printResponseLog(response);
        switch (response.code()) {
            case 400:
            case 401:
//                ((MainActivity) Objects.requireNonNull(getActivity())).logout();
                break;
            case 502:
                waitDialog.dismiss();
                new MyDialog(getString(R.string.text_error_itsm)).show(getFragmentManager(), "fragmentDialog");
                break;
        }
    }

    private void initDialog() {
        waitDialog = WaitDialog.setDialog(mContext);
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
//                case REQUEST_CODE_LOGIN:
//                    getOrderId();
//                    break;
            }
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

}
