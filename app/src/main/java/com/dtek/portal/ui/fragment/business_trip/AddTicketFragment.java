package com.dtek.portal.ui.fragment.business_trip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import com.dtek.portal.models.business_trips.BTLocation;
import com.dtek.portal.models.business_trips.BTTicket;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.businees_trips.BTLocationsAdapter;
import com.dtek.portal.ui.dialog.DatePickerDialog;
import com.dtek.portal.ui.dialog.TimePickerDialog;
import com.dtek.portal.utils.BusinessTrip;
import com.dtek.portal.utils.ConvertTime;
import com.dtek.portal.utils.PickerUtils;
import com.dtek.portal.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.BusinessTrips.API_BT;
import static com.dtek.portal.Const.BusinessTrips.BT_GET_LOCATIONS;
import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.utils.ConvertTime.stringToDate;
import static com.dtek.portal.utils.ConvertTime.stringToTime;

public class AddTicketFragment extends Fragment {

    private BT businessTrip;
    private BTTicket btTicket;
    private Context mContext;
    private Boolean newTicket = true;

    private List<BTLocation> locationsListTo = new ArrayList<>();
    private List<BTLocation> filterLocationsListTo = new ArrayList<>();

    private List<BTLocation> locationsListFrom = new ArrayList<>();
    private List<BTLocation> filterLocationsListFrom = new ArrayList<>();

    private Date dateReturn;
    private Date timeReturn;

    private boolean dropList = true;


    private static final int REQUEST_DATE_DEPARTURE = 1;
    private static final int REQUEST_TIME_DEPARTURE = 2;
    private static final int REQUEST_DATE_DEPARTURE_OF_RETURN_TRIP = 3;
    private static final int REQUEST_TIME_DEPARTURE_OF_RETURN_TRIP = 4;


    private Unbinder unbinder;

    @BindView(R.id.et_departure_date)
    EditText etDepartureDate;
    @BindView(R.id.et_departure_time)
    EditText etDepartureTime;
    @BindView(R.id.return_trip_cb_layout)
    LinearLayout returnTripCbLayout;
    @BindView(R.id.cb_return_trip)
    CheckBox cbReturnTrip;
    @BindView(R.id.return_trip_layout)
    LinearLayout returnTripLayout;
    @BindView(R.id.et_departure_date_of_return_trip)
    EditText etDepartureDateOfReturnTrip;
    @BindView(R.id.et_departure_time_of_return_trip)
    EditText etDepartureTimeOfReturnTrip;

    @BindView(R.id.spinner_ticket_type)
    Spinner sTicketType;

    @BindView(R.id.ac_tv_destination_to)
    AutoCompleteTextView acTvDestinationTo;
    @BindView(R.id.pb_destination_to_search)
    ProgressBar pdDestinationToSearch;
    @BindView(R.id.iv_destination_to_list)
    ImageView ivDestinationToList;
    @BindView(R.id.ac_tv_destination_from)
    AutoCompleteTextView acTvDestinationFrom;
    @BindView(R.id.pb_destination_from_search)
    ProgressBar pdDestinationFromSearch;
    @BindView(R.id.iv_destination_from_list)
    ImageView ivDestinationFromList;

    @BindView(R.id.et_comments)
    EditText etComment;
    @BindView(R.id.btn_delete)
    Button btnDelete;


    public AddTicketFragment() {
    }

    @SuppressLint("ValidFragment")
    public AddTicketFragment(BTTicket btTicket) {
        this.btTicket = btTicket;
        newTicket = false;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_tickets_add, container, false);
        unbinder = ButterKnife.bind(this, v);
        mContext = v.getContext();
        businessTrip = BusinessTrip.getInstance().getBusinessTrip();

        initDeleteButton();
        initSpinnerTicketType();
        initTicket();
        initListeners();
        return v;
    }

    private void initTicket() {
        if (null == btTicket) {
            btTicket = new BTTicket();
            btTicket.setTicketType("ЖД");
            initDates();
        } else
            loadTicketData();
    }

    private void initDates() {
        btTicket.setDepartureDate(businessTrip.getStartDate());
        etDepartureDate.setText(ConvertTime.dateToString(businessTrip.getStartDate()));
    }

    private void loadTicketData() {
        initSpinner();
        btnDelete.setVisibility(View.VISIBLE);
        etDepartureDate.setText(ConvertTime.dateToString(btTicket.getDepartureDate()));
        if (btTicket.getDepartureTime() != null)
            etDepartureTime.setText(ConvertTime.timeToString(btTicket.getDepartureTime()));
        acTvDestinationFrom.setText(btTicket.getFrom().getCity().getName());
        acTvDestinationTo.setText(btTicket.getTo().getCity().getName());
        etComment.setText(btTicket.getComment());
        returnTripCbLayout.setVisibility(View.GONE);
    }

    private void initSpinner() {
        List<String> types = new ArrayList<>();
        Collections.addAll(types, mContext.getResources().getStringArray(R.array.tickets_type));
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).equals(btTicket.getTicketType()))
                sTicketType.setSelection(i);
        }
    }

    @OnClick({R.id.cb_return_trip, R.id.btn_save, R.id.btn_delete})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_return_trip:
                if (cbReturnTrip.isChecked()) {
                    returnTripLayout.setVisibility(View.VISIBLE);
                    if (businessTrip.getDelayDate() != null) {
                        etDepartureDateOfReturnTrip.setText(ConvertTime.dateToString(businessTrip.getDelayDate()));
                        dateReturn = businessTrip.getDelayDate();
                    } else {
                        etDepartureDateOfReturnTrip.setText(ConvertTime.dateToString(businessTrip.getEndDate()));
                        dateReturn = businessTrip.getEndDate();
                    }
                } else
                    returnTripLayout.setVisibility(View.GONE);
                break;
            case R.id.btn_save:
                saveTicket();
                break;
            case R.id.btn_delete:
                deleteTicket();
                break;
        }
    }

    @OnClick({R.id.et_departure_date, R.id.et_departure_time, R.id.et_departure_date_of_return_trip, R.id.et_departure_time_of_return_trip})
    void onDateTimeClick(View view) {
        switch (view.getId()) {
            case R.id.et_departure_date:
                PickerUtils.initDatePicker(AddTicketFragment.this,
                        businessTrip.getStartDate(), businessTrip.getStartDate(), getMaxDate(), REQUEST_DATE_DEPARTURE);
                break;
            case R.id.et_departure_date_of_return_trip:
                PickerUtils.initDatePicker(AddTicketFragment.this,
                        stringToDate(etDepartureDateOfReturnTrip.getText().toString()), businessTrip.getStartDate(), getMaxDate(), REQUEST_DATE_DEPARTURE_OF_RETURN_TRIP);
                break;
            case R.id.et_departure_time:
                PickerUtils.initTimePicker(AddTicketFragment.this,
                        stringToTime(etDepartureTime.getText().toString()), REQUEST_TIME_DEPARTURE);
                break;
            case R.id.et_departure_time_of_return_trip:
                PickerUtils.initTimePicker(AddTicketFragment.this,
                        stringToTime(etDepartureTimeOfReturnTrip.getText().toString()), REQUEST_TIME_DEPARTURE_OF_RETURN_TRIP);
                break;
        }
    }

    private Date getMaxDate() {
        Date maxDate;
        if (businessTrip.getDelayDate() != null)
            maxDate = businessTrip.getDelayDate();
        else
            maxDate = businessTrip.getEndDate();
        return maxDate;
    }

    @OnItemSelected({R.id.spinner_ticket_type})
    public void spinnerItemSelected(Spinner spinner, int position) {
        btTicket.setTicketType(spinner.getSelectedItem().toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_DATE_DEPARTURE:
                    Date date = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    etDepartureDate.setText(ConvertTime.dateToString(date));
                    btTicket.setDepartureDate(date);
                    break;
                case REQUEST_DATE_DEPARTURE_OF_RETURN_TRIP:
                    dateReturn = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    etDepartureDateOfReturnTrip.setText(ConvertTime.dateToString(dateReturn));
                    break;
                case REQUEST_TIME_DEPARTURE:
                    Date time = (Date) data.getSerializableExtra(TimePickerDialog.EXTRA_TIME);
                    etDepartureTime.setText(ConvertTime.timeToString(time));
                    btTicket.setDepartureTime(time);
                    break;
                case REQUEST_TIME_DEPARTURE_OF_RETURN_TRIP:
                    timeReturn = (Date) data.getSerializableExtra(TimePickerDialog.EXTRA_TIME);
                    etDepartureTimeOfReturnTrip.setText(ConvertTime.timeToString(timeReturn));
                    break;

            }
        }
    }

    private void initSpinnerTicketType() {
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.tickets_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTicketType.setAdapter(adapter);
        sTicketType.setSelection(0);
    }

    private void initListeners() {
        acTvDestinationTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strEntered = s.toString();
                if (strEntered.length() > 2) {
//                    if (locationsList.isEmpty()) {
                    if (Utils.isNetworkAvailable(mContext))
                        getLocationsTo(strEntered);
                    else
                        Toast.makeText(mContext, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    locationsListTo.clear();
                    getDropLocationsToList(locationsListTo);
                }
//                String strEntered = s.toString();
//                if (strEntered.length() > 2) {
//                    if (locationsListTo.isEmpty())
//                        getLocationsTo(strEntered);
//                } else {
//                    locationsListTo.clear();
//                    getDropLocationsToList(locationsListTo);
//                }
//
//                filterLocationsListTo.clear();
//                for (BTLocation bsLocation : locationsListTo) {
//                    if (bsLocation.getCity().getName().toLowerCase().contains(strEntered.toLowerCase())) { //проверяем иммя на содержание нашего текста
//                        filterLocationsListTo.add(bsLocation); // добавляем объект в массив фильта
//                    }
//                }
//
//                if (filterLocationsListTo.isEmpty())  // если массив фильтра пустой
//                    getDropLocationsToList(locationsListTo); // выпадающий список с масивом полученным из сервера
//                else
//                    getDropLocationsToList(filterLocationsListTo); // выпадающий список с масивом фильтра
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        acTvDestinationTo.setOnItemClickListener((adapterView, view, i, l) -> {
            btTicket.setTo(locationsListTo.get(0)); // индекс 0, потому что при выборе он становится 1 в списке
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(acTvDestinationTo.getWindowToken(), 0);
            dropList = false;
        });

        acTvDestinationFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String strEntered = s.toString();
                if (strEntered.length() > 2) {
//                    if (locationsList.isEmpty()) {
                    if (Utils.isNetworkAvailable(mContext))
                        getLocationsFrom(strEntered);
                    else
                        Toast.makeText(mContext, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    locationsListFrom.clear();
                    getDropLocationsFromList(locationsListFrom);
                }

//                String strEntered = s.toString();
//                if (strEntered.length() > 2) {
//                    if (locationsListFrom.isEmpty())
//                        getLocationsFrom(strEntered);
//                } else {
//                    locationsListFrom.clear();
//                    getDropLocationsFromList(locationsListFrom);
//                }
//
//                filterLocationsListFrom.clear();
//                for (BTLocation bsLocation : locationsListFrom) {
//                    if (bsLocation.getCity().getName().toLowerCase().contains(strEntered.toLowerCase())) { //проверяем иммя на содержание нашего текста
//                        filterLocationsListFrom.add(bsLocation); // добавляем объект в массив фильта
//                    }
//                }
//
//                if (filterLocationsListFrom.isEmpty())  // если массив фильтра пустой
//                    getDropLocationsFromList(locationsListFrom); // выпадающий список с масивом полученным из сервера
//                else
//                    getDropLocationsFromList(filterLocationsListFrom); // выпадающий список с масивом фильтра
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        acTvDestinationFrom.setOnItemClickListener((adapterView, view, i, l) -> {
            btTicket.setFrom(locationsListFrom.get(0)); // индекс 0, потому что при выборе он становится 1 в списке
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(acTvDestinationFrom.getWindowToken(), 0);
            dropList = false;
        });
    }

    private void getLocationsTo(String name) {
        showSearchLocationsProgress(true, pdDestinationToSearch, ivDestinationToList);
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
                                    locationsListTo = response.body();
                                    if (dropList)
                                        getDropLocationsToList(locationsListTo);
                                    else
                                        dropList = true;
                                }
                                break;
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getContext())).logout();
                                break;
                        }
                        showSearchLocationsProgress(false, pdDestinationToSearch, ivDestinationToList);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BTLocation>> call, Throwable t) {
                        t.printStackTrace();
                        showSearchLocationsProgress(false, pdDestinationToSearch, ivDestinationToList);
                    }
                });
    }

    private void getDropLocationsToList(List<BTLocation> locationsList) {
        BTLocationsAdapter adapter = new BTLocationsAdapter(mContext, R.layout.item_bs_location, locationsList);
        acTvDestinationTo.setThreshold(1);//will start working from first characte
        acTvDestinationTo.setAdapter(adapter);//setting the adapter data into the//
        acTvDestinationTo.showDropDown();
    }

    private void getLocationsFrom(String name) {
        showSearchLocationsProgress(true, pdDestinationFromSearch, ivDestinationFromList);
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
                                    locationsListFrom = response.body();
                                    if (dropList)
                                        getDropLocationsFromList(locationsListFrom);
                                    else
                                        dropList = true;
                                }
                                break;
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getContext())).logout();
                                break;
                        }
                        showSearchLocationsProgress(false, pdDestinationFromSearch, ivDestinationFromList);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BTLocation>> call, Throwable t) {
                        t.printStackTrace();
                        showSearchLocationsProgress(false, pdDestinationFromSearch, ivDestinationFromList);
                    }
                });
    }

    private void getDropLocationsFromList(List<BTLocation> locationsList) {
        BTLocationsAdapter adapter = new BTLocationsAdapter(mContext, R.layout.item_bs_location, locationsList);
        acTvDestinationFrom.setThreshold(1);//will start working from first characte
        acTvDestinationFrom.setAdapter(adapter);//setting the adapter data into the//
        acTvDestinationFrom.showDropDown();
    }

    private void showSearchLocationsProgress(Boolean show, ProgressBar progressBar, ImageView imageView) {
        if (progressBar != null && imageView != null) {
            if (show) {
                progressBar.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void saveTicket() {
        if (checkAllInfo()) {
            btTicket.setComment(etComment.getText().toString());
            if (newTicket) {
                businessTrip.getTickets().add(btTicket);
                if (cbReturnTrip.isChecked())
                    addReturnTrip();
            }
            (Objects.requireNonNull(getActivity())).onBackPressed();
        }
    }

    private void addReturnTrip() {
        BTTicket returnTicket = new BTTicket();
        returnTicket.setTicketType(btTicket.getTicketType());
        returnTicket.setDepartureDate(dateReturn);
        returnTicket.setDepartureTime(timeReturn);
        returnTicket.setTo(btTicket.getFrom());
        returnTicket.setFrom(btTicket.getTo());
        returnTicket.setComment(etComment.getText().toString());
        businessTrip.getTickets().add(returnTicket);
    }

    private Boolean checkAllInfo() {
        if (null == btTicket.getDepartureDate()) {
            Toast.makeText(mContext, mContext.getString(R.string.empty_departure_date), Toast.LENGTH_LONG).show();
            return false;
        }
        if (null == btTicket.getFrom()) {
            Toast.makeText(mContext, mContext.getString(R.string.empty_destination_from), Toast.LENGTH_LONG).show();
            return false;
        }
        if (null == btTicket.getTo()) {
            Toast.makeText(mContext, mContext.getString(R.string.empty_destination_to), Toast.LENGTH_LONG).show();
            return false;
        }
        if (cbReturnTrip.isChecked() && null == dateReturn) {
            Toast.makeText(mContext, mContext.getString(R.string.empty_departure_return_date), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void initDeleteButton() {
        if (newTicket)
            btnDelete.setVisibility(View.GONE);
    }

    private void deleteTicket() {
        showDeleteDialog();
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("Так", (dialog, which) -> {
            businessTrip.getTickets().remove(btTicket);
            (Objects.requireNonNull(getActivity())).onBackPressed();
        });
        builder.setNegativeButton("Ні", (dialog, which) -> {
        });
        builder.setMessage(Objects.requireNonNull(getContext()).getString(R.string.text_ticket_delete));
        AlertDialog alert = builder.create();
        alert.show();
    }

}