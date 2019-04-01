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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.business_trips.BT;
import com.dtek.portal.models.business_trips.BTHotel;
import com.dtek.portal.models.business_trips.BTLocation;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.businees_trips.BTLocationsAdapter;
import com.dtek.portal.ui.dialog.DatePickerDialog;
import com.dtek.portal.ui.dialog.TimePickerDialog;
import com.dtek.portal.utils.BusinessTrip;
import com.dtek.portal.utils.ConvertTime;
import com.dtek.portal.utils.PickerUtils;
import com.dtek.portal.utils.Utils;

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
import static com.dtek.portal.Const.BusinessTrips.BT_GET_LOCATIONS;
import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.utils.ConvertTime.stringToDate;
import static com.dtek.portal.utils.ConvertTime.stringToTime;

public class AddHotelFragment extends Fragment {

    private BT businessTrip;
    private BTHotel btHotel;
    private Context mContext;
    private Boolean newHotel = true;

    private static final int REQUEST_ARRIVAL_DATE = 1;
    private static final int REQUEST_EARLY_ARRIVAL_TIME = 2;
    private static final int REQUEST_DATE_DEPARTURE = 3;
    private static final int REQUEST_LATE_DEPARTURE_TIME = 4;


    private List<BTLocation> locationsList = new ArrayList<>();
    private List<BTLocation> filterLocationsList = new ArrayList<>();
    private Unbinder unbinder;

    private boolean dropList = true;

    @BindView(R.id.et_arrival_date)
    EditText etArrivalDate;
    @BindView(R.id.cb_early_arrival)
    CheckBox cbEarlyArrival;
    @BindView(R.id.early_arrival_layout)
    LinearLayout earlyArrivalLayout;
    @BindView(R.id.et_early_arrival_time)
    EditText etEarlyArrivalTime;
    @BindView(R.id.et_date_of_departure)
    EditText etDateOfDeparture;
    @BindView(R.id.cb_late_departure)
    CheckBox cbLateDeparture;
    @BindView(R.id.late_departure_layout)
    LinearLayout lateDepartureLayout;
    @BindView(R.id.et_late_departure_time)
    EditText etLateDepartureTime;

    @BindView(R.id.ac_tv_destination)
    AutoCompleteTextView acTvDestination;
    @BindView(R.id.iv_destination_list)
    ImageView ivDestinationsList;
    @BindView(R.id.pb_destination_search)
    ProgressBar pbDestinationsSearch;

    @BindView(R.id.et_comments)
    EditText etComment;
    @BindView(R.id.btn_delete)
    Button btnDelete;


    public AddHotelFragment() {
    }

    @SuppressLint("ValidFragment")
    public AddHotelFragment(BTHotel btHotel) {
        this.btHotel = btHotel;
        newHotel = false;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_hotel_add, container, false);
        unbinder = ButterKnife.bind(this, v);
        businessTrip = BusinessTrip.getInstance().getBusinessTrip();
        mContext = v.getContext();


        initDeleteButton();
        initHotel();
        initListeners();
        return v;
    }

    private void initHotel() {
        if (null == btHotel) {
            btHotel = new BTHotel();
            initDates();
        } else
            loadHotelData();
    }

    private void initDates() {
        btHotel.setArrivalDate(businessTrip.getStartDate());
        btHotel.setDepartureDate(businessTrip.getEndDate());
        etArrivalDate.setText(ConvertTime.dateToString(btHotel.getArrivalDate()));
        etDateOfDeparture.setText(ConvertTime.dateToString(btHotel.getDepartureDate()));
    }

    private void loadHotelData() {
        btnDelete.setVisibility(View.VISIBLE);
        acTvDestination.setText(btHotel.getLocation().getCity().getName());
        cbEarlyArrival.setChecked(btHotel.getEarlyCheckIn());
        cbLateDeparture.setChecked(btHotel.getLateCheckOut());
        etArrivalDate.setText(ConvertTime.dateToString(btHotel.getArrivalDate()));
        etDateOfDeparture.setText(ConvertTime.dateToString(btHotel.getDepartureDate()));
        if (cbEarlyArrival.isChecked()) {
            earlyArrivalLayout.setVisibility(View.VISIBLE);
            if (btHotel.getArrivalTime() != null)
                etEarlyArrivalTime.setText(ConvertTime.timeToString(btHotel.getArrivalTime()));
        }
        if (cbLateDeparture.isChecked()) {
            lateDepartureLayout.setVisibility(View.VISIBLE);
            if (btHotel.getDepartureTime() != null)
                etLateDepartureTime.setText(ConvertTime.timeToString(btHotel.getDepartureTime()));
        }
        etComment.setText(btHotel.getComment());

    }


    private void initListeners() {
        acTvDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strEntered = s.toString();
                if (strEntered.length() > 2) {
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

//                String strEntered = s.toString();
//                if (strEntered.length() > 2) {
//                    if (locationsList.isEmpty())
//                        getLocations(strEntered);
//                } else {
//                    locationsList.clear();
//                    getDropLocationsList(locationsList);
//                }
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

        acTvDestination.setOnItemClickListener((adapterView, view, i, l) -> {
            btHotel.setLocation(locationsList.get(0)); // индекс 0, потому что при выборе он становится 1 в списке
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(acTvDestination.getWindowToken(), 0);
            acTvDestination.clearFocus();
            dropList = false;
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


    @OnClick({R.id.cb_early_arrival, R.id.cb_late_departure, R.id.btn_save, R.id.btn_delete})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_early_arrival:
                if (cbEarlyArrival.isChecked())
                    earlyArrivalLayout.setVisibility(View.VISIBLE);
                else
                    earlyArrivalLayout.setVisibility(View.GONE);
                break;
            case R.id.cb_late_departure:
                if (cbLateDeparture.isChecked())
                    lateDepartureLayout.setVisibility(View.VISIBLE);
                else
                    lateDepartureLayout.setVisibility(View.GONE);
                break;
            case R.id.btn_save:
                saveHotel();
                break;
            case R.id.btn_delete:
                deleteHotel();
                break;
        }
    }

    private void getDropLocationsList(List<BTLocation> locationsList) {
        BTLocationsAdapter adapter = new BTLocationsAdapter(mContext, R.layout.item_bs_location, locationsList);
        acTvDestination.setThreshold(1);//will start working from first characte
        acTvDestination.setAdapter(adapter);//setting the adapter data into the//
        acTvDestination.showDropDown();
    }

    @OnClick({R.id.et_arrival_date, R.id.et_early_arrival_time, R.id.et_date_of_departure, R.id.et_late_departure_time})
    void onDateTimeClick(View view) {
        switch (view.getId()) {
            case R.id.et_arrival_date:
                PickerUtils.initDatePicker(AddHotelFragment.this,
                        businessTrip.getStartDate(), businessTrip.getStartDate(), businessTrip.getEndDate(), REQUEST_ARRIVAL_DATE);
                break;
            case R.id.et_early_arrival_time:
                PickerUtils.initTimePicker(AddHotelFragment.this,
                        stringToTime(etEarlyArrivalTime.getText().toString()), REQUEST_EARLY_ARRIVAL_TIME);
                break;
            case R.id.et_date_of_departure:
                PickerUtils.initDatePicker(AddHotelFragment.this,
                        stringToDate(etArrivalDate.getText().toString()), btHotel.getArrivalDate(), businessTrip.getEndDate(), REQUEST_DATE_DEPARTURE);
                break;
            case R.id.et_late_departure_time:
                PickerUtils.initTimePicker(AddHotelFragment.this,
                        stringToTime(etLateDepartureTime.getText().toString()), REQUEST_LATE_DEPARTURE_TIME);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ARRIVAL_DATE:
                    Date date = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    etArrivalDate.setText(ConvertTime.dateToString(date));
                    btHotel.setArrivalDate(date);
                    break;
                case REQUEST_EARLY_ARRIVAL_TIME:
                    Date time = (Date) data.getSerializableExtra(TimePickerDialog.EXTRA_TIME);
                    etEarlyArrivalTime.setText(ConvertTime.timeToString(time));
                    btHotel.setArrivalTime(time);
                    break;
                case REQUEST_DATE_DEPARTURE:
                    Date dateDeparture = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    etDateOfDeparture.setText(ConvertTime.dateToString(dateDeparture));
                    btHotel.setDepartureDate(dateDeparture);
                    break;
                case REQUEST_LATE_DEPARTURE_TIME:
                    Date timeLate = (Date) data.getSerializableExtra(TimePickerDialog.EXTRA_TIME);
                    etLateDepartureTime.setText(ConvertTime.timeToString(timeLate));
                    btHotel.setDepartureTime(timeLate);
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void saveHotel() {
        if (checkAllInfo()) {
            btHotel.setComment(etComment.getText().toString());
            btHotel.setEarlyCheckIn(cbEarlyArrival.isChecked());
            btHotel.setLateCheckOut(cbLateDeparture.isChecked());
            if (newHotel) {
                businessTrip.getHotels().add(btHotel);
            }
            (Objects.requireNonNull(getActivity())).onBackPressed();
        }
    }

    private Boolean checkAllInfo() {
        if (null == btHotel.getLocation()) {
            Toast.makeText(mContext, mContext.getString(R.string.empty_destination), Toast.LENGTH_LONG).show();
            return false;
        }
        if (null == btHotel.getArrivalDate()) {
            Toast.makeText(mContext, mContext.getString(R.string.empty_arrival_date), Toast.LENGTH_LONG).show();
            return false;
        }
        if (null == btHotel.getDepartureDate()) {
            Toast.makeText(mContext, mContext.getString(R.string.empty_date_of_departure), Toast.LENGTH_LONG).show();
            return false;
        }

        if (btHotel.getArrivalDate().after(btHotel.getDepartureDate())) {
            Toast.makeText(mContext, mContext.getString(R.string.arrival_date_after_departure_date), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void initDeleteButton() {
        if (newHotel)
            btnDelete.setVisibility(View.GONE);
    }

    private void deleteHotel() {
        showDeleteDialog();
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("Так", (dialog, which) -> {
            businessTrip.getHotels().remove(btHotel);
            (Objects.requireNonNull(getActivity())).onBackPressed();
        });
        builder.setNegativeButton("Ні", (dialog, which) -> {
        });
        builder.setMessage(Objects.requireNonNull(getContext()).getString(R.string.text_hotel_delete));
        AlertDialog alert = builder.create();
        alert.show();
    }
}
