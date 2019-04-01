package com.dtek.portal.ui.fragment.business_trip;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.business_trips.BT;
import com.dtek.portal.models.business_trips.BTDestination;
import com.dtek.portal.models.business_trips.BTEmployee;
import com.dtek.portal.models.business_trips.BTHotel;
import com.dtek.portal.models.business_trips.BTPreview;
import com.dtek.portal.models.business_trips.BTTicket;
import com.dtek.portal.models.business_trips.TripTypes;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.businees_trips.AdditionalServicesListAdapter;
import com.dtek.portal.ui.adapter.businees_trips.ApproversListAdapter;
import com.dtek.portal.ui.adapter.businees_trips.BTEmployeesAdapter;
import com.dtek.portal.ui.adapter.businees_trips.FinalDestinationsListAdapter;
import com.dtek.portal.ui.adapter.businees_trips.HotelsListAdapter;
import com.dtek.portal.ui.adapter.businees_trips.TicketsListAdapter;
import com.dtek.portal.ui.dialog.DatePickerDialog;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.utils.BusinessTrip;
import com.dtek.portal.utils.ConvertTime;
import com.dtek.portal.utils.PickerUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.dtek.portal.Const.BusinessTrips.API_BT;
import static com.dtek.portal.Const.BusinessTrips.BT_GET;
import static com.dtek.portal.Const.BusinessTrips.BT_GET_EMPLOYEE;
import static com.dtek.portal.Const.BusinessTrips.BT_GET_EMPLOYEES;
import static com.dtek.portal.Const.BusinessTrips.BT_GET_TRIP_TYPES;
import static com.dtek.portal.Const.BusinessTrips.BT_STATUS_APPROVED;
import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.utils.ConvertTime.stringToDate;

public class AddDetailBusinessTripsFragment extends Fragment {

    private BT businessTrip;
    private BTPreview btPreview;

    private static final int REQUEST_DATE_START = 1;
    private static final int REQUEST_DATE_END = 2;
    private static final int REQUEST_DATE_DELAY = 3;
    private static final int REQUEST_DATE_PASSPORT_VALIDITY = 4;

    private Unbinder unbinder;
    private Dialog waitDialog;

    private FinalDestinationsListAdapter finalDestinationsListAdapter;
    private TicketsListAdapter ticketsListAdapter;
    private HotelsListAdapter hotelsListAdapter;
    private AdditionalServicesListAdapter additionalServicesListAdapter;
    private ApproversListAdapter approversListAdapter;

    private List<BTEmployee> employeesList = new ArrayList<>();
    private List<BTEmployee> filterEmployeesList = new ArrayList<>();

    @BindView(R.id.et_employee)
    AutoCompleteTextView acTvEmployee;
    @BindView(R.id.iv_employee_cancel)
    ImageView ivEmployeeCancel;
    @BindView(R.id.spinner_business_trip_type)
    Spinner spinnerBusinessTripType;
    @BindView(R.id.pb_employee_search)
    ProgressBar pbEmployeeSearch;


    @BindView(R.id.status_info_layout)
    LinearLayout statusInfoLayout;
    @BindView(R.id.tv_request_number)
    TextView tvRequestNumber;
    @BindView(R.id.tv_request_status)
    TextView tvRequestStatus;
    @BindView(R.id.tv_create_date)
    TextView tvCreateDate;

    @BindView(R.id.final_destinations_list_btn)
    ImageButton finalDestinationsListBtn;
    @BindView(R.id.final_destination_add_btn)
    ImageButton ibFinalDestinationAddBtn;
    @BindView(R.id.final_destination_list)
    LinearLayout finalDestinationList;
    @BindView(R.id.rv_final_destination)
    RecyclerView rvFinalDestinations;

    @BindView(R.id.tickets_list_btn)
    ImageButton tickets_list_btn;
    @BindView(R.id.ticket_add_btn)
    ImageButton ibTicketAdBtn;
    @BindView(R.id.tickets_list)
    LinearLayout tickets_list;
    @BindView(R.id.rv_tickets)
    RecyclerView rvTickets;

    @BindView(R.id.hotels_list_btn)
    ImageButton hotels_list_btn;
    @BindView(R.id.hotel_add_btn)
    ImageButton ibHotelAddBtn;
    @BindView(R.id.hotels_list)
    LinearLayout hotels_list;
    @BindView(R.id.rv_hotels)
    RecyclerView rvHotels;

    @BindView(R.id.additional_services_list_btn)
    ImageButton ib_Additional_Services_List_Btn;
    @BindView(R.id.additional_service_add_btn)
    ImageButton ibAdditionalServiceAddBtn;
    @BindView(R.id.additional_services_list)
    LinearLayout additional_services_list;
    @BindView(R.id.rv_additional_services)
    RecyclerView rvAdditionalServices;

    @BindView(R.id.approver_layout)
    LinearLayout approverLayout;
    @BindView(R.id.approvers_list_btn)
    ImageButton ibApproversListButton;
    @BindView(R.id.approvers_list)
    LinearLayout approvers_list;
    @BindView(R.id.rv_approvers)
    RecyclerView rvApprovers;


    @BindView(R.id.delay_date_layout)
    LinearLayout delayDateLayout;
    @BindView(R.id.spinner_delay)
    Spinner spinnerDelay;
    @BindView(R.id.et_date_start)
    EditText etDateStart;
    @BindView(R.id.et_date_end)
    EditText etDateEnd;
    @BindView(R.id.et_date_delay)
    EditText etDateDelay;

    @BindView(R.id.tv_final_destination_count)
    TextView tvFinalDestinationsCount;
    @BindView(R.id.tv_tickets_count)
    TextView tvTicketsCount;
    @BindView(R.id.tv_hotel_count)
    TextView tvHotelCount;
    @BindView(R.id.tv_additional_services_count)
    TextView tvAdditionalServicesCount;

    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_change)
    Button btnChange;
    @BindView(R.id.btn_recall)
    Button btnRecall;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_approving)
    Button btnApproving;
    @BindView(R.id.btn_save_changes)
    Button btnSaveChanges;
    @BindView(R.id.buttons_layout)
    ConstraintLayout buttonsLayout;

    @BindView(R.id.passport_data_layout)
    LinearLayout passportDataLayout;
    @BindView(R.id.et_passport_number)
    EditText etPassportNumber;
    @BindView(R.id.passport_number_layout)
    LinearLayout passportNumberLayout;
    @BindView(R.id.passport_validity_layout)
    LinearLayout passportValidityLayout;
    @BindView(R.id.et_passport_validity)
    EditText etPassportValidity;

    public AddDetailBusinessTripsFragment() {
    }

    @SuppressLint("ValidFragment")
    public AddDetailBusinessTripsFragment(BTPreview btPreview) {
        this.btPreview = btPreview;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_business_trips_add, container, false);
        unbinder = ButterKnife.bind(this, v);
        btnDelete.setVisibility(View.GONE);

        businessTrip = BusinessTrip.getInstance().getBusinessTrip();

        initDialog();
        initRecyclerViews();
        initBT();
        return v;
    }

    @OnClick({R.id.btn_save, R.id.btn_change, R.id.btn_recall, R.id.btn_save_changes, R.id.btn_approving})
    void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                if (checkBtBeforeSave())
                    saveBt();
                break;
            case R.id.btn_change:
                BusinessTrip.getInstance().setChangeStatus(true);
                if (new Date().after(businessTrip.getEndDate()) && !businessTrip.getStatusRequest().equals(Const.BusinessTrips.BT_STATUS_NEW))
                    Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getString(R.string.wrong_dates), Toast.LENGTH_LONG).show();
                else
                    setChangeStatus(true);
                break;
            case R.id.btn_recall:
                showCancelDialog();
                break;
            case R.id.btn_save_changes:
                if (checkBtBeforeSave())
                    saveChanges();
                break;
            case R.id.btn_approving:
                approveBT();
                break;
        }
    }

    @OnClick({R.id.et_date_start, R.id.et_date_end, R.id.et_date_delay, R.id.et_passport_validity})
    void onDateClick(View view) {
        switch (view.getId()) {
            case R.id.et_date_start:
                PickerUtils.initDatePicker(AddDetailBusinessTripsFragment.this,
                        stringToDate(etDateStart.getText().toString()), new Date(), null, REQUEST_DATE_START);
                break;
            case R.id.et_date_end:
                PickerUtils.initDatePicker(AddDetailBusinessTripsFragment.this,
                        stringToDate(etDateEnd.getText().toString()), stringToDate(etDateStart.getText().toString()), null, REQUEST_DATE_END);
                break; //
            case R.id.et_date_delay:
                if (etDateStart.getText().toString().equals(""))
                    Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getString(R.string.empty_start_date), Toast.LENGTH_LONG).show();
                else
                    PickerUtils.initDatePicker(AddDetailBusinessTripsFragment.this,
                            stringToDate(etDateEnd.getText().toString()), stringToDate(etDateEnd.getText().toString()), null, REQUEST_DATE_DELAY);
                break;
            case R.id.et_passport_validity:
                PickerUtils.initDatePicker(AddDetailBusinessTripsFragment.this,
                        new Date(), null, null, REQUEST_DATE_PASSPORT_VALIDITY);
                break;
        }
    }

    @OnItemSelected({R.id.spinner_delay})
    public void spinnerItemSelected(Spinner spinner, int position) {
        switch (spinner.getId()) {
            case R.id.spinner_delay:
                if (position == 1) {
                    businessTrip.setDelay(true);
                    delayDateLayout.setVisibility(View.VISIBLE);
                } else {
                    businessTrip.setDelay(false);
                    businessTrip.setDelayDate(null);
                    delayDateLayout.setVisibility(View.GONE);
                    etDateDelay.setText("");
                }
                break;
        }
    }

    @OnClick({R.id.final_destinations_list_btn, R.id.tickets_list_btn, R.id.hotels_list_btn, R.id.additional_services_list_btn, R.id.approvers_list_btn})
    void onClickItem(View view) {
        View list = new View(getContext());
        switch (view.getId()) {
            case R.id.final_destinations_list_btn:
                list = finalDestinationList;
                break;
            case R.id.tickets_list_btn:
                list = tickets_list;
                break;
            case R.id.hotels_list_btn:
                list = hotels_list;
                break;
            case R.id.additional_services_list_btn:
                list = additional_services_list;
                break;
            case R.id.approvers_list_btn:
                list = approvers_list;
                break;

        }
        setListVisibility(view, list);
    }

    @OnClick({R.id.final_destination_add_btn, R.id.ticket_add_btn, R.id.hotel_add_btn, R.id.additional_service_add_btn})
    void onClickAddItem(View view) {
        if (checkDates()) {
            switch (view.getId()) {
                case R.id.final_destination_add_btn:
                    switchFragment(new AddFinalDestinationFragment(), getString(R.string.final_destination));
                    break;
                case R.id.ticket_add_btn:
                    switchFragment(new AddTicketFragment(), getString(R.string.tickets));
                    break;
                case R.id.hotel_add_btn:
                    switchFragment(new AddHotelFragment(), getString(R.string.hotel));
                    break;
                case R.id.additional_service_add_btn:
                    switchFragment(new AddAdditionalServiceFragment(), getString(R.string.additional_services));
                    break;
            }
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
                    etDateEnd.setText(ConvertTime.dateToString(date));
                    businessTrip.setStartDate(date);
                    businessTrip.setEndDate(date);
                    break;
                case REQUEST_DATE_END:
                    Date dateEnd = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    etDateEnd.setText(ConvertTime.dateToString(dateEnd));
                    businessTrip.setEndDate(dateEnd);
                    break;
                case REQUEST_DATE_DELAY:
                    Date dateDelay = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    etDateDelay.setText(ConvertTime.dateToString(dateDelay));
                    businessTrip.setDelayDate(dateDelay);
                    break;
                case REQUEST_DATE_PASSPORT_VALIDITY:
                    Date datePassportValidity = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    etPassportValidity.setText(ConvertTime.dateToString(datePassportValidity));
                    businessTrip.setPassportDate(datePassportValidity);
            }
        }
    }

    private void initBT() {
            if (null == btPreview) {
                loadInfo();
            } else
                getBTFromApi();
    }

    private void loadInfo() {
        initRequestStatus();
        getEmployee();
        initSpinnerDelay();
        initDates();
        getTripTypes();
        initInfo();
    }

    @SuppressLint("SetTextI18n")
    private void initRequestStatus() {
        if (businessTrip.getId() != null) {
            statusInfoLayout.setVisibility(View.VISIBLE);
            tvRequestNumber.setText(businessTrip.getId().toString());
            tvRequestStatus.setText(businessTrip.getStatusRequestName());
            tvCreateDate.setText(ConvertTime.dateToString(businessTrip.getCreatedDate()));

            if(businessTrip.getStatusRequest().equals(Const.BusinessTrips.BT_STATUS_NEW))
                BusinessTrip.getInstance().setChangeStatus(true);
            setChangeStatus(BusinessTrip.getInstance().getChangeStatus());
        }
    }

    private void getBTFromApi() {
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, Const.BusinessTrips.TEST_TOKEN);

        RestManager.getApi()
                .getBT(map, API_BT + BT_GET + "/" + btPreview.getId() + "?personnelNumber=" + btPreview.getPersonnelNumber())
                .enqueue(new Callback<BT>() {
                    @Override
                    public void onResponse(@NonNull Call<BT> call, @NonNull Response<BT> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 200:
                                if (response.isSuccessful() && response.body() != null) {
                                    businessTrip = response.body();
                                    BusinessTrip.getInstance().setBusinessTrip(businessTrip);
                                    loadInfo();
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
                    public void onFailure(@NonNull Call<BT> call, Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                    }
                });
    }

    private void getEmployee() {
        if (businessTrip.getEmployee() != null) {
            acTvEmployee.setText(businessTrip.getEmployee().getFullName());
            employeesList.add(businessTrip.getEmployee());
            setListenerForEmployee();
        } else {
            getEmployeeFromServer();
        }
    }

    private void getEmployeeFromServer() {
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, Const.BusinessTrips.TEST_TOKEN);

        RestManager.getApi()
                .getBTEmployee(map, API_BT + BT_GET_EMPLOYEE)
                .enqueue(new Callback<BTEmployee>() {
                    @Override
                    public void onResponse(@NonNull Call<BTEmployee> call, @NonNull Response<BTEmployee> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 200:
                                if (response.isSuccessful() && response.body() != null) {
                                    BTEmployee employee = response.body();
                                    businessTrip.setEmployee(employee);
                                    acTvEmployee.setText(employee.getFullName());
                                    employeesList.add(businessTrip.getEmployee());
                                    setListenerForEmployee();
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
                    public void onFailure(@NonNull Call<BTEmployee> call, Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                    }
                });
    }

    private void setListenerForEmployee() {

        ivEmployeeCancel.setOnClickListener(v -> {
            acTvEmployee.setFocusableInTouchMode(true);
            acTvEmployee.setText("");
            acTvEmployee.requestFocus();
            ivEmployeeCancel.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        });

//        acTvEmployee.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                acTvEmployee.setText("");
//                ivEmployeeCancel.setVisibility(View.GONE);
//            }
//        });

        acTvEmployee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (acTvEmployee.hasFocus()) {
                    String strEntered = s.toString();
                    if (strEntered.length() > 2) {
                        if (employeesList.isEmpty())
                            getEmployees(strEntered);
                    } else {
                        employeesList.clear();
                        getDropEmployeesList(employeesList);
                    }

                    filterEmployeesList.clear();
                    for (BTEmployee employee : employeesList) {
                        if (employee.getFullName().toLowerCase().contains(strEntered.toLowerCase())) { //проверяем иммя на содержание нашего текста
                            filterEmployeesList.add(employee); // добавляем объект в массив фильта
                        }
                    }

                    if (filterEmployeesList.isEmpty())  // если массив фильтра пустой
                        getDropEmployeesList(employeesList); // выпадающий список с масивом полученным из сервера
                    else
                        getDropEmployeesList(filterEmployeesList); // выпадающий список с масивом фильтра
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        acTvEmployee.setOnItemClickListener((adapterView, view, i, l) -> {
            businessTrip.setEmployee(filterEmployeesList.get(0));
            acTvEmployee.setText(businessTrip.getEmployee().getFullName());
            acTvEmployee.setFocusable(false);
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(acTvEmployee.getWindowToken(), 0);
        });
    }

    private void getEmployees(String name) {
        pbEmployeeSearch.setVisibility(View.VISIBLE);
        ivEmployeeCancel.setVisibility(View.GONE);

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, Const.BusinessTrips.TEST_TOKEN);

        RestManager.getApi()
                .getBTEmployees(map, API_BT + BT_GET_EMPLOYEES + "?surname=" + name)
                .enqueue(new Callback<List<BTEmployee>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BTEmployee>> call, @NonNull Response<List<BTEmployee>> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 200:
                                if (response.isSuccessful() && response.body() != null) {
                                    employeesList = response.body();
                                    getDropEmployeesList(employeesList);
                                }
                                break;
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getContext())).logout();
                                break;
                        }
                        pbEmployeeSearch.setVisibility(View.GONE);
                        ivEmployeeCancel.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BTEmployee>> call, Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                        pbEmployeeSearch.setVisibility(View.GONE);
                        ivEmployeeCancel.setVisibility(View.VISIBLE);
                    }
                });
    }

//    private void showSearchEmployeesProgress(Boolean show) {
//        if (pbDestinationsSearch != null && ivDestinationsList != null) {
//            if (show) {
//                pbDestinationsSearch.setVisibility(View.VISIBLE);
//                ivDestinationsList.setVisibility(View.GONE);
//            } else {
//                pbDestinationsSearch.setVisibility(View.GONE);
//                ivDestinationsList.setVisibility(View.VISIBLE);
//            }
//        }
//    }

    private void getDropEmployeesList(List<BTEmployee> employeesList) {
        BTEmployeesAdapter adapter = new BTEmployeesAdapter(getContext(), R.layout.item_bt_employee, employeesList);
        acTvEmployee.setThreshold(1);//will start working from first characte
        acTvEmployee.setAdapter(adapter);//setting the adapter data into the//
        acTvEmployee.showDropDown();
    }

    private void getTripTypes() {
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, Const.BusinessTrips.TEST_TOKEN);

        RestManager.getApi()
                .getTripTypes(map, API_BT + BT_GET_TRIP_TYPES)
                .enqueue(new Callback<List<TripTypes>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<TripTypes>> call, @NonNull Response<List<TripTypes>> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 200:
                                if (response.isSuccessful() && response.body() != null) {
                                    List<TripTypes> tripTypesList = response.body();
                                    initSpinnerBTtype(tripTypesList);
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
                    public void onFailure(@NonNull Call<List<TripTypes>> call, Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                    }
                });
    }

    private void saveBt() {
        businessTrip.setPassportNumber(etPassportNumber.getText().toString());
        Timber.d(businessTrip.toString()); //todo вернуть назад
//        saveBtToAPI();
    }

    private void saveBtToAPI() {
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, Const.BusinessTrips.TEST_TOKEN);
//        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        RestManager.getApi()
                .saveBT(map, businessTrip)
                .enqueue(new Callback<BT>() {
                    @Override
                    public void onResponse(@NonNull Call<BT> call, @NonNull Response<BT> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 200:
                                if (response.isSuccessful() && response.body() != null) {
//                                    BusinessTrip.getInstance().setBusinessTrip(response.body());
//                                    initRecyclerViews();
//                                    initBT();
                                    ((MainActivity) Objects.requireNonNull(getContext())).switchToFragment(new TabBusinessTripFragment(TabBusinessTripFragment.TAB_ALL), true);
                                }
                                break;
                            case 400:
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    showErrorDialog(jObjError.getString("message"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getContext())).logout();
                                break;
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<BT> call, Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                    }
                });
    }

    private void showErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void initRecyclerViews() {

        rvFinalDestinations.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTickets.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHotels.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAdditionalServices.setLayoutManager(new LinearLayoutManager(getContext()));
        rvApprovers.setLayoutManager(new LinearLayoutManager(getContext()));


        finalDestinationsListAdapter = new FinalDestinationsListAdapter();
        ticketsListAdapter = new TicketsListAdapter();
        hotelsListAdapter = new HotelsListAdapter();
        additionalServicesListAdapter = new AdditionalServicesListAdapter();
        approversListAdapter = new ApproversListAdapter();

        rvFinalDestinations.setAdapter(finalDestinationsListAdapter);
        rvTickets.setAdapter(ticketsListAdapter);
        rvHotels.setAdapter(hotelsListAdapter);
        rvAdditionalServices.setAdapter(additionalServicesListAdapter);
        rvApprovers.setAdapter(approversListAdapter);
    }

    private void initSpinnerBTtype(List<TripTypes> tripTypesList) {
        ArrayAdapter<TripTypes> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, tripTypesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBusinessTripType.setAdapter(adapter);
        spinnerBusinessTripType.setSelection(getIndexType(tripTypesList));
        initSpinnerBusinessTripTypeListener(tripTypesList);
    }

    private int getIndexType(List<TripTypes> tripTypesList) {
        if (businessTrip.getTripTypeId() != null && !businessTrip.getTripTypeId().equals("")) {
            for (int i = 0; i < tripTypesList.size(); i++) {
                if (tripTypesList.get(i).getId().equals(businessTrip.getTripTypeId()))
                    return i;
            }
        }
        return 0;
    }

    private void initSpinnerDelay() {
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.delay, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDelay.setAdapter(adapter);
        spinnerDelay.setSelection(getDelayIndex());
    }

    private int getDelayIndex() {
        if (businessTrip.getDelay() != null && businessTrip.getDelay()) {
            delayDateLayout.setVisibility(View.VISIBLE);
            return 1;
        } else
            return 0;
    }

    private void approveBT() {
        businessTrip.setPassportNumber(etPassportNumber.getText().toString());
        sendApproveBT();
    }

    private void sendApproveBT() {
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, Const.BusinessTrips.TEST_TOKEN);
//        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        RestManager.getApi()
                .approveBT(map, businessTrip)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 200:
                                if (response.isSuccessful() && response.body() != null) {
                                    ((MainActivity) Objects.requireNonNull(getContext())).switchToFragment(new TabBusinessTripFragment(TabBusinessTripFragment.TAB_ACTIVE), true);
                                }
                                break;
                            case 400:
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    showErrorDialog(jObjError.getString("message"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getContext())).logout();
                                break;
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                    }
                });
    }

    private void showCancelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("Так", (dialog, which) -> recallBT());
        builder.setNegativeButton("Ні", (dialog, which) -> (Objects.requireNonNull(getActivity())).onBackPressed());
        builder.setMessage(Objects.requireNonNull(getContext()).getString(R.string.text_cancel_bt));
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void saveChanges() {
        businessTrip.setPassportNumber(etPassportNumber.getText().toString());
//        saveBtChangesToAPI(); //todo вернуть назад
    }

    private void saveBtChangesToAPI() {
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, Const.BusinessTrips.TEST_TOKEN);
//        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        RestManager.getApi()
                .saveBTChanges(map, businessTrip)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 200:
                                if (response.isSuccessful() && response.body() != null) {
                                    ((MainActivity) Objects.requireNonNull(getContext())).switchToFragment(new TabBusinessTripFragment(TabBusinessTripFragment.TAB_ALL), true);
                                }
                                break;
                            case 400:
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    showErrorDialog(jObjError.getString("message"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getContext())).logout();
                                break;
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                    }
                });
    }

    private void recallBT() {
        waitDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, Const.BusinessTrips.TEST_TOKEN);
//        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        RestManager.getApi()
                .cancelBT(map, businessTrip)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 200:
                                if (response.isSuccessful()) {
                                    ((MainActivity) Objects.requireNonNull(getContext())).switchToFragment(new TabBusinessTripFragment(TabBusinessTripFragment.TAB_ACTIVE), true);
                                }
                                break;
                            case 400:
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    showErrorDialog(jObjError.getString("message"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getContext())).logout();
                                break;
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                    }
                });
    }

    private Boolean checkDates() {
        if (businessTrip.getStartDate() != null && businessTrip.getEndDate() != null)
            return Boolean.TRUE;
        Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getString(R.string.empty_start_date), Toast.LENGTH_LONG).show();
        return Boolean.FALSE;
    }

    private void setListVisibility(View view, View list) {
        if (list.getVisibility() == View.VISIBLE) {
            view.setBackgroundResource(R.drawable.arrow_down);
            list.setVisibility(View.GONE);
        } else {
            view.setBackgroundResource(R.drawable.arrow_up);
            list.setVisibility(View.VISIBLE);
        }
    }

    private void switchFragment(Fragment fragment, String title) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).switchToFragment(fragment, false);
            ((MainActivity) getActivity()).mToolbarTitle.setText(title);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showUpButton();
            ((MainActivity) getActivity()).mToolbarTitle.setText(getString(R.string.title_business_trip));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        btPreview = null;
    }

    private void initDialog() {
        waitDialog = WaitDialog.setDialog(getContext());
    }

    private void initSpinnerBusinessTripTypeListener(List<TripTypes> tripTypesList) {
        spinnerBusinessTripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                businessTrip.setTripTypeId(tripTypesList.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initDates() {
        if (businessTrip.getStartDate() != null)
            etDateStart.setText(ConvertTime.dateToString(businessTrip.getStartDate()));
        if (businessTrip.getEndDate() != null)
            etDateEnd.setText(ConvertTime.dateToString(businessTrip.getEndDate()));
        if (businessTrip.getDelayDate() != null)
            etDateDelay.setText(ConvertTime.dateToString(businessTrip.getDelayDate()));
    }

    private void initInfo() {
        initFinalDestinations();
        initTickets();
        initHotels();
        initAdditionalServices();
        initPassport();
        initApprovers();
    }

    @SuppressLint("SetTextI18n")
    private void initFinalDestinations() {
        tvFinalDestinationsCount.setText("(" + businessTrip.getDestinations().size() + ")");
        loadDestinationsList();
    }

    private void loadDestinationsList() {
        finalDestinationsListAdapter.setItems(businessTrip.getDestinations());
    }

    @SuppressLint("SetTextI18n")
    private void initTickets() {
        tvTicketsCount.setText("(" + businessTrip.getTickets().size() + ")");
        loadTicketsList();
    }

    private void loadTicketsList() {
        ticketsListAdapter.setItems(businessTrip.getTickets());
    }

    @SuppressLint("SetTextI18n")
    private void initHotels() {
        tvHotelCount.setText("(" + businessTrip.getHotels().size() + ")");
        loadHotelsList();
    }

    private void loadHotelsList() {
        hotelsListAdapter.setItems(businessTrip.getHotels());
    }

    @SuppressLint("SetTextI18n")
    private void initAdditionalServices() {
        tvAdditionalServicesCount.setText("(" + businessTrip.getAdditionalServices().size() + ")");
        loadAdditionalServicesList();
    }

    private void loadAdditionalServicesList() {
        additionalServicesListAdapter.setItems(businessTrip.getAdditionalServices());
    }

    private void initApprovers(){
        if(businessTrip.getApprover()!=null) {
            if (!businessTrip.getApprover().isEmpty()) {
                approverLayout.setVisibility(View.VISIBLE);
                loadApproverList();
            }
        }
    }

    private void loadApproverList() {
        approversListAdapter.setItems(businessTrip.getApprover());
    }

    private void initPassport() {
        for (BTDestination destination : businessTrip.getDestinations()) {
            if (!destination.getLocation().getCountry().getId().equals("UA")) {
                passportDataLayout.setVisibility(View.VISIBLE);
                passportNumberLayout.setVisibility(View.VISIBLE);
                passportValidityLayout.setVisibility(View.VISIBLE);

                if (businessTrip.getPassportDate() != null)
                    etPassportValidity.setText(ConvertTime.dateToString(businessTrip.getPassportDate()));
                if (businessTrip.getPassportNumber() != null)
                    etPassportNumber.setText(businessTrip.getPassportNumber());
            }
        }
    }

    private Boolean checkBtBeforeSave() {

        if (null == businessTrip.getStartDate()) {
            Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getString(R.string.empty_start_date), Toast.LENGTH_LONG).show();
            return false;
        }
        if (businessTrip.getDelay() && null == businessTrip.getDelayDate()) {
            Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getString(R.string.empty_delay_date), Toast.LENGTH_LONG).show();
            return false;
        }
        if (businessTrip.getDestinations().isEmpty()) {
            Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getString(R.string.empty_dest), Toast.LENGTH_LONG).show();
            return false;
        }

        for (BTDestination destination : businessTrip.getDestinations()) {
            if (destination.getStartDate().before(businessTrip.getStartDate()) || destination.getEndDate().after(businessTrip.getEndDate())) {
                Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getString(R.string.wrong_destination_date), Toast.LENGTH_LONG).show();
                return false;
            }
        }

        Date checkTicketEndDate;
        if (businessTrip.getDelay())
            checkTicketEndDate = businessTrip.getDelayDate();
        else
            checkTicketEndDate = businessTrip.getEndDate();

        for (BTTicket ticket : businessTrip.getTickets()) {
            if (ticket.getDepartureDate().before(businessTrip.getStartDate()) || ticket.getDepartureDate().after(checkTicketEndDate)) {
                Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getString(R.string.wrong_ticket_date), Toast.LENGTH_LONG).show();
                return false;
            }
        }

        for (BTHotel hotel : businessTrip.getHotels()) {
            if (hotel.getArrivalDate().before(businessTrip.getStartDate()) || hotel.getDepartureDate().after(businessTrip.getEndDate())) {
                Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getString(R.string.wrong_hotel_date), Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private void setChangeStatus(Boolean isChange) {
        btnSave.setVisibility(View.GONE);

        if (businessTrip.getStatusRequest().equals(Const.BusinessTrips.BT_STATUS_CANCELED)
                || businessTrip.getStatusRequest().equals(Const.BusinessTrips.BT_STATUS_APPROVING)
                || businessTrip.getStatusRequest().equals(Const.BusinessTrips.BT_STATUS_CLOSED))
            buttonsLayout.setVisibility(View.GONE);

        acTvEmployee.setEnabled(isChange);
        spinnerBusinessTripType.setEnabled(isChange);
        etDateStart.setEnabled(isChange);
        etDateEnd.setEnabled(isChange);
        spinnerDelay.setEnabled(isChange);
        etDateDelay.setEnabled(isChange);
        etPassportNumber.setEnabled(isChange);
        etPassportValidity.setEnabled(isChange);
        ivEmployeeCancel.setEnabled(isChange);

        finalDestinationsListAdapter.setClick(isChange);
        ticketsListAdapter.setClick(isChange);
        hotelsListAdapter.setClick(isChange);
        additionalServicesListAdapter.setClick(isChange);

        if (!isChange) {
            btnSaveChanges.setVisibility(View.GONE);
            btnApproving.setVisibility(View.GONE);
            btnChange.setVisibility(View.VISIBLE);
            btnRecall.setVisibility(View.VISIBLE);

            ibFinalDestinationAddBtn.setVisibility(View.GONE);
            ibTicketAdBtn.setVisibility(View.GONE);
            ibHotelAddBtn.setVisibility(View.GONE);
            ibAdditionalServiceAddBtn.setVisibility(View.GONE);
        } else {
            btnSaveChanges.setVisibility(View.VISIBLE);
            btnChange.setVisibility(View.GONE);
            btnRecall.setVisibility(View.GONE);
            btnApproving.setVisibility(View.VISIBLE);

            ibFinalDestinationAddBtn.setVisibility(View.VISIBLE);
            ibTicketAdBtn.setVisibility(View.VISIBLE);
            ibHotelAddBtn.setVisibility(View.VISIBLE);
            ibAdditionalServiceAddBtn.setVisibility(View.VISIBLE);
        }

        if(businessTrip.getStatusRequest().equals(BT_STATUS_APPROVED))
            btnSaveChanges.setVisibility(View.GONE);
    }
}
