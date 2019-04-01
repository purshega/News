package com.dtek.portal.ui.fragment.reference;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.itsm.ItAddressInfo;
import com.dtek.portal.models.itsm.ItPerson;
import com.dtek.portal.models.itsm.ItResponse;
import com.dtek.portal.models.reference.Param;
import com.dtek.portal.models.reference.Reference;
import com.dtek.portal.models.reference.ReferenceAttributeForSave;
import com.dtek.portal.models.reference.ReferenceForSave;
import com.dtek.portal.models.reference.ReferenceList;
import com.dtek.portal.models.reference.ReferencePersons;
import com.dtek.portal.models.reference.ReferenceResult;
import com.dtek.portal.ui.adapter.reference.ReferenceAttributeAdapter;
import com.dtek.portal.ui.adapter.reference.ReferenceDateAdapter;
import com.dtek.portal.ui.dialog.DatePickerDialog;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.ui.fragment.itsm.CreateItsmFragment;
import com.dtek.portal.utils.ConvertTime;
import com.dtek.portal.utils.PickerUtils;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.ITSM_CONTENT_TYPE_SOAP;
import static com.dtek.portal.Const.HTTP.ITSM_CREATE_REFERENCE;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_PERSON_SEARCH;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_REFERENCES_TYPE;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_REFERENCE_CREATE_REQUEST;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_REFERENCE_CREATE_REQUEST_2;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_REFERENCE_CREATE_RESPONSE;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_REFERENCE_CREATE_RESPONSE_2;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_CONTENT_TYPE;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_REQUEST;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_RESPONSE;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_URL;
import static com.dtek.portal.Const.HTTP.ITSM_URL_PERSON_SEARCH_1;
import static com.dtek.portal.Const.HTTP.ITSM_URL_PERSON_SEARCH_2;
import static com.dtek.portal.Const.HTTP.ITSM_URL_REFERENCES_TYPE;

public class AddReferenceFragment extends CreateItsmFragment implements ReferenceDateAdapter.DateListener {

    @BindView(R.id.search_person_layout)
    LinearLayout llSearchPerson;
    @BindView(R.id.pb_person)
    ProgressBar pbPerson;

    @BindView(R.id.reference_attribute_layout)
    LinearLayout llReferenceAttribute;
    @BindView(R.id.spinner_reference_type)
    Spinner sReferenceType;
    @BindView(R.id.rv_reference_dates)
    RecyclerView rvReferenceDates;
    @BindView(R.id.rv_reference_attribute)
    RecyclerView rvReferenceAttribute;


    @BindView(R.id.urgency_layout)
    LinearLayout llUrgency;

    private ReferenceDateAdapter referenceDateAdapter;
    private List<Param> paramDateList = new ArrayList<>();
    private List<Param> paramAttributeList = new ArrayList<>();
    private List<ItPerson> personsList = new ArrayList<>();
    private List<ItPerson> filterList = new ArrayList<>();
    private Reference currentReference;
    private ItPerson itPerson;
    private Boolean firstOpen = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_it_service_create, container, false);
        ButterKnife.bind(this, v);

        initDialog();
        initPersonSearch();
        initInfoUser();
        initVisibility();
        initReferenceTypes();
        return v;
    }

    @OnClick({R.id.iv_clear_person, R.id.btn_confirm})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_clear_person:
                actvPerson.setText("");
                break;
            case R.id.btn_confirm:
                if (Utils.isNetworkAvailable(getContext())) {
                    if (dateValidation())
                        checkEmptyField();
                    else
                        Toast.makeText(getContext(), R.string.date_validation_error, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void initPersonSearch() {
        actvPerson.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!firstOpen) {
                    String strEntered = s.toString();
                    if (strEntered.length() > 2) {
                        if (personsList.isEmpty())
                            getPersons(strEntered);
                    } else {
                        personsList.clear();
                        getDropPersonsList(personsList);
                    }

                    filterList.clear();
                    for (ItPerson person : personsList) {
                        if (person.getDisplayName().toLowerCase().contains(strEntered.toLowerCase())) { //проверяем иммя на содержание нашего текста
                            filterList.add(person); // добавляем объект в массив фильта
                        }
                    }

                    if (filterList.isEmpty())  // если массив фильтра пустой
                        getDropPersonsList(personsList); // выпадающий список с масивом полученным из сервера
                    else
                        getDropPersonsList(filterList); // выпадающий список с масивом фильтра
                } else
                    firstOpen = false;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        actvPerson.setOnItemClickListener((adapterView, view, i, l) -> {
            itPerson = filterList.get(0);
            addressCode = filterList.get(0).getAddresscode();
            mEtRoom.setText(filterList.get(0).getRoom());
            mActvAddress.setText(filterList.get(0).getCity() + ", " + filterList.get(0).getStreet());
            addressList.add(new ItAddressInfo(filterList.get(0).getAddresscode(), filterList.get(0).getStreet(), filterList.get(0).getCity()));
        });
    }

    private void setReferenceType(ReferenceList referenceList) {
        ArrayAdapter<Reference> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, referenceList.getRoot().getReferenceList());
        adapter.setDropDownViewResource(R.layout.item_spinner_multi);
        sReferenceType.setAdapter(adapter);
        sReferenceType.setSelection(0);
        currentReference = referenceList.getRoot().getReferenceList().get(0);
        initSpinnerReferenceTypeListener(referenceList);
    }

    private void initSpinnerReferenceTypeListener(ReferenceList tripTypesList) {
        sReferenceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                createReferenceParam(tripTypesList.getRoot().getReferenceList().get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void createReferenceParam(Reference reference) {
        currentReference = reference;
        setParamLists(reference);

        referenceDateAdapter = new ReferenceDateAdapter();
        ReferenceAttributeAdapter referenceAttributeAdapter = new ReferenceAttributeAdapter();

        referenceDateAdapter.setDateListener(this); // слушатель интерфейса
        rvReferenceDates.setLayoutManager(new LinearLayoutManager(getContext()));
        rvReferenceDates.setAdapter(referenceDateAdapter);
        referenceDateAdapter.setItems(paramDateList);

        rvReferenceAttribute.setLayoutManager(new LinearLayoutManager(getContext()));
        rvReferenceAttribute.setAdapter(referenceAttributeAdapter);
        referenceAttributeAdapter.setItems(paramAttributeList);
    }

    @Override
    public void onItemClick(Param currentParam, int index) {
        if (currentParam.getParamId().equals(Const.References.REFERENCE_DESIRE_DATE_OF_RECEIPT)) {
            Calendar calMinDate = Calendar.getInstance();
//            Calendar calMaxDate = Calendar.getInstance();
            calMinDate.add(Calendar.DATE, 2);
//            calMaxDate.add(Calendar.DATE, 11);
            PickerUtils.initDatePicker(AddReferenceFragment.this,
                    calMinDate.getTime(), calMinDate.getTime(), null, index);
        } else
            PickerUtils.initDatePicker(AddReferenceFragment.this,
                    new Date(), null, null, index);
    }

    private void setParamLists(Reference reference) {
        paramDateList.clear();
        paramAttributeList.clear();
        for (int i = 0; i < reference.getParamList().size() - 2; i++) {
            Param param = reference.getParamList().get(i);
            if (reference.getParamList().get(i).getParamName().toLowerCase().contains("дата") || reference.getParamList().get(i).getParamName().toLowerCase().contains("период заказа")) {
                if (param.getParamId().equals(Const.References.REFERENCE_DESIRE_DATE_OF_RECEIPT)) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, 10);
                    param.setParamValue(ConvertTime.dateToString(cal.getTime()));
                }
                paramDateList.add(param);
            } else
                paramAttributeList.add(param);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Date date = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
            Param param = paramDateList.get(requestCode);
            param.setParamValue(ConvertTime.dateToString(date));
            referenceDateAdapter.setChangeItems(param, requestCode);
        }
    }

    @Override
    protected void sendOrder(String str) {
    }

    private void getPersons(String str) {
        pbPerson.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        try {
            map.put(ITSM_KEY_URL, ITSM_URL_PERSON_SEARCH_1 + URLEncoder.encode(str, "UTF-8") + ITSM_URL_PERSON_SEARCH_2);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put(ITSM_KEY_RESPONSE, ITSM_FILE_PERSON_SEARCH);

        RestManager.getApi()
                .getPersons(map)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            String strJson = response.body();
                            Gson gson = new Gson();
                            ReferencePersons referencePersons = gson.fromJson(strJson, ReferencePersons.class);
                            personsList = referencePersons.getPersons().getPersonsList();
                            personsList.remove(personsList.size() - 1);
                            personsList.remove(personsList.size() - 1);
                            getDropPersonsList(personsList);
                            dropDaownAdressList = false;
                        }
                        pbPerson.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        pbPerson.setVisibility(View.GONE);
                        new MyDialog(t.getMessage()).show(getFragmentManager(), "fragmentDialog");
                    }
                });
    }


    private void getDropPersonsList(List<ItPerson> itPersonList) {
        List<String> list = new ArrayList<>();
        for (ItPerson itPerson : itPersonList) {
            list.add(itPerson.getDisplayName());
        }
        actvPerson.setAdapter(new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.item_text, R.id.autoCompleteItem, list));
        actvPerson.showDropDown();
    }

    private void initReferenceTypes() {
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        map.put(ITSM_KEY_URL, ITSM_URL_REFERENCES_TYPE);
        map.put(ITSM_KEY_RESPONSE, ITSM_FILE_REFERENCES_TYPE);

        RestManager.getApi()
                .getReferencesType(map)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            String strJson = response.body();
                            Gson gson = new Gson();
                            ReferenceList referenceList = gson.fromJson(strJson, ReferenceList.class);
                            setReferenceType(referenceList);
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

    private void createReference(String reference) {
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        map.put(ITSM_KEY_URL, ITSM_CREATE_REFERENCE);
        map.put(ITSM_KEY_REQUEST, ITSM_FILE_REFERENCE_CREATE_REQUEST);
        map.put(ITSM_KEY_RESPONSE, ITSM_FILE_REFERENCE_CREATE_RESPONSE);
        map.put(ITSM_KEY_CONTENT_TYPE, ITSM_CONTENT_TYPE_SOAP);

        RestManager.getApi()
                .createReference(map, reference)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            String strJson = response.body();
                            Gson gson = new Gson();
                            ReferenceResult referenceResult = gson.fromJson(strJson, ReferenceResult.class);
                            setReferenceAttribute(referenceResult);
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

    private void setReferenceAttribute(ReferenceResult referenceResult) {
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        map.put(ITSM_KEY_URL, ITSM_CREATE_REFERENCE);
        map.put(ITSM_KEY_REQUEST, ITSM_FILE_REFERENCE_CREATE_REQUEST_2);
        map.put(ITSM_KEY_RESPONSE, ITSM_FILE_REFERENCE_CREATE_RESPONSE_2);
        map.put(ITSM_KEY_CONTENT_TYPE, ITSM_CONTENT_TYPE_SOAP);

        RestManager.getApiSimpleFormatDate()
                .createReference(map, fillReferenceRequest2(referenceResult))
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
                        new MyDialog(t.getMessage()).show(getFragmentManager(), "fragmentDialog");
                    }
                });
    }


    private String fillReferenceRequest() {
        ReferenceForSave referenceForSave = new ReferenceForSave();
        ReferenceForSave.Root root = new ReferenceForSave.Root();

        root.setAddress(addressCode);
        root.setRoom(mEtRoom.getText().toString());
        root.setUrgency("3");
        root.setSubject(currentReference.getName());
        root.setDetails(mEtDescription.getText().toString());
        root.setPersonId(mItDefaultInfo.getRoot().getPersonid());
        root.setClassNumber(String.valueOf(currentReference.getId()));
        if (itPerson != null)
            root.setAffectedid(itPerson.getPersonid());
        else
            root.setAffectedid(mItDefaultInfo.getRoot().getPersonid());
        referenceForSave.setRoot(root);

        Gson gson = new Gson();
        String strReferenceForSave = gson.toJson(referenceForSave);

        return strReferenceForSave;
    }

    private String fillReferenceRequest2(ReferenceResult referenceResult) {
        ReferenceAttributeForSave referenceAttributeForSave = new ReferenceAttributeForSave();
        ReferenceAttributeForSave.Root root = new ReferenceAttributeForSave.Root();

        root.setOrder_id(referenceResult.getRoot().getOrder_id());
        List<Param> params = new ArrayList<>();
        params.addAll(paramDateList);
        params.addAll(paramAttributeList);
        root.setParams(params);
        referenceAttributeForSave.setRoot(root);

        Gson gson = new Gson();
        String strReferenceAttributeForSave = gson.toJson(referenceAttributeForSave);

        return strReferenceAttributeForSave;
    }

    private void initVisibility() {
        llSearchPerson.setVisibility(View.VISIBLE);
        llUrgency.setVisibility(View.GONE);
        llReferenceAttribute.setVisibility(View.VISIBLE);
        mEtSubject.setVisibility(View.GONE);
        mtSubject.setVisibility(View.GONE);
    }

    private Boolean dateValidation() {
        Date periodFrom = null;
        Date periodTo = null;
        for (Param param : paramDateList) {
            if (param.getParamValue() != null) {
                if (param.getParamName().toLowerCase().contains("период заказа с"))
                    periodFrom = ConvertTime.stringToDate(param.getParamValue());
                else if (param.getParamName().toLowerCase().contains("период заказа по"))
                    periodTo = ConvertTime.stringToDate(param.getParamValue());
            }
        }

        if (null == periodFrom || null == periodTo)
            return true;
        else
            return periodFrom.before(periodTo);
    }

    private void checkEmptyField() {
        boolean check = true;
        if (mEtDescription.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.description_empty_error, Toast.LENGTH_LONG).show();
            check = false;
        }
        for (Param param : currentReference.getParamList()) {
            if (param.getParamName().toLowerCase().contains("место требования")) {
                if (param.getParamValue() != null) {
                    if (param.getParamValue().equals("")) {
                        Toast.makeText(getContext(), R.string.space_requirement_empty_error, Toast.LENGTH_LONG).show();
                        check = false;
                    }
                } else {
                    Toast.makeText(getContext(), R.string.space_requirement_empty_error, Toast.LENGTH_LONG).show();
                    check = false;
                }
            }
        }
        if (check)
            createReference(fillReferenceRequest());
    }
}