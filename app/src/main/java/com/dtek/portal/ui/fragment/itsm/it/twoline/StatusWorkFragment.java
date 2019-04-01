package com.dtek.portal.ui.fragment.itsm.it.twoline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.dtek.portal.models.itsm.twoline.ItChangeRequest;
import com.dtek.portal.models.itsm.twoline.ItRootDetail;
import com.dtek.portal.ui.adapter.it.ItTwoLineStatusAdapter;
import com.dtek.portal.ui.dialog.DatePickerDialog;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.ui.dialog.TimePickerDialog;
import com.dtek.portal.utils.ConvertTime;
import com.dtek.portal.utils.PickerUtils;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.dtek.portal.Const.HTTP.ITSM_CONTENT_TYPE_ENCODED;
import static com.dtek.portal.Const.HTTP.ITSM_CONTENT_TYPE_SOAP;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_DONE;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_DONE_RESP;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_REJECT;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_REJECT_INCORRECTLY;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_REJECT_LITE;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_REJECT_RESP;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_RESP_LITE;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_TAKE_IN_WORK;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_TAKE_IN_WORK_LITE;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_TAKE_IN_WORK_OWNER;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_TAKE_IN_WORK_OWNER_RESP;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_TAKE_LITE_RESP_QUEUE;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_TAKE_LITE_RESP_REJECT;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_TAKE_QUEUE_LITE;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_WAIT;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_WAIT_RESP;
import static com.dtek.portal.Const.HTTP.ITSM_URL_DONE;
import static com.dtek.portal.Const.HTTP.ITSM_URL_REJECT;
import static com.dtek.portal.Const.HTTP.ITSM_URL_REJECT_INCORRECTLY;
import static com.dtek.portal.Const.HTTP.ITSM_URL_TAKE_IN_WORK;
import static com.dtek.portal.Const.HTTP.ITSM_URL_TAKE_LITE;
import static com.dtek.portal.Const.HTTP.ITSM_URL_TAKE_QUEUE;
import static com.dtek.portal.utils.ConvertTime.stringToDate;
import static com.dtek.portal.utils.ConvertTime.stringToTime;

public class StatusWorkFragment extends Base2LineFragment
        implements ItTwoLineStatusAdapter.SingleClickListener {
    private static final String TAG = "StatusWorkFragment";
    private static final String ARG_TASK = "arg_task";
    private static final String TYPE_WORK_ASSIGNMENT = "Рабочее задание";
    private static final String TYPE_INCIDENT = "Инцидент";

    public static final String STATUS_NEW = "Назначен";
    public static final String STATUS_NEW_INCIDENT = "Новое";
    public static final String STATUS_QUEUE = "В очереди";
    public static final String STATUS_IN_WORK = "В работе";
    public static final String STATUS_POSTPONE = "Отложено";

    private static final int REQUEST_DATE = 1;
    private static final int REQUEST_TIME = 2;

    private Calendar calendar = Calendar.getInstance();

    @BindView(R.id.rv_status)
    RecyclerView mRvStatus;
    @BindView(R.id.spinner_cause)
    Spinner mSpinnerCause;
    @BindView(R.id.ll_cause)
    LinearLayout mLlCause;
    @BindView(R.id.et_note)
    EditText mEtNote;
    @BindView(R.id.ll_note)
    LinearLayout mLlNote;
    @BindView(R.id.ll_date_time)
    LinearLayout llDateTime;

    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.et_date)
    EditText etDate;
    @BindView(R.id.et_time)
    EditText etTime;

    @BindString(R.string.text_status_take_queue)
    String takeQueue;
    @BindString(R.string.text_status_take_work)
    String takeWork;
    @BindString(R.string.text_status_reject_incorrectly)
    String rejectIncorrectly;
    @BindString(R.string.text_status_reject_return)
    String rejectReturn;
    @BindString(R.string.text_status_reject)
    String reject;
    @BindString(R.string.text_status_wait)
    String wait;
    @BindString(R.string.text_status_done)
    String done;
    @BindString(R.string.text_status_in_work)
    String inWork;

    private ItTwoLineStatusAdapter mAdapter;
    private ItRootDetail.TaskDetail taskDetail;
    private String willStatus;
    private String[] array;


    public StatusWorkFragment() {
        // Required empty public constructor
    }

    public static StatusWorkFragment newInstance(ItRootDetail.TaskDetail taskDetail) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_TASK, taskDetail);

        StatusWorkFragment fragment = new StatusWorkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            taskDetail = bundle.getParcelable(ARG_TASK);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_it_two_line_status, container, false);
        unbinder = ButterKnife.bind(this, v);

        readBundle(getArguments());
        initArrayFromStatus();
        initRecycler();
        return v;
    }

    private void initArrayFromStatus() {
        switch (taskDetail.getStatus()) {
            case STATUS_NEW:
            case STATUS_NEW_INCIDENT:
            case STATUS_QUEUE:
                if (taskDetail.getTypeOrder().equals(TYPE_WORK_ASSIGNMENT)) {
                    if (taskDetail.getOrigRecordClass().equals("SR"))
                        array = new String[]{takeQueue, takeWork, rejectIncorrectly, rejectReturn, reject};
                    else
                        array = new String[]{takeQueue, takeWork, rejectReturn, reject};
                } else if (taskDetail.getTypeOrder().equals(TYPE_INCIDENT)) {
                    array = new String[]{takeQueue, takeWork, rejectIncorrectly, rejectReturn, reject};
                }
                mLlNote.setVisibility(View.VISIBLE);
                break;
            case STATUS_IN_WORK:
                if (taskDetail.getTypeOrder().equals(TYPE_WORK_ASSIGNMENT)) {
                    if (taskDetail.getOrigRecordClass().equals("SR"))
                        array = new String[]{rejectIncorrectly, rejectReturn, reject, wait, done};
                    else
                        array = new String[]{rejectReturn, reject, wait, done};
                } else if (taskDetail.getTypeOrder().equals(TYPE_INCIDENT)) {
                    array = new String[]{rejectIncorrectly, rejectReturn, reject, wait, done};
                }
                mLlNote.setVisibility(View.VISIBLE);
                break;
            case STATUS_POSTPONE:
                array = new String[]{inWork};
                break;
            default:
                array = new String[]{};
                break;
        }
    }

    private void initRecycler() {
        mAdapter = new ItTwoLineStatusAdapter(array);
        mRvStatus.setAdapter(mAdapter);
//        mRvStatus.setHasFixedSize(true);
        mRvStatus.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClickListener(int position, View view) {
        mAdapter.selectedItem();
        willStatus = array[position];
        llDateTime.setVisibility(View.GONE);

        if (willStatus.equals(rejectReturn)) {
            if (taskDetail.getTypeOrder().equals(TYPE_WORK_ASSIGNMENT))
                setSpinnerAdapter(getFromResource(R.array.cause_revision));
            else if (taskDetail.getTypeOrder().equals(TYPE_INCIDENT))
                setSpinnerAdapter(getFromResource(R.array.cause_revision));
            tvReason.setText(R.string.text_name_status_cause);
        } else if (willStatus.equals(wait) && taskDetail.getTypeOrder().equals(TYPE_WORK_ASSIGNMENT)) {
            setSpinnerAdapter(getFromResource(R.array.cause_not_properly_qualified_view));
            tvReason.setText(R.string.text_name_status_cause_wait);
            llDateTime.setVisibility(View.VISIBLE);
        } else {
            mLlCause.setVisibility(View.GONE);
            mSpinnerCause.setAdapter(null);
        }
    }

    private void setSpinnerAdapter(ArrayAdapter<?> fromResource) {
        mLlCause.setVisibility(View.VISIBLE);
        mSpinnerCause.setAdapter(fromResource);
        mSpinnerCause.setSelection(1);
    }

    @NonNull
    private ArrayAdapter<?> getFromResource(int arrayResource) {
        return ArrayAdapter.createFromResource(mContext,
                arrayResource,
                R.layout.item_spinner_multi);
    }

    @OnClick({R.id.btn_cancel, R.id.btn_ok})
    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                Objects.requireNonNull(getFragmentManager()).popBackStack(); // шаг назад
                break;
            case R.id.btn_ok:

//                Objects.requireNonNull(getFragmentManager()).popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);// очистить стек фрагментов
//                Objects.requireNonNull(getFragmentManager()).popBackStackImmediate(); //  шаг назад
//                getFragmentManager().popBackStackImmediate(getFragmentManager().getBackStackEntryCount() - 2, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                Map<String, String> header = setMapHeaderFirst(willStatus);

//                new MyDialog(body).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                if (header != null) {
                    String body = setRequestToString();
                    if (Utils.isNetworkAvailable(mContext)) { //todo REFACTORING NEED
                        if (taskDetail.getTypeOrder().equals(TYPE_WORK_ASSIGNMENT)) {
                            if (willStatus.equals(rejectIncorrectly) || willStatus.equals(rejectReturn))
                                sendChangeOrderFirstGet(header, body);
                            else
                                sendChangeOrderFirst(header, body);
                        } else
                            sendChangeOrderFirst(header, body);
                    } else
                        Toast.makeText(mContext, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
                } else {
                    new MyDialog(getString(R.string.text_error_action)).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                }
                break;
        }
    }

    @OnClick({R.id.et_date, R.id.et_time})
    void onDateTimeClick(View view) {
        switch (view.getId()) {
            case R.id.et_date:
                PickerUtils.initDatePicker(StatusWorkFragment.this,
                        stringToDate(etDate.getText().toString()), new Date(), null, REQUEST_DATE);
                break;
            case R.id.et_time:
                PickerUtils.initTimePicker(StatusWorkFragment.this,
                        stringToTime(etTime.getText().toString()), REQUEST_TIME);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_DATE:
                    Date date = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    Calendar calDate = Calendar.getInstance();
                    calDate.setTime(date);
                    calendar.set(Calendar.DAY_OF_MONTH, calDate.get(Calendar.DAY_OF_MONTH));
                    calendar.set(Calendar.MONTH, calDate.get(Calendar.MONTH) + 1);
                    calendar.set(Calendar.YEAR, calDate.get(Calendar.YEAR));
                    etDate.setText(ConvertTime.dateToString(date));
                    break;
                case REQUEST_TIME:
                    Date time = (Date) data.getSerializableExtra(TimePickerDialog.EXTRA_TIME);
                    Calendar calTime = Calendar.getInstance();
                    calTime.setTime(time);
                    calendar.set(Calendar.MILLISECOND, calTime.get(Calendar.MILLISECOND));
                    calendar.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
                    calendar.set(Calendar.HOUR, calTime.get(Calendar.HOUR));
                    etTime.setText(ConvertTime.timeToString(time));
                    break;
            }
        }
    }

    private String setRequestToString() {
        ItChangeRequest itChange = new ItChangeRequest();
        ItChangeRequest.Root root = new ItChangeRequest.Root();
        root.setIdOrder(taskDetail.getIdOrder());
        root.setSiteId(taskDetail.getSiteId());
        root.setPersonId(PreferenceUtils.getLoginId());
//        if (mSpinnerCause.getSelectedItem() != null) {
//            if (willStatus.equals(wait)) {
//                ArrayAdapter<?> statusList = getFromResource(R.array.cause_not_properly_qualified_request);
//                root.setSubject((String) statusList.getItem(mSpinnerCause.getSelectedItemPosition()));
//            } else
//                root.setSubject(mSpinnerCause.getSelectedItem().toString());
//        } else
        if (willStatus.equals(done) && taskDetail.getTypeOrder().equals(TYPE_INCIDENT)) {
            root.setFactTime("1"); //указываем фактическое время выполнения 1 час
        }
        if (willStatus.equals(rejectReturn) && taskDetail.getTypeOrder().equals(TYPE_INCIDENT)) {
            root.setReturnReason(getReturnReason());
        }
        if (willStatus.equals(wait)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            String dateTime = formatter.format(calendar.getTime());
            root.setDate(dateTime);
            root.setReason(getSubject());
        }
        root.setSubject(getSubject());
        root.setDetails(mEtNote.getText().toString());
        itChange.setRoot(root);

        String strItChange = new Gson().toJson(itChange);
        Timber.d("GSON INSIDE ====================================  " + strItChange);
        return strItChange;
    }

    private String getSubject() {
        if (willStatus.equals(rejectIncorrectly) || willStatus.equals(reject))
            return "Причина отклонения";
        else if (mSpinnerCause.getSelectedItem() != null && taskDetail.getTypeOrder().equals(TYPE_WORK_ASSIGNMENT)) {
            if (willStatus.equals(wait)) {
                ArrayAdapter<?> statusList = getFromResource(R.array.cause_not_properly_qualified_request);
                return (String) statusList.getItem(mSpinnerCause.getSelectedItemPosition());
            } else
                return mSpinnerCause.getSelectedItem().toString();
        } else
            return "";
    }

    private String getReturnReason() {
        ArrayAdapter<?> statusList = getFromResource(R.array.cause_revision_request);
        return (String) statusList.getItem(mSpinnerCause.getSelectedItemPosition());
    }

    private boolean checkDate() {
        if (etDate.getText().toString().equals("") || etDate.getText().toString().equals(""))
            return false;
        else
            return true;
    }

    private Map<String, String> setMapHeaderFirst(String willStatus) {
        if (willStatus == null) {
            return null;
        }
        if (taskDetail.getTypeOrder().equals(TYPE_WORK_ASSIGNMENT)) {
            if (willStatus.equals(takeQueue)) { // Взять себе в очередь
                return mapHeader(ITSM_URL_TAKE_QUEUE, ITSM_FILE_TAKE_QUEUE_LITE, ITSM_FILE_TAKE_LITE_RESP_QUEUE, ITSM_CONTENT_TYPE_ENCODED);
            }
            if (willStatus.equals(takeWork)) { // Взять в работу
                return mapHeader(ITSM_URL_TAKE_IN_WORK, ITSM_FILE_TAKE_IN_WORK_LITE, ITSM_FILE_TAKE_IN_WORK, ITSM_CONTENT_TYPE_ENCODED);
            }
            if (willStatus.equals(rejectIncorrectly)) { // Отклонить как не правильно классифицирован
                return mapHeader(ITSM_URL_REJECT_INCORRECTLY + taskDetail.getIdOrder(), null, ITSM_FILE_REJECT_INCORRECTLY, null);
            }
            if (willStatus.equals(rejectReturn)) {  //отклонить - вернуть на доработку
                return mapHeader(ITSM_URL_REJECT_INCORRECTLY + taskDetail.getIdOrder(), null, "WoCheck", null);
            }
            if (willStatus.equals(reject)) { //Отклонить
                return mapHeader(ITSM_URL_TAKE_LITE, ITSM_FILE_REJECT_LITE, ITSM_FILE_TAKE_LITE_RESP_REJECT, ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(wait)) { //Отложить
                return mapHeader(ITSM_URL_TAKE_LITE, ITSM_FILE_WAIT, ITSM_FILE_WAIT_RESP, ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(done)) { //выполнено
                return mapHeader(ITSM_URL_TAKE_LITE, ITSM_FILE_REJECT_LITE, ITSM_FILE_TAKE_LITE_RESP_REJECT, ITSM_CONTENT_TYPE_SOAP);
            }

        } else if (taskDetail.getTypeOrder().equals(TYPE_INCIDENT)) {

            if (willStatus.equals(takeWork)) { // Взять в работу
                return mapHeader("meaweb/wf/INCINPROG", "IncInProg", "IncInProgResp", ITSM_CONTENT_TYPE_ENCODED);
            }
            if (willStatus.equals(rejectIncorrectly)) { // Отклонить как не правильно классифицирован
                return mapHeader("meaweb/services/MOBINCLITE", "IncWorklog", "IncWorklogResp", ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(done)) { //выполнено
                return mapHeader("meaweb/services/MOBINCLITE", "IncWorklogResolve", "IncWorklogResp", ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(rejectReturn)) {  //отклонить - вернуть на доработку
                return mapHeader("meaweb/services/MOBINCLITE", "IncWrongSR", "IncWorklogResp", ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(reject)) { //Отклонить
                return mapHeader("meaweb/services/MOBINCLITE", "IncWorklog", "IncWorklogRes", ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(wait)) { //Отложить
                return mapHeader("meaweb/services/MOBINCLITE", "IncSetDelay", "IncWorklogResp", ITSM_CONTENT_TYPE_SOAP);
            }
        }

        if (willStatus.equals(inWork)) { //в работу
            return mapHeader(ITSM_URL_TAKE_IN_WORK, ITSM_FILE_TAKE_IN_WORK_LITE, ITSM_FILE_TAKE_IN_WORK, ITSM_CONTENT_TYPE_ENCODED);
        }

        return null;
    }

    @NonNull
    private Map<String, String> setMapHeaderSecond(String willStatus) {
        if (taskDetail.getTypeOrder().equals("Рабочее задание")) {
            if (willStatus.equals(takeQueue)) { // Взять себе в очередь
                return mapHeader(ITSM_URL_TAKE_LITE, ITSM_FILE_TAKE_IN_WORK_OWNER, ITSM_FILE_TAKE_IN_WORK_OWNER_RESP, ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(takeWork)) { // Взять в работу
                return mapHeader(ITSM_URL_TAKE_LITE, ITSM_FILE_TAKE_IN_WORK_OWNER, ITSM_FILE_TAKE_IN_WORK_OWNER_RESP, ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(rejectIncorrectly)) {
                return mapHeader("meaweb/services/MOBWOLITE", "WOWorklog", "WOWorklogResp", ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(rejectReturn)) {
                return mapHeader(ITSM_URL_TAKE_LITE, "WOWorklog", "WOWorklogResp", ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(reject)) { //Отклонить
                return mapHeader(ITSM_URL_REJECT, ITSM_FILE_REJECT, ITSM_FILE_REJECT_RESP, ITSM_CONTENT_TYPE_ENCODED);
            }
            if (willStatus.equals(wait)) {  //Отложить
                return mapHeader(ITSM_URL_TAKE_LITE, ITSM_FILE_REJECT_LITE, ITSM_FILE_RESP_LITE, ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(done)) { //Выполнено
                return mapHeader(ITSM_URL_DONE, ITSM_FILE_DONE, ITSM_FILE_DONE_RESP, ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(inWork)) { //в работу
                return mapHeader(ITSM_URL_TAKE_LITE, ITSM_FILE_TAKE_IN_WORK_OWNER, ITSM_FILE_TAKE_IN_WORK_OWNER_RESP, ITSM_CONTENT_TYPE_SOAP);
            }
        } else if (taskDetail.getTypeOrder().equals(TYPE_INCIDENT)) {

            if (willStatus.equals(takeWork)) { // Взять в работу
                return mapHeader("meaweb/services/MOBINCLITE", "IncSetOwner", "IncSetOwnerResp", ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(rejectIncorrectly)) {
                return mapHeader("meaweb/wf/MOBINCREJ", "IncRejClas", "IncRejClassResp", ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(done)) { //Выполнено
                return mapHeader("meaweb/wf/MOBINCRESO", "IncResolve", "IncResolveResp", ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(rejectReturn)) {  //отклонить - вернуть на доработку
                return mapHeader("meaweb/wf/INC_REJECT", "IncRejSR", "WoRejSRResp", ITSM_CONTENT_TYPE_SOAP);
            }
            if (willStatus.equals(reject)) { //Отклонить
                return mapHeader(ITSM_URL_REJECT, ITSM_FILE_REJECT, ITSM_FILE_REJECT_RESP, ITSM_CONTENT_TYPE_ENCODED);
            }

            if (willStatus.equals(wait)) {  //Отложить
                return mapHeader("/meaweb/wf/INC_SLAHOL", "IncHold", "IncHoldResp", ITSM_CONTENT_TYPE_SOAP);
            }
        }
        return mapHeader(null, null, null, null);
    }

    @NonNull
    private Map<String, String> setMapHeaderThird(String willStatus) {
        if (taskDetail.getTypeOrder().equals(TYPE_WORK_ASSIGNMENT)) {
            if (willStatus.equals(rejectIncorrectly)) {
                return mapHeader("meaweb/wf/MOB_WOREJC", "WoRejClass", "WoRejClassResp", ITSM_CONTENT_TYPE_SOAP);
            }

            if (willStatus.equals(rejectReturn)) {
                return mapHeader("meaweb/wf/MOBWOREJSR", "WoRejSR", "WoRejSRResp", ITSM_CONTENT_TYPE_SOAP);
            }

            if (willStatus.equals(wait)) {
                return mapHeader("meaweb/wf/WO_WAIT", "WOWait", "WOWaitResp", ITSM_CONTENT_TYPE_SOAP);
            }
        }
        if (taskDetail.getTypeOrder().equals(TYPE_INCIDENT)) {
            if (willStatus.equals(done)) {
                return mapHeader("meaweb/services/MOBINCLITE", "IncSetOwner", "IncSetOwnerResp", ITSM_CONTENT_TYPE_SOAP);
            }
        }


        return mapHeader(null, null, null, null);
    }

    private void sendChangeOrderFirst(Map<String, String> header, String body) {
        waitDialog.show();

        RestManager.getApi()
                .sendItOrder(header, body)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            ItResponse itResponse = new Gson().fromJson(response.body(), ItResponse.class);

                            if (itResponse.getRoot() != null) {
                                if (Utils.isNetworkAvailable(mContext)) {
                                    sendChangeOrderSecond(setMapHeaderSecond(willStatus), body);
                                } else {
                                    waitDialog.dismiss();
                                    Toast.makeText(mContext, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                waitDialog.dismiss();
                            }
                        } else {
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

    private void sendChangeOrderFirstGet(Map<String, String> header, String body) {
        waitDialog.show();
        RestManager.getApi()
                .sendItOrderGet(header)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            ItResponse itResponse = new Gson().fromJson(response.body(), ItResponse.class);
                            if (itResponse.getRoot() != null) {
                                if (Utils.isNetworkAvailable(mContext)) {
                                    sendChangeOrderSecond(setMapHeaderSecond(willStatus), body);
                                } else {
                                    waitDialog.dismiss();
                                    Toast.makeText(mContext, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                waitDialog.dismiss();
                            }
                        } else {
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

    private void sendChangeOrderSecond(Map<String, String> header, String body) {

        RestManager.getApi()
                .sendItOrder(header, body)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        waitDialog.dismiss();
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
//                            ItResponse itResponse = new Gson().fromJson(response.body(), ItResponse.class);
//                            if (itResponse.getRoot() != null) {
//                                if (itResponse.getRoot().isResult()) {
                            if (taskDetail.getTypeOrder().equals(TYPE_WORK_ASSIGNMENT)) {
                                if (willStatus.equals(rejectIncorrectly) || willStatus.equals(rejectReturn) || willStatus.equals(wait)) {
                                    sendChangeOrderThird(setMapHeaderThird(willStatus), body);
                                } else {
                                    fragmentBackStack();
                                    waitDialog.dismiss();
                                }
                            } else if (taskDetail.getTypeOrder().equals(TYPE_INCIDENT)) {
                                if (willStatus.equals(done)) {
                                    sendChangeOrderThird(setMapHeaderThird(willStatus), body);
                                } else {
                                    fragmentBackStack();
                                    waitDialog.dismiss();
                                }
                            }
//                                } else {
//                                    new MyDialog(itResponse.getRoot().getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
//                                }
//                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                    }
                });
    }

    private void sendChangeOrderThird(Map<String, String> header, String body) {

        RestManager.getApi()
                .sendItOrder(header, body)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        waitDialog.dismiss();
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
//                            ItResponse itResponse = new Gson().fromJson(response.body(), ItResponse.class);
//                            if (itResponse.getRoot() != null) {
//                                if (itResponse.getRoot().isResult()) {
                            fragmentBackStack();
//                                } else {
//                                    new MyDialog(itResponse.getRoot().getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
//                                }
//                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                    }
                });
    }

    private void fragmentBackStack() {
        Objects.requireNonNull(getFragmentManager()).popBackStackImmediate(
                getFragmentManager().getBackStackEntryCount() - 2, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

//    private void setRejectOrder() {
//        waitDialog.show();
//        RestManager.getApi()
//                .sendItOrder(header, body)
//                .enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
//                        waitDialog.dismiss();
//                        restError(response);
//                        if (response.isSuccessful() && response.body() != null) {
//                            ItResponse itResponse = new Gson().fromJson(response.body(), ItResponse.class);
//
//                            if (itResponse.getRoot() != null) {
//                                if (itResponse.getRoot().isResult()) {
//                                    Objects.requireNonNull(getFragmentManager()).popBackStackImmediate(
//                                            getFragmentManager().getBackStackEntryCount() - 2, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                                } else {
//                                    new MyDialog(itResponse.getRoot().getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                        waitDialog.dismiss();
//                        new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
//                    }
//                });
//    }
}
