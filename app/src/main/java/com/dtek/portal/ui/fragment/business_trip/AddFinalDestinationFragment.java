package com.dtek.portal.ui.fragment.business_trip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.business_trips.BT;
import com.dtek.portal.models.business_trips.BTDestination;
import com.dtek.portal.models.business_trips.BTLocation;
import com.dtek.portal.models.business_trips.BTOrganization;
import com.dtek.portal.models.business_trips.BTTripAim;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.businees_trips.BTLocationsAdapter;
import com.dtek.portal.ui.dialog.DatePickerDialog;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.utils.BusinessTrip;
import com.dtek.portal.utils.ConvertTime;
import com.dtek.portal.utils.PickerUtils;
import com.dtek.portal.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.dtek.portal.Const.BusinessTrips.API_BT;
import static com.dtek.portal.Const.BusinessTrips.BT_GET_COMPENSATION_NUMBERS;
import static com.dtek.portal.Const.BusinessTrips.BT_GET_LOCATIONS;
import static com.dtek.portal.Const.BusinessTrips.BT_GET_ORGANIZATION;
import static com.dtek.portal.Const.BusinessTrips.BT_GET_TRIP_AIM;
import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.utils.ConvertTime.stringToDate;

public class AddFinalDestinationFragment extends Fragment {

    private BT businessTrip;
    private BTDestination destination;
    private Boolean newDestination = true;

    private static final int REQUEST_DATE_START = 1;
    private static final int REQUEST_DATE_END = 2;
    private static final int REQUEST_DATE_COMPENSATION_OF = 3;
    private static final int REQUEST_DATE_COMPENSATION_TO = 4;

    private Unbinder unbinder;
    private Context mContext;

    private List<BTLocation> locationsList = new ArrayList<>();
    private List<BTLocation> filterLocationsList = new ArrayList<>();

    private List<BTOrganization> organizationsList = new ArrayList<>();
    private List<BTOrganization> filterOrganizationsList = new ArrayList<>();

    private List<BTTripAim> tripAimList = new ArrayList<>();

    private Dialog waitDialog;
    private boolean dropList = true;

    @BindView(R.id.et_date_start)
    EditText etDateStart;
    @BindView(R.id.et_date_end)
    EditText etDateEnd;
    @BindView(R.id.cb_name_is_absent)
    CheckBox cbNameIsAbsent;
    @BindView(R.id.absent_company_name_layout)
    LinearLayout absentCompanyNameLayout;
    @BindView(R.id.et_absent_company_name)
    EditText etAbsentCompanyName;
    @BindView(R.id.cb_compensation_layout)
    LinearLayout cbCompensationLayout;
    @BindView(R.id.cb_compensation)
    CheckBox cbCompensation;
    @BindView(R.id.compensation_layout)
    LinearLayout compensationLayout;
    @BindView(R.id.et_compensation_start_date)
    EditText etCompensationStartDate;
    @BindView(R.id.et_compensation_end_date)
    EditText etCompensationEndDate;

    @BindView(R.id.ac_tv_final_destination)
    AutoCompleteTextView acTvFinalDestination;
    @BindView(R.id.iv_destinations_list)
    ImageView ivDestinationsList;
    @BindView(R.id.pb_destinations_search)
    ProgressBar pbDestinationsSearch;

    @BindView(R.id.ac_tv_organizations)
    AutoCompleteTextView acTvOrganizations;
    @BindView(R.id.iv_organizations_list)
    ImageView ivOrganizationsList;
    @BindView(R.id.pb_organizations_search)
    ProgressBar pbOrganizationsSearch;

    @BindView(R.id.business_trip_goal)
    Spinner sBusinessTripGoal;
    @BindView(R.id.btn_delete)
    Button btnDelete;

    public AddFinalDestinationFragment() {
    }

    @SuppressLint("ValidFragment")
    public AddFinalDestinationFragment(BTDestination destination) {
        this.destination = destination;
        newDestination = false;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_final_destination_add, container, false);
        unbinder = ButterKnife.bind(this, v);
        mContext = v.getContext();
        businessTrip = BusinessTrip.getInstance().getBusinessTrip();

        initDialog();
        initDestination();
        initDeleteButton();
        initListeners();
        initSpinner();
        return v;
    }

    private void initDestination() {
        if (destination != null)
            loadDestinationData();
        else {
            destination = new BTDestination();
            initDates();
        }
    }

    private void loadDestinationData() {
        btnDelete.setVisibility(View.VISIBLE);
        acTvFinalDestination.setText(destination.getLocation().getCity().getName());
        etDateStart.setText(ConvertTime.dateToString(destination.getStartDate()));
        etDateEnd.setText(ConvertTime.dateToString(destination.getEndDate()));
        if (destination.getOrganization().getId().equals("-1")) {
            cbNameIsAbsent.setChecked(true);
            absentCompanyNameLayout.setVisibility(View.VISIBLE);
            etAbsentCompanyName.setText(destination.getOrganization().getName());
        } else {
            acTvOrganizations.setText(destination.getOrganization().getName());
        }

        if (destination.getCompensationNeeded()) {
            cbCompensationLayout.setVisibility(View.VISIBLE);
            cbCompensation.setChecked(true);
            compensationLayout.setVisibility(View.VISIBLE);
            if (destination.getCompensationStartDate() != null && destination.getCompensationEndDate() != null) {
                etCompensationStartDate.setText(ConvertTime.dateToString(destination.getCompensationStartDate()));
                etCompensationEndDate.setText(ConvertTime.dateToString(destination.getCompensationEndDate()));
            }
        } else {
            initCompensations();
        }
    }

    private void initDates() {
        destination.setStartDate(businessTrip.getStartDate());
        destination.setEndDate(businessTrip.getEndDate());
        etDateStart.setText(ConvertTime.dateToString(destination.getStartDate()));
        etDateEnd.setText(ConvertTime.dateToString(destination.getEndDate()));
    }

    @OnClick({R.id.cb_name_is_absent, R.id.cb_compensation, R.id.btn_save, R.id.btn_delete})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_name_is_absent:
                if (cbNameIsAbsent.isChecked()) {
                    absentCompanyNameLayout.setVisibility(View.VISIBLE);
                    acTvOrganizations.setText("");
                    destination.setOrganization(null);
                } else
                    absentCompanyNameLayout.setVisibility(View.GONE);
                break;
            case R.id.cb_compensation:
                if (cbCompensation.isChecked()) {
                    compensationLayout.setVisibility(View.VISIBLE);
                    destination.setCompensationNeeded(true);
                } else {
                    destination.setCompensationNeeded(false);
                    compensationLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_save:
                saveDestination();
                break;
            case R.id.btn_delete:
                deleteDestination();
                break;
        }
    }

    @OnClick({R.id.et_date_start, R.id.et_date_end, R.id.et_compensation_start_date, R.id.et_compensation_end_date})
    void onDateClick(View view) {
        switch (view.getId()) {
            case R.id.et_date_start:
                PickerUtils.initDatePicker(AddFinalDestinationFragment.this,
                        businessTrip.getStartDate(), businessTrip.getStartDate(), businessTrip.getEndDate(), REQUEST_DATE_START);
                break;
            case R.id.et_date_end:
                PickerUtils.initDatePicker(AddFinalDestinationFragment.this,
                        businessTrip.getEndDate(), businessTrip.getStartDate(), businessTrip.getEndDate(), REQUEST_DATE_END);
                break;
            case R.id.et_compensation_start_date:
                PickerUtils.initDatePicker(AddFinalDestinationFragment.this,
                        stringToDate(etCompensationStartDate.getText().toString()), businessTrip.getStartDate(), businessTrip.getEndDate(), REQUEST_DATE_COMPENSATION_OF);
                break;
            case R.id.et_compensation_end_date:
                PickerUtils.initDatePicker(AddFinalDestinationFragment.this,
                        stringToDate(etCompensationEndDate.getText().toString()), businessTrip.getStartDate(), businessTrip.getEndDate(), REQUEST_DATE_COMPENSATION_TO);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_DATE_START:
                    Date date = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    etDateStart.setText(ConvertTime.dateToString(date));
//                    etDateEnd.setText(ConvertTime.dateToString(date));
                    destination.setStartDate(date);
//                    destination.setEndDate(date);
                    break;
                case REQUEST_DATE_END:
                    Date dateEnd = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    etDateEnd.setText(ConvertTime.dateToString(dateEnd));
                    destination.setEndDate(dateEnd);
                    break;
                case REQUEST_DATE_COMPENSATION_OF:
                    Date dateCompensationOf = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    etCompensationStartDate.setText(ConvertTime.dateToString(dateCompensationOf));
                    destination.setCompensationStartDate(dateCompensationOf);
                    break;
                case REQUEST_DATE_COMPENSATION_TO:
                    Date dateCompensationTo = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    etCompensationEndDate.setText(ConvertTime.dateToString(dateCompensationTo));
                    destination.setCompensationEndDate(dateCompensationTo);
                    break;
            }
        }
    }

    private void initListeners() {
        acTvFinalDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strEntered = s.toString();
                if (strEntered.length() > 2) {
                    resetCompensation();
//                    if (locationsList.isEmpty()) {
                    if (Utils.isNetworkAvailable(mContext))
                        getLocations(strEntered);
                    else
                        Toast.makeText(mContext, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    locationsList.clear();
                    getDropLocationsList(locationsList);
                }
//
//                filterLocationsList.clear();
//                for (BTLocation bsLocation : locationsList) {
//                    if (bsLocation.getCity().getName().toLowerCase().contains(strEntered.toLowerCase())) { //проверяем иммя на содержание нашего текста
//                        filterLocationsList.add(bsLocation); // добавляем объект в массив фильта
//                    }
//                }
//
//                if (filterLocationsList.isEmpty())  // если массив фильтра пустой
//                    getDropLocationsList(locationsList); // выпадающий список с масивом полученным из сервера
//                else
//                    getDropLocationsList(filterLocationsList); // выпадающий список с масивом фильтра
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        acTvFinalDestination.setOnItemClickListener((adapterView, view, i, l) -> {
            destination.setLocation(locationsList.get(0));
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(acTvFinalDestination.getWindowToken(), 0);
            if (locationsList.get(0).getCountry().getId().equals("UA") && businessTrip.getEmployee().getCompanyId() != null) {
                initCompensations();
            }
            dropList = false;
        });

        acTvOrganizations.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strEntered = s.toString();
                if (strEntered.length() > 2) {
                    if (organizationsList.isEmpty())
                        getOrganizations(strEntered);
                } else {
                    organizationsList.clear();
                    getDropOrganizationsList(organizationsList);
                }

                filterOrganizationsList.clear();
                for (BTOrganization bsOrganization : organizationsList) {
                    if (bsOrganization.getName().toLowerCase().contains(strEntered.toLowerCase())) { //проверяем иммя на содержание нашего текста
                        filterOrganizationsList.add(bsOrganization); // добавляем объект в массив фильта
                    }
                }

                if (filterOrganizationsList.isEmpty())  // если массив фильтра пустой
                    getDropOrganizationsList(organizationsList); // выпадающий список с масивом полученным из сервера
                else
                    getDropOrganizationsList(filterOrganizationsList); // выпадающий список с масивом фильтра
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        acTvOrganizations.setOnItemClickListener((adapterView, view, i, l) -> {
            destination.setOrganization(filterOrganizationsList.get(0)); // индекс 0, потому что при выборе он становится 1 в списке
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(acTvOrganizations.getWindowToken(), 0);
        });
    }

    private void getLocations(String name) {
        showSearchLocationsProgress(true);
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, Const.BusinessTrips.TEST_TOKEN);

        RestManager.getApi()
                .getBSLocations(map, API_BT + BT_GET_LOCATIONS + name)
                .enqueue(new Callback<List<BTLocation>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BTLocation>> call, @NonNull Response<List<BTLocation>> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 200:
                                if (response.isSuccessful() && response.body() != null) {
                                    locationsList = response.body();
                                    if (dropList)
                                        getDropLocationsList(locationsList);
                                    else
                                        dropList = true;
                                }
                                break;
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getContext())).logout();
                                break;
                        }
                        showSearchLocationsProgress(false);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BTLocation>> call, Throwable t) {
                        t.printStackTrace();
                        showSearchLocationsProgress(false);
                    }
                });
    }

    private void getOrganizations(String name) {
        showSearchOrganizationProgress(true);
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, Const.BusinessTrips.TEST_TOKEN);

        RestManager.getApi()
                .getBSOrganization(map, API_BT + BT_GET_ORGANIZATION + name)
                .enqueue(new Callback<List<BTOrganization>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BTOrganization>> call, @NonNull Response<List<BTOrganization>> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 200:
                                if (response.isSuccessful() && response.body() != null) {
                                    organizationsList = response.body();
                                    getDropOrganizationsList(organizationsList);
                                }
                                break;
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getContext())).logout();
                                break;
                        }
                        showSearchOrganizationProgress(false);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BTOrganization>> call, Throwable t) {
                        t.printStackTrace();
                        showSearchOrganizationProgress(false);
                    }
                });
    }

    private void showSearchLocationsProgress(Boolean show) {
        if (pbDestinationsSearch != null && ivDestinationsList != null) {
            if (show) {
                pbDestinationsSearch.setVisibility(View.VISIBLE);
                ivDestinationsList.setVisibility(View.GONE);
            } else {
                pbDestinationsSearch.setVisibility(View.GONE);
                ivDestinationsList.setVisibility(View.VISIBLE);
            }
        }
    }

    private void showSearchOrganizationProgress(Boolean show) {
        if (pbOrganizationsSearch != null && ivOrganizationsList != null) {
            if (show) {
                pbOrganizationsSearch.setVisibility(View.VISIBLE);
                ivOrganizationsList.setVisibility(View.GONE);
            } else {
                pbOrganizationsSearch.setVisibility(View.GONE);
                ivOrganizationsList.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getDropLocationsList(List<BTLocation> locationsList) {
        BTLocationsAdapter adapter = new BTLocationsAdapter(mContext, R.layout.item_bs_location, locationsList);
        acTvFinalDestination.setThreshold(1);//will start working from first characte
        acTvFinalDestination.setAdapter(adapter);//setting the adapter data into the//
        acTvFinalDestination.showDropDown();
    }

    private void getDropOrganizationsList(List<BTOrganization> organizationsList) {
        List<String> list = new ArrayList<>();
        for (BTOrganization bsOrganization : organizationsList) {
            list.add(bsOrganization.getName());
        }
        acTvOrganizations.setAdapter(new ArrayAdapter<>(mContext, R.layout.item_text, R.id.autoCompleteItem, list));
        acTvOrganizations.showDropDown();
    }

    private void initSpinner() {
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, Const.BusinessTrips.TEST_TOKEN);

        RestManager.getApi()
                .getTripAim(map, API_BT + BT_GET_TRIP_AIM)
                .enqueue(new Callback<List<BTTripAim>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BTTripAim>> call, @NonNull Response<List<BTTripAim>> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 200:
                                if (response.isSuccessful() && response.body() != null) {
                                    List<BTTripAim> tripAimList = response.body();
                                    createListForSpinner(tripAimList);
                                }
                                break;
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getContext())).logout();
                                break;
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BTTripAim>> call, Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                    }
                });
    }

    private void createListForSpinner(List<BTTripAim> tripAimList) {
        for (BTTripAim aim : tripAimList) {
            if (businessTrip.getTripTypeId().equals(aim.getTripTypeId()))
                this.tripAimList.add(aim);
        }
        setListToSpinner();
    }

    private void setListToSpinner() {
        ArrayAdapter<BTTripAim> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, tripAimList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sBusinessTripGoal.setAdapter(adapter);
        sBusinessTripGoal.setSelection(getIndex());
        destination.setTripAim(tripAimList.get(getIndex()));
        initSpinnerBusinessTripGoalListener();
    }

    private int getIndex() {
        int index = 2;
        if (destination.getTripAim() != null) {
            for (int i = 0; tripAimList.size() > i; i++) {
                if (tripAimList.get(i).getId().equals(destination.getTripAim().getId()))
                    index = i;
            }
        }
        return index;
    }

    private void initSpinnerBusinessTripGoalListener() {
        sBusinessTripGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                destination.setTripAim(tripAimList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void initCompensations() {
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, Const.BusinessTrips.TEST_TOKEN);

        RestManager.getApi()
                .getCompensationNumbers(map, API_BT + BT_GET_COMPENSATION_NUMBERS)
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 200:
                                if (response.isSuccessful() && response.body() != null) {
                                    List<String> numbersList = response.body();
                                    checkIncomingNumbers(numbersList);
                                }
                                break;
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getContext())).logout();
                                break;
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<String>> call, Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                    }
                });
    }

    private void checkIncomingNumbers(List<String> listNumbers) {
        for (String number : listNumbers) {
            if (number.equals(businessTrip.getEmployee().getCompanyId())) {
                cbCompensationLayout.setVisibility(View.VISIBLE);
                destination.setCompensationStartDate(businessTrip.getStartDate());
                destination.setCompensationEndDate(businessTrip.getEndDate());
                etCompensationStartDate.setText(ConvertTime.dateToString(destination.getCompensationStartDate()));
                etCompensationEndDate.setText(ConvertTime.dateToString(destination.getCompensationEndDate()));
            }
        }
    }

    private void resetCompensation() {
        cbCompensationLayout.setVisibility(View.GONE);
        destination.setCompensationNeeded(false);
        cbCompensation.setChecked(false);
    }

    private void initDialog() {
        waitDialog = WaitDialog.setDialog(getContext());
    }

    private void initDeleteButton() {
        if (newDestination)
            btnDelete.setVisibility(View.GONE);
    }

    private void saveDestination() {
        if (cbNameIsAbsent.isChecked() && !etAbsentCompanyName.getText().toString().equals("")) {
            BTOrganization btOrganization = new BTOrganization();
            btOrganization.setId("-1");
            btOrganization.setName(etAbsentCompanyName.getText().toString());
            destination.setOrganization(btOrganization);
        }
        if (checkAllInfo())
            saveToBT();
    }

    private void saveToBT() {
        if (newDestination)
            businessTrip.getDestinations().add(destination);
        (Objects.requireNonNull(getActivity())).onBackPressed();
    }

    private Boolean checkAllInfo() {
        if (null == destination.getLocation()) {
            Toast.makeText(mContext, mContext.getString(R.string.empty_final_destination), Toast.LENGTH_LONG).show();
            return false;
        }
        if (null == destination.getStartDate()) {
            Toast.makeText(mContext, mContext.getString(R.string.empty_start_date), Toast.LENGTH_LONG).show();
            return false;
        }
        if (null == destination.getEndDate()) {
            Toast.makeText(mContext, mContext.getString(R.string.empty_end_date), Toast.LENGTH_LONG).show();
            return false;
        }
        if (null == destination.getOrganization()) {
            Toast.makeText(mContext, mContext.getString(R.string.empty_company_name), Toast.LENGTH_LONG).show();
            return false;
        }

        if (destination.getStartDate().after(destination.getEndDate())) {
            Toast.makeText(mContext, mContext.getString(R.string.start_date_after_end_date), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void deleteDestination() {
        showDeleteDialog();
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("Так", (dialog, which) -> {
            businessTrip.getDestinations().remove(destination);
            (Objects.requireNonNull(getActivity())).onBackPressed();
        });
        builder.setNegativeButton("Ні", (dialog, which) -> {
        });
        builder.setMessage(Objects.requireNonNull(getContext()).getString(R.string.text_destination_delete));
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
