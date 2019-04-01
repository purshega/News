package com.dtek.portal.ui.fragment.itsm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.itsm.ItAddress;
import com.dtek.portal.models.itsm.ItAddressInfo;
import com.dtek.portal.models.itsm.ItDefaultInfo;
import com.dtek.portal.models.itsm.ItOrder;
import com.dtek.portal.models.qr.QrResp;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.it.ItAddressAdapter;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.ui.dialog.WaitDialog;;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.ITSM_CONTENT_TYPE_SOAP;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_ADDRESS;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_CREATE;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_CREATE_RESP;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_DEFAULT_INFO;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_CONTENT_TYPE;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_REQUEST;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_RESPONSE;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_URL;
import static com.dtek.portal.Const.HTTP.ITSM_URL_CREATE;
import static com.dtek.portal.Const.HTTP.ITSM_URL_USER;
import static com.dtek.portal.Const.HTTP.ITSM_URL_WORD;
import static com.dtek.portal.ui.fragment.qr.QrScanFragment.ARG_QR_DATA;


public class CreateItsmFragment extends Fragment {
    private static final String TAG = "CreateItsmFragment";
    private static final int REQUEST_CODE_LOGIN = 100;

    private Context mContext;
    //    private ProgressDialog waitDialog;
    protected Dialog waitDialog;
    private boolean firstLoadAddress = true;
    protected boolean dropDaownAdressList = true;

    @BindView(R.id.actv_person)
    protected AutoCompleteTextView actvPerson;

    @BindView(R.id.actv_address)
    protected AutoCompleteTextView mActvAddress;
    @BindView(R.id.iv_clear_address)
    ImageView mIvClearAddress;
    @BindView(R.id.pb_address)
    ProgressBar mPbAddress;
    @BindView(R.id.et_room)
    protected EditText mEtRoom;
    @BindView(R.id.spinner_urgency)
    Spinner mSpinnerUrgency;
    @BindView(R.id.et_subject)
    protected EditText mEtSubject;
    @BindView(R.id.t_subject)
    protected TextView mtSubject;
    @BindView(R.id.et_description)
    protected EditText mEtDescription;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;


    protected ItDefaultInfo mItDefaultInfo;
    protected List<ItAddressInfo> addressList = new ArrayList<>(); // список полученный с сервера
    private List<ItAddressInfo> filterList = new ArrayList<>(); // список фильтр
    protected String addressCode;
    private QrResp qrResp;
//    private String task;

    //    public static CreateItsmFragment newInstance(QrResp qrResp, String task) {
    public static CreateItsmFragment newInstance(QrResp qrResp) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_QR_DATA, qrResp);
//        args.putString(ARG_QR_TASK, task);

        CreateItsmFragment fragment = new CreateItsmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CreateItsmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        if (getArguments() != null) {
            qrResp = getArguments().getParcelable(ARG_QR_DATA);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_it_service_create, container, false);
        ButterKnife.bind(this, v);

        initDialog(); // диалоги
        initInfoOrder(); // инфо о заявке
        if (qrResp != null) {
            fillQrInfoUser(qrResp);
            initChoiceDialog(qrResp);
        }
        initInfoUser(); // инфо о пользователе
        return v;
    }

    private void fillQrInfoUser(QrResp qrResp) {
//        mEtRoom.setText(qrResp.getLocation());
        mEtSubject.setText(qrResp.getTitle());

//        mEtDescription.setText(task);

//        addressCode = qrResp.getAddressCode();
//        mActvAddress.setText(qrResp.getLocation());
//        mEtRoom.setText(qrResp.ge);
    }

    protected void initInfoUser() {
        if (Utils.isNetworkAvailable(mContext)) {
            loadDefaultInfo();
        } else {
            Toast.makeText(mContext, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
        }

        mActvAddress.setOnItemClickListener((parent, view, position, id) -> {
//                ItAddressInfo address = addressList.get(position);
            ItAddressInfo address = filterList.get(0);
            addressCode = address.getAddresscode();
            mActvAddress.setText(String.format("%s, %s", address.getCity(), address.getStreet()));
        });

    }

    @OnTextChanged(R.id.actv_address)
    public void onTextChanged(CharSequence s) {
        if (dropDaownAdressList) {
            if (firstLoadAddress) {
                firstLoadAddress = false;
            } else {
                String strEntered = s.toString(); // ввод часть слова

                if (strEntered.length() > 2) { //количество введенных символов
                    if (addressList.isEmpty()) { // если массив фильтра пустой
                        if (Utils.isNetworkAvailable(mContext)) {
                            loadListAddress(strEntered); // загружаем данные и присваиваем их массиву addressList по введенному слову
                        } else {
                            Toast.makeText(mContext, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    addressCode = ""; //обнуляем addressCode
                    addressList.clear(); // очищаем массив полученный из сервера
                }

                filterList.clear(); // очищаем массив фильтра

                for (ItAddressInfo address : addressList) { // перебераем массив данных из сервера
                    if (address.getStreet().toLowerCase().contains(strEntered.toLowerCase())) { //проверяем иммя на содержание нашего текста
                        filterList.add(address); // добавляем объект в массив фильта
                    }
                }

                if (!filterList.isEmpty()) { // если массив фильтра не пустой
                    getDropList(filterList); // выпадающий список с масивом фильтра
//        } else {
//                    getDropList(addressList); // выпадающий список с масивом полученным из сервера
                }
            }
        } else {
            dropDaownAdressList = true;
        }


    }

    public void getDropList(List<ItAddressInfo> list) {
        ItAddressAdapter adapter = new ItAddressAdapter(mContext, R.layout.item_address, list);
        mActvAddress.setThreshold(1);//will start working from first characte
        mActvAddress.setAdapter(adapter);//setting the adapter data into the//
        mActvAddress.showDropDown();
    }

    private void loadListAddress(String str) {
        mPbAddress.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        try {
            map.put(ITSM_KEY_URL, ITSM_URL_WORD + URLEncoder.encode(str, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put(ITSM_KEY_RESPONSE, ITSM_FILE_ADDRESS);

        RestManager.getApi()
                .loadItStringJson(map)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            String strJson = response.body();
                            Gson gson = new Gson();
                            ItAddress itAddress = gson.fromJson(strJson, ItAddress.class);
                            addressList = itAddress.getRoot().getItAddressInfo();
                            addressList.remove(addressList.size() - 1);
                            addressList.remove(addressList.size() - 1);
                            getDropList(addressList);
                        }
                        mPbAddress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        mPbAddress.setVisibility(View.GONE);
                    }
                });
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

    private void loadDefaultInfo() {
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        map.put(ITSM_KEY_URL, ITSM_URL_USER + PreferenceUtils.getLogin());
        map.put(ITSM_KEY_RESPONSE, ITSM_FILE_DEFAULT_INFO);

        RestManager.getApi()
                .loadItStringJson(map)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            String strJson = response.body();
                            Gson gson = new Gson();
                            mItDefaultInfo = gson.fromJson(strJson, ItDefaultInfo.class);
//                            if (qrResp == null)
                            fillInfoUser(mItDefaultInfo);
                        }
                        if ((waitDialog != null) && waitDialog.isShowing())
                            waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                    }
                });
    }

    private void fillInfoUser(ItDefaultInfo mItDefaultInfo) {
        actvPerson.setText(mItDefaultInfo.getRoot().getDisplayName());
        mEtRoom.setText(mItDefaultInfo.getRoot().getRoom());
        addressCode = mItDefaultInfo.getRoot().getAddresscode();
        mActvAddress.setText(String.format("%s, %s",
                mItDefaultInfo.getRoot().getCity(), mItDefaultInfo.getRoot().getStreet()));
    }

    private void initInfoOrder() {
// Настраиваем адаптер
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(mContext, R.array.urgency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Вызываем адаптер
        mSpinnerUrgency.setAdapter(adapter);
        mSpinnerUrgency.setSelection(2); // по умолчанию открываем 3й
    }

    @OnClick({R.id.iv_clear_address, R.id.btn_confirm})
    void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear_address:
                mActvAddress.setText("");
                break;
            case R.id.btn_confirm:
                if (Utils.isNetworkAvailable(mContext) && isValidate()) {
                    String strItOrder = fillOrderRequest(); // наполняем модель заявки и переводим gson в строку
                    sendOrder(strItOrder);
                } else {
                    Toast.makeText(mContext, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean isValidate() {
        String error = "";

        if (mActvAddress.getText().toString().length() < 4 || addressCode.length() <= 0) {
            error = String.format("- %s", getString(R.string.text_error_city));
        }
        if (mEtSubject.getText().toString().length() <= 0) {
            error = String.format("%s\n- %s", error, getString(R.string.text_error_subject));
        }
        if (mEtDescription.getText().toString().length() <= 0) {
            error = String.format("%s\n- %s", error, getString(R.string.text_error_description));
        }
        if (error.length() > 0) {
            new MyDialog(error).show(getFragmentManager(), "fragmentDialog");
            return false;
        } else {
            return true;
        }
    }

    protected void sendOrder(String strItOrder) {
        waitDialog.show();
        RestManager.getApi()
                .sendItOrder(fillMapHeader(), strItOrder)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            if (((MainActivity) getActivity()) != null) {
                                getActivity().onBackPressed();
                            }
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

    @NonNull
    protected Map<String, String> fillMapHeader() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        map.put(ITSM_KEY_URL, ITSM_URL_CREATE);
        map.put(ITSM_KEY_REQUEST, ITSM_FILE_CREATE);
        map.put(ITSM_KEY_RESPONSE, ITSM_FILE_CREATE_RESP);
        map.put(ITSM_KEY_CONTENT_TYPE, ITSM_CONTENT_TYPE_SOAP);
        return map;
    }

    private String fillOrderRequest() {
        ItOrder itOrder = new ItOrder();
        ItOrder.Root root = new ItOrder.Root();
        root.setAddress(addressCode);
        root.setRoom(mEtRoom.getText().toString());
        root.setUrgency(String.valueOf(mSpinnerUrgency.getSelectedItem().toString().charAt(0)));
        root.setSubject(mEtSubject.getText().toString());
        root.setDetails(mEtDescription.getText().toString());
        root.setPersonId(mItDefaultInfo.getRoot().getPersonid());
        itOrder.setRoot(root);

        Gson gson = new Gson();
        String strItOrder = gson.toJson(itOrder);

        return strItOrder;
    }

    protected void initDialog() {
//        waitDialog = WaitDialog.showDialog(mContext, getString(R.string.dialog_wait));
        waitDialog = WaitDialog.setDialog(mContext);
//        waitDialog = new WaitDialog();
    }

    @SuppressLint("SetTextI18n")
    private void initChoiceDialog(QrResp qrResp) {
        List<String> mList = qrResp.getInfoList();
        final String[] choices_list = mList.toArray(new String[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> dialog.cancel())
                .setNeutralButton("Свой вариант", (dialog, which) -> {
                    dialog.cancel();
                    mEtDescription.setText(qrResp.getLocation() + "");
                })
                .setSingleChoiceItems(choices_list, -1,
                        (dialog, which) -> mEtDescription.setText(qrResp.getLocation() + "\n" + choices_list[which]));
        builder.show();
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

//    // Реакция на получение данных от диалогового окна
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            switch (requestCode) {
//                case REQUEST_CODE_LOGIN:
//                    loadDefaultInfo();
//                    break;
//            }
//        }
//    }
}
