package com.dtek.portal.ui.fragment.car;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.car.CarOrder;
import com.dtek.portal.models.car.Passenger;
import com.dtek.portal.models.car.Point;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.car.CarPassengerAdapter;
import com.dtek.portal.ui.adapter.car.CarPointAdapter;
import com.dtek.portal.ui.adapter.car.CarSpinnerAdapter;
import com.dtek.portal.ui.dialog.DatePickerDialog;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.ui.dialog.TimePickerDialog;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.utils.ConvertTime;
import com.dtek.portal.utils.PickerUtils;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.API_CAR_ITEM;
import static com.dtek.portal.Const.HTTP.CAR_CANCEL;
import static com.dtek.portal.Const.HTTP.CAR_RATING;
import static com.dtek.portal.ui.adapter.car.BaseCarAdapter.STATE_ASSIGNED;
import static com.dtek.portal.ui.adapter.car.BaseCarAdapter.STATE_CANCELED;
import static com.dtek.portal.ui.adapter.car.BaseCarAdapter.STATE_CLOSED;
import static com.dtek.portal.ui.adapter.car.BaseCarAdapter.STATE_DRAFT;
import static com.dtek.portal.ui.adapter.car.BaseCarAdapter.STATE_IN_WORK;
import static com.dtek.portal.ui.adapter.car.BaseCarAdapter.STATE_RATING;
import static com.dtek.portal.ui.adapter.car.CarPointAdapter.TAKE_AWAY;
import static com.dtek.portal.ui.adapter.car.CarPointAdapter.WAIT;
import static com.dtek.portal.utils.ConvertTime.stringToDate;
import static com.dtek.portal.utils.ConvertTime.stringToTime;

public class AddDetailCarFragment extends Fragment
        implements CarPassengerAdapter.PassengerListener, CarPointAdapter.PointListener {

    private static final String TAG = "AddDetailCarFragment";
    private static final String ARG_ORDER_CAR = "order_car";
    private static final int REQUEST_DATE_START = 1; // константа для кода запроса
    private static final int REQUEST_TIME_START = 2; // константа для кода запроса
    private static final int REQUEST_DATE_END = 3; // константа для кода запроса
    private static final int REQUEST_TIME_END = 4; // константа для кода запроса
    private static final int REQUEST_CODE_LOGIN = 101;

    private Context mContext;
    private Unbinder unbinder;
    private ProgressDialog waitDialog;
    @BindView(R.id.ll_all)
    LinearLayout mLlAll;
    //шапка заявки
    @BindView(R.id.ll_header)
    LinearLayout mLlHeader;
    @BindView(R.id.tv_numb_order)
    TextView mTvNumbOrder;
    @BindView(R.id.tv_status_order)
    TextView mTvStatusOrder;
    @BindView(R.id.tv_date_order)
    TextView mTvDateOrder;
    @BindView(R.id.ibtn_car_now)
    ImageButton mIbtnCarNow;
    //общая информация
    @BindView(R.id.spinner_city)
    Spinner mSpinnerСity;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.spinner_direction)
    Spinner mSpinnerDirection;
    //Транспорт предоставить для
    @BindView(R.id.switch_me)
    Switch mSwitchMe;
    @BindView(R.id.switch_other)
    Switch mSwitchOther;
    @BindView(R.id.ll_numb_passenger)
    LinearLayout mLlNumbPassenger;
    @BindView(R.id.et_numb_passenger)
    EditText mEtNumbPassenger;
    @BindView(R.id.btn_add_passenger)
    Button mBtnAddPassenger;
    @BindView(R.id.rv_passenger)
    RecyclerView mRvPassenger;
    private CarPassengerAdapter mAdapterPassenger;
    private int mCountAllPassenger; // количество всех пассажиров
    private int countNumbPassenger = 1; // порядковый номер списка пассажиров
    private List<Passenger> mPassengers; // список пассажиров без инициатора
    //Маршрут
    @BindView(R.id.et_point_start)
    EditText mEtPointStart;
    @BindView(R.id.et_date_start)
    EditText mEtDateStart;
    @BindView(R.id.et_time_start)
    EditText mEtTimeStart;
    @BindView(R.id.et_point_end)
    EditText mEtPointEnd;
    @BindView(R.id.et_date_end)
    EditText mEtDateEnd;
    @BindView(R.id.et_time_end)
    EditText mEtTimeEnd;
    @BindView(R.id.btn_add_point)
    Button mBtnAddPoint;
    @BindView(R.id.rv_point)
    RecyclerView mRvPoint;
    private CarPointAdapter mAdapterPoint;
    private int countNumbPoint = 1; // порядковый номер списка пунктов
    private List<Point> mPoints; // список пассажиров без инициатора
    //Примечания
    @BindView(R.id.et_comment)
    EditText mEtComment;
    //Водитель
    @BindView(R.id.ll_driver)
    LinearLayout mLlDriver;
    @BindView(R.id.tv_driver)
    TextView mTvDriver;
    @BindView(R.id.tv_car)
    TextView mTvCar;
    @BindView(R.id.tv_comment)
    TextView mTvComment;
    //Оценка
    @BindView(R.id.ll_rating)
    LinearLayout mLlRating;
    @BindView(R.id.spinner_rating_car)
    Spinner mSpinnerRating;
    @BindView(R.id.et_comment_rating)
    EditText mEtCommentRating;
    @BindView(R.id.btn_draft)
    Button mBtnDraft;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.btn_revoke)
    Button mBtnCancel;
    @BindView(R.id.btn_rating)
    Button mBtnRating;
    private CarOrder mCarOrder;

    public static AddDetailCarFragment newInstance(CarOrder carOrder) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_ORDER_CAR, carOrder);

        AddDetailCarFragment fragment = new AddDetailCarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AddDetailCarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mCarOrder = new CarOrder();
        mPassengers = new ArrayList<>();
        mPoints = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_car_add, container, false);
        unbinder = ButterKnife.bind(this, v);

        initDialog(); // диалоги
        getOrderId(); // перадача объекта со списка
        getInfoGeneral(); // общая информация
        initPassenger(); // пасажиры
        initPoint(); // маршрут
        return v;
    }

    private void initDialog() {
        waitDialog = WaitDialog.showDialog(mContext, getString(R.string.dialog_wait));
    }

    private void getOrderId() { // перадача объекта со списка
        mCarOrder = new CarOrder(Parcel.obtain());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mCarOrder = bundle.getParcelable(ARG_ORDER_CAR);
            if (Utils.isNetworkAvailable(mContext)) {
                loadOrderId(mCarOrder.getId());
            } else {
                Toast.makeText(mContext, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadOrderId(int idOrder) { // загрузка заявки по id
        mLlAll.setVisibility(View.GONE);
        waitDialog.show();
        RestManager.getApi()
                .getOrderCarId(mapHeader(), API_CAR_ITEM + idOrder)
                .enqueue(new Callback<CarOrder>() {
                    @Override
                    public void onResponse(@NonNull Call<CarOrder> call, @NonNull Response<CarOrder> response) {
                        errorToken(response);
                        if (response.isSuccessful() && response.body() != null) {
                            mCarOrder = response.body();
                            setOrderContent();
                            mLlAll.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(mContext, "response.isSuccessful() = " + response.isSuccessful(), Toast.LENGTH_SHORT).show();
                        }
                        waitDialog.dismiss();
                    }
                    @Override
                    public void onFailure(@NonNull Call<CarOrder> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        Log.d("onFailure", t.toString());
                    }
                });
    }

    // наполнение контента заявки с инета
    private void setOrderContent() {
        setOrderHeader();
        setInfoGeneral();
        setPassenger();
        setPoint();
        setComment(); // примечание заявки

        setViewOfStatus();
    }

    private void setViewOfStatus() {
        if (!mCarOrder.getState().equals(STATE_DRAFT)) {
            statusNotDraft();
        }
        if (mCarOrder.getState().equals(STATE_CANCELED) || mCarOrder.getState().equals(STATE_RATING) || mCarOrder.getState().equals(STATE_CLOSED)) {
            mBtnCancel.setVisibility(View.GONE);
        }
        if (mCarOrder.getState().equals(STATE_IN_WORK) || mCarOrder.getState().equals(STATE_ASSIGNED) || mCarOrder.getState().equals(STATE_RATING) ||
                mCarOrder.getState().equals(STATE_CANCELED) || mCarOrder.getState().equals(STATE_CLOSED)) {
            setDriver();
        }
        if (mCarOrder.getState().equals(STATE_RATING) || mCarOrder.getState().equals(STATE_CLOSED)) {
            statusRating();
        }
        if (mCarOrder.getState().equals(STATE_CLOSED)) {
            statusClosed();
        }
    }

    private void statusNotDraft() {
        mSpinnerСity.setEnabled(false);
        mSpinnerСity.setClickable(false);
        mEtPhone.setEnabled(false);
        mEtPhone.setClickable(false);
        mSpinnerDirection.setEnabled(false);
        mSpinnerDirection.setClickable(false);
        mSwitchMe.setEnabled(false);
        mSwitchMe.setClickable(false);
        mSwitchOther.setEnabled(false);
        mSwitchOther.setClickable(false);
        mEtNumbPassenger.setEnabled(false);
        mEtNumbPassenger.setClickable(false);
        mEtPointStart.setEnabled(false);
        mEtPointStart.setClickable(false);
        mEtPointEnd.setEnabled(false);
        mEtPointEnd.setClickable(false);
        mEtDateStart.setEnabled(false);
        mEtDateStart.setClickable(false);
        mEtTimeStart.setEnabled(false);
        mEtTimeStart.setClickable(false);
        mEtDateEnd.setEnabled(false);
        mEtDateEnd.setClickable(false);
        mEtTimeEnd.setEnabled(false);
        mEtTimeEnd.setClickable(false);
        mEtComment.setEnabled(false);
        mEtComment.setClickable(false);
        mRvPassenger.addOnItemTouchListener(disableRecyclerListener());
        mRvPoint.addOnItemTouchListener(disableRecyclerListener());
        mBtnAddPassenger.setEnabled(false);
        mBtnAddPassenger.setClickable(false);
        mBtnAddPoint.setEnabled(false);
        mBtnAddPoint.setClickable(false);
        mBtnDraft.setVisibility(View.GONE);
        mBtnConfirm.setVisibility(View.GONE);
        mBtnCancel.setVisibility(View.VISIBLE);
    }

    private void setDriver() {
        mLlDriver.setVisibility(View.VISIBLE);
        mTvDriver.setText(mCarOrder.getDriver());
        mTvCar.setText(mCarOrder.getCar());
        mTvComment.setText(mCarOrder.getNotes());
    }

    private void statusRating() {
        mLlRating.setVisibility(View.VISIBLE);

        String[] spinnerRating = getResources().getStringArray(R.array.rating_car);
        CarSpinnerAdapter adapter = new CarSpinnerAdapter(mContext, R.layout.item_spinner);
        adapter.addAll(spinnerRating);
        adapter.add(getString(R.string.text_select_rating));
        mSpinnerRating.setAdapter(adapter);
        mSpinnerRating.setSelection(adapter.getCount());
        switch (mCarOrder.getRating()) {
            case "4":
                mSpinnerRating.setSelection(0);
                break;
            case "3":
                mSpinnerRating.setSelection(1);
                break;
            case "2":
                mSpinnerRating.setSelection(2);
                break;
            case "1":
                mSpinnerRating.setSelection(3);
                break;
        }
        mEtCommentRating.setText(mCarOrder.getRatingComment());
        mBtnRating.setVisibility(View.VISIBLE);
    }

    private void statusClosed() {
        mBtnRating.setVisibility(View.GONE);
        mLlRating.setVisibility(View.VISIBLE);
        mSpinnerRating.setEnabled(false);
        mSpinnerRating.setClickable(false);
        mEtCommentRating.setEnabled(false);
        mEtCommentRating.setClickable(false);
    }

    // наполнение хедера
    private void setOrderHeader() {
        if (mCarOrder.getState() != null) { // проверка для просмотра
            mLlHeader.setVisibility(View.VISIBLE);
            mTvNumbOrder.setText(mCarOrder.getTitle());
            mTvStatusOrder.setText(String.format("%s: %s", getString(R.string.text_status), mCarOrder.getState()));
            mTvDateOrder.setText(String.format("%s: %s", getResources().getText(R.string.text_data_create),
                    ConvertTime.formatDateTime(mCarOrder.getCreatedTime())));
        }
    }

    private void setInfoGeneral() {
        ArrayAdapter<CharSequence> adapterCity = ArrayAdapter.createFromResource(mContext,
                R.array.cities, android.R.layout.simple_spinner_item);
        String orderCity = mCarOrder.getOrderCity();
        int position = adapterCity.getPosition(orderCity);
        mSpinnerСity.setSelection(position);

        mEtPhone.setText(mCarOrder.getContactPhone());

        ArrayAdapter<CharSequence> adapterDirection = ArrayAdapter.createFromResource(mContext,
                R.array.direction, android.R.layout.simple_spinner_item);
        mSpinnerDirection.setSelection(adapterDirection.getPosition(mCarOrder.getDirection()));
    }

    private void setPassenger() {
        mSwitchMe.setChecked(mCarOrder.isMePassenger());
        mSwitchOther.setChecked(mCarOrder.isOtherPassenger());
        mEtNumbPassenger.setText(String.valueOf(mCarOrder.getNumPassengers())); // общ. кол-во пассажиров

        if (!mCarOrder.getPassengerList().isEmpty()) { // если пассажиров > 0
            mLlNumbPassenger.setVisibility(View.VISIBLE); // отображаем список RecyclerView
            for (Passenger passenger : mCarOrder.getPassengerList()) { // пробегаемся по списку
                passenger.setNumber(countNumbPassenger++);
                if (passenger.getPhone() == null) {
                    passenger.setPhone("");
                }
                if (passenger.getComment() == null) {
                    passenger.setComment("");
                }
                addPassenger(passenger); // добавляем пассажиров в массив и список RecyclerView
            }
        }
    }

    private void setPoint() {
        mEtPointStart.setText(mCarOrder.getStartPoint()); // пункт отправления
        mEtDateStart.setText(ConvertTime.formatDate(mCarOrder.getDateStart()));
        mEtTimeStart.setText(ConvertTime.formatTime(mCarOrder.getDateStart()));
        mEtPointEnd.setText(mCarOrder.getStopPoint()); // пункт прибытия
        mEtDateEnd.setText(ConvertTime.formatDate(mCarOrder.getDateFinish()));
        mEtTimeEnd.setText(ConvertTime.formatTime(mCarOrder.getDateFinish()));

        if (!mCarOrder.getPointList().isEmpty()) { // доп пункты есть?
            for (Point point : mCarOrder.getPointList()) { // пробегаемся по списку
                point.setNumber(countNumbPoint++);
                if (point.getWaitMinutes() == null) {
                    point.setWaitMinutes("");
                }
                if (point.getDateTakeAway() == 0) {
                    point.setDateTakeAway(point.getDatePoint());
                }
                addPoint(point); // добавляем пассажиров в массив и список RecyclerView
            }
        }
    }

    private void setComment() {
        mEtComment.setText(mCarOrder.getUsersComments());
    }

    @NonNull // отключаем клики по RecyclerView
    private RecyclerView.OnItemTouchListener disableRecyclerListener() {
        return new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return true;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        };
    }

    private void getInfoGeneral() {
// Город заказа
        String[] spinnerCities = getResources().getStringArray(R.array.cities);
        CarSpinnerAdapter adapterCity = new CarSpinnerAdapter(mContext, R.layout.item_spinner_36);
        adapterCity.addAll(spinnerCities);
        adapterCity.add("");
        mSpinnerСity.setAdapter(adapterCity);
        mSpinnerСity.setSelection(adapterCity.getCount());
// Инициатор заявки
        mEtPhone.setText(String.format("+%s", PreferenceUtils.getUserPhone()));
// Направление
        String[] spinnerDirection = getResources().getStringArray(R.array.direction);
        CarSpinnerAdapter adapterDirect = new CarSpinnerAdapter(mContext, android.R.layout.simple_spinner_dropdown_item);
        adapterDirect.addAll(spinnerDirection);
        adapterDirect.add("");
        mSpinnerDirection.setAdapter(adapterDirect);
        mSpinnerDirection.setSelection(adapterDirect.getCount());
    }

    // пассажиры
    private void initPassenger() {
        mCountAllPassenger = 1;
// используем linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRvPassenger.setLayoutManager(mLayoutManager);
// создаем адаптер
        mAdapterPassenger = new CarPassengerAdapter(mContext, mPassengers);
        mAdapterPassenger.setPassengerListener(this);
        mRvPassenger.setAdapter(mAdapterPassenger);
// количество пассажиров
        mEtNumbPassenger.setText(String.valueOf(mCountAllPassenger));
    }

    // switch-еры транспорт для
    @OnCheckedChanged({R.id.switch_me, R.id.switch_other})
    void onCheckedFor(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_me:
                if (isChecked) {
                    mCountAllPassenger++;
                } else {
                    mCountAllPassenger--;
                }
                mEtNumbPassenger.setText(String.valueOf(mCountAllPassenger));
                break;
            case R.id.switch_other:
                if (isChecked) {
                    mLlNumbPassenger.setVisibility(View.VISIBLE);
                } else {
                    mLlNumbPassenger.setVisibility(View.GONE);
                    mCountAllPassenger = mCountAllPassenger - mPassengers.size();
                    mPassengers.clear();
                    mAdapterPassenger.notifyDataSetChanged();
                }
                break;
        }
    }

    // добавление пассажиров
    @SuppressLint("LongLogTag")
    @OnClick(R.id.btn_add_passenger)
    void onAddPassengerClick(Button view) {
        Log.i(TAG, "onAddClick: " + mPassengers.size());
        addPassenger(newPassenger());
        Log.i(TAG, "onAddClick: " + mPassengers.size());
    }

    private Passenger newPassenger() { // новый пассажир по default
        int position = mPassengers.size(); // позиция в листе
        countNumbPassenger = position + 1; // порядковый номер списка пассажиров
        String type = "";
        String foi = "";
        boolean sms = false;
        String phone = "";
        String comment = "";
        Passenger passenger = new Passenger(countNumbPassenger, type, foi, sms, phone, comment);
        return passenger;
    }

    @SuppressLint("LongLogTag")
    private void addPassenger(Passenger passenger) { // добавляем пассажира в список
        mCountAllPassenger++;
        mPassengers.add(passenger);
        mAdapterPassenger.updatePassengerListItems(mPassengers); // обновляем данные в списках
        mRvPassenger.scrollToPosition(mPassengers.size());
        mEtNumbPassenger.setText(String.valueOf(mCountAllPassenger));
//        Log.i(TAG, "addPassenger: " + mPassengers.size());
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onRemovePassenger(int position) { // удаляем пассажира Callback
        mAdapterPassenger.removeItem(position);
        mAdapterPassenger.updateListFragment(mPassengers);
        mCountAllPassenger--;
        mEtNumbPassenger.setText(String.valueOf(mCountAllPassenger));
        if (mPassengers.size() == 0) { // если пассажиры все удалены, то выключаем свитчер
            mSwitchOther.setChecked(false);
        }
        Log.i(TAG, "onRemoveClick: " + mPassengers.size());
    }

    // маршрут
    private void initPoint() {
//даты по умолчанию
        mEtDateStart.setText(ConvertTime.formatDate(System.currentTimeMillis() / 1000L));
        mEtTimeStart.setText("09:00");
        mEtDateEnd.setText(ConvertTime.formatDate(Calendar.getInstance().getTimeInMillis() / 1000));
        mEtTimeEnd.setText("10:00");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRvPoint.setLayoutManager(layoutManager);
        // создаем адаптер
        mAdapterPoint = new CarPointAdapter(mContext, mPoints);
        mAdapterPoint.setPointListener(this);
        mRvPoint.setAdapter(mAdapterPoint);
    }

    @OnClick({R.id.et_date_start, R.id.et_time_start, R.id.et_date_end, R.id.et_time_end})
    void onDateClick(View view) {
        switch (view.getId()) {
            case R.id.et_date_start:
                PickerUtils.initDatePicker(AddDetailCarFragment.this,
                        stringToDate(mEtDateStart.getText().toString()), new Date(), null, REQUEST_DATE_START);
                break;
            case R.id.et_time_start:
                PickerUtils.initTimePicker(AddDetailCarFragment.this,
                        stringToTime(mEtTimeStart.getText().toString()), REQUEST_TIME_START);
                break;
            case R.id.et_date_end:
                PickerUtils.initDatePicker(AddDetailCarFragment.this,
                        stringToDate(mEtDateEnd.getText().toString()), new Date(), null, REQUEST_DATE_END);
                break;
            case R.id.et_time_end:
                PickerUtils.initTimePicker(AddDetailCarFragment.this,
                        stringToTime(mEtTimeEnd.getText().toString()), REQUEST_TIME_END);
                break;
        }
    }

    // добавление пассажиров
    @SuppressLint("LongLogTag")
    @OnClick(R.id.btn_add_point)
    void onAddPointClick(Button view) {
        Log.i(TAG, "onAddClick: " + mPoints.size());
        addPoint(newPoint());
        Log.i(TAG, "onAddClick: " + mPoints.size());
    }

    private Point newPoint() { // новый пункт по default
        int position = mPoints.size(); // позиция в листе
        countNumbPoint = position + 1; // порядковый номер списка пассажиров
        String address = "";
        long datePoint = ConvertTime.convertDateToUnix(mEtDateStart.getText().toString(),
                mEtTimeStart.getText().toString());
        String waitOrTakeAway = WAIT;
        String timeWait = "05 мин";
        long dateTakeAway = ConvertTime.convertDateToUnix(mEtDateStart.getText().toString(),
                mEtTimeStart.getText().toString());
        Point point = new Point(countNumbPoint, address, datePoint, waitOrTakeAway, timeWait, dateTakeAway);
        return point;
    }

    @SuppressLint("LongLogTag")
    private void addPoint(Point point) { // добавляем пункты в список
        mPoints.add(point);
        mAdapterPoint.updatePointListItems(mPoints); // обновляем данные в списках
        mRvPoint.scrollToPosition(mPoints.size());
//        Log.i(TAG, "addPassenger: " + mPassengers.size());
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onRemovePoint(int position) { // удаляем доп.пункт
        mAdapterPoint.removeItem(position);
        mAdapterPoint.updateListFragment(mPoints);
//        Log.i(TAG, "onRemoveClick: " + mPoints.size());
    }

    // кнопки - манипуляции с заявками
    @OnClick({R.id.btn_draft, R.id.btn_confirm, R.id.btn_revoke, R.id.btn_rating})
    void onButtonClick(View v) {
        setOrderCar();// Наполняем поля модели данными из View-шек
        if (Utils.isNetworkAvailable(mContext)) {
            switch (v.getId()) {
                case R.id.btn_draft:
                    if (mCarOrder.getState() != null && mCarOrder.getState().equals(STATE_DRAFT)) {
                        mCarOrder.setState(STATE_DRAFT); // статус
                        updateOrder(STATE_DRAFT); // обновление
                    } else {
                        mCarOrder.setState(STATE_DRAFT); // статус
                        sendOrder(STATE_DRAFT);
                    }
                    break;
                case R.id.btn_confirm:
                    if (isValidation()) { //валидация формы
//                        Toast.makeText(mContext, "Валидация пройдена", Toast.LENGTH_SHORT).show();
                        if (mCarOrder.getState() != null && mCarOrder.getState().equals(STATE_DRAFT)) {
                            mCarOrder.setState(STATE_IN_WORK); // статус
                            updateOrder(STATE_IN_WORK); // обновление
                        } else {
                            mCarOrder.setState(STATE_IN_WORK); // статус
                            sendOrder(STATE_IN_WORK);
                        }
                    }
                    break;
                case R.id.btn_revoke:
                    cancelOrder(CAR_CANCEL);
                    break;
                case R.id.btn_rating:
                    if (isValidationRating()) {
//                        Toast.makeText(mContext, "Валидация пройдена", Toast.LENGTH_SHORT).show();
                        ratingOrder();
                    }
                    break;
            }
        } else {
            Snackbar.make(v, R.string.error_msg_no_internet, Snackbar.LENGTH_LONG).show();
        }

    }

    @NonNull
    private Map<String, String> mapHeader() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        return map;
    }

    private void errorToken(@NonNull Response<CarOrder> response) {
        RestManager.printResponseLog(response);
        switch (response.code()) {
            case 400:
            case 401:
                ((MainActivity) Objects.requireNonNull(getActivity())).logout();
                break;
        }
    }

    @SuppressLint("LongLogTag")
    private void sendOrder(final String state) { // отправляем заявку на сервер
        waitDialog.show();
        RestManager.getApi()
                .sendOrderCar(mapHeader(), mCarOrder)
                .enqueue(new Callback<CarOrder>() {
                    @Override
                    public void onResponse(@NonNull Call<CarOrder> call, @NonNull Response<CarOrder> response) {
                        errorToken(response);
                        if (response.isSuccessful() && response.body() != null) {
                            setSwitchTab(state);
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<CarOrder> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        setSwitchTab(state);
                    }
                });
    }

    @SuppressLint("LongLogTag")
    private void updateOrder(final String state) { // редактируем заявку
        waitDialog.show();
        RestManager.getApi()
                .updateOrderCar(mapHeader(), mCarOrder, API_CAR_ITEM + mCarOrder.getId())
                .enqueue(new Callback<CarOrder>() {
                    @Override
                    public void onResponse(@NonNull Call<CarOrder> call, @NonNull Response<CarOrder> response) {
                        errorToken(response);
                        if (response.isSuccessful() && response.body() != null) {
                            setSwitchTab(state);
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<CarOrder> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        setSwitchTab(state);
                    }
                });
    }

    @SuppressLint("LongLogTag")
    private void cancelOrder(final String state) { // отправляем заявку на сервер
        waitDialog.show();
        RestManager.getApi()
                .cancelOrderCar(mapHeader(), API_CAR_ITEM + state + mCarOrder.getId())
                .enqueue(new Callback<CarOrder>() {
                    @Override
                    public void onResponse(@NonNull Call<CarOrder> call, @NonNull Response<CarOrder> response) {
                        errorToken(response);
                        if (response.isSuccessful() && response.body() != null) {
                            setSwitchTab(STATE_CANCELED);
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<CarOrder> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        setSwitchTab(STATE_CANCELED);
                    }
                });
    }

    @SuppressLint("LongLogTag")
    private void ratingOrder() { // оценка
        waitDialog.show();
        RestManager.getApi()
                .updateOrderCar(mapHeader(), mCarOrder, CAR_RATING)
                .enqueue(new Callback<CarOrder>() {
                    @Override
                    public void onResponse(@NonNull Call<CarOrder> call, @NonNull Response<CarOrder> response) {
                        errorToken(response);
                        if (response.isSuccessful() && response.body() != null) {
                            setSwitchTab(STATE_IN_WORK);
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<CarOrder> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        setSwitchTab(STATE_IN_WORK);
                    }
                });
    }

    private void setSwitchTab(String state) { // переключаемся между Tab-ами
        if (getActivity() != null) {
            getActivity().onBackPressed();
//            switch (state) {
//                case STATE_DRAFT:
//                    ((MainActivity) getActivity()).switchToFragment(new TabCarFragment(TAB_DRAFT));
//                    break;
//                case STATE_IN_WORK:
//                    ((MainActivity) getActivity()).switchToFragment(new TabCarFragment(TAB_ACTIVE));
//                    break;
//                case STATE_CANCELED:
//                    ((MainActivity) getActivity()).switchToFragment(new TabCarFragment(TAB_ARCHIVE));
//                    break;
//            }
        }
    }

    private void setOrderCar() { // Наполняем поля модели данными из View-шек
        mCarOrder.setOrderCity(mSpinnerСity.getSelectedItem().toString()); // город заказа
        mCarOrder.setContactPhone(mEtPhone.getText().toString()); // телефон
        mCarOrder.setDirection(mSpinnerDirection.getSelectedItem().toString()); // направление
        mCarOrder.setMePassenger(mSwitchMe.isChecked());  // наличие инициатора
        mCarOrder.setOtherPassenger(mSwitchOther.isChecked());  // наличие других пассажирова
        mCarOrder.setDateStart(ConvertTime.convertDateToUnix(mEtDateStart.getText().toString(),
                mEtTimeStart.getText().toString())); // дата отправления
        mCarOrder.setDateFinish(ConvertTime.convertDateToUnix(mEtDateEnd.getText().toString(),
                mEtTimeEnd.getText().toString())); // дата прибытия
        mCarOrder.setStartPoint(mEtPointStart.getText().toString()); // пункт отправления
        mCarOrder.setStopPoint(mEtPointEnd.getText().toString()); // пункт прибытия
        mCarOrder.setUsersComments(mEtComment.getText().toString()); // примечание
        mCarOrder.setNumPassengers(Integer.parseInt(mEtNumbPassenger.getText().toString())); // колличество всех пассажиров
        mCarOrder.setPassengerList(mPassengers); // список пассажиров
        mCarOrder.setPointList(mPoints); // список доп.пунктов
        mCarOrder.setEvaluation(String.valueOf(mSpinnerRating.getSelectedItem().toString().charAt(0))); // оценка (4)
        mCarOrder.setComment(mEtCommentRating.getText().toString()); // комент рейтинга
    }

    private boolean isValidation() {
        String error = "";

        if (mCarOrder.getOrderCity() == null || mCarOrder.getOrderCity().equals("")) {
            error = String.format("- %s", getString(R.string.text_error_city));
        }

        if (mCarOrder.getContactPhone() == null && mCarOrder.getContactPhone().equals("")) {
            error = String.format("%s\n- %s", error, getString(R.string.text_error_phone_null));
        }

        if (mCarOrder.getDirection() == null || mCarOrder.getDirection().equals("")) {
            error = String.format("%s\n- %s", error, getString(R.string.text_error_direction));
        }

        if (!(mCarOrder.getContactPhone() != null &&
                mCarOrder.getContactPhone().length() == 13 &&
                mCarOrder.getContactPhone().contains("+38"))) {
            error = String.format("%s\n- %s", error, getString(R.string.text_error_phone_format));
        }

        if (!mPassengers.isEmpty()) {
            for (Passenger passenger : mPassengers) {
                if (passenger.getFio() != null && passenger.getFio().equals("")) {
                    error = String.format("%s\n- %s", error, getString(R.string.text_error_passenger_fio));
                }
                if (passenger.isSms()) {
                    if (passenger.getPhone() == null || passenger.getPhone().equals("")) {
                        error = String.format("%s\n- %s", error, getString(R.string.text_error_phone_passenger));
                    }
                    if (!(passenger.getPhone().length() == 13 && passenger.getPhone().contains("+38"))) {
                        error = String.format("%s\n- %s", error, getString(R.string.text_error_phone_format_passenger));
                    }
                }
            }
        }
        if (mCarOrder.getNumPassengers() == 0) {
            error = String.format("%s\n- %s", error, getString(R.string.text_error_numb_passenger));
        }
        if (mCarOrder.getStartPoint() == null || mCarOrder.getStartPoint().equals("")) {
            error = String.format("%s\n- %s", error, getString(R.string.text_error_point_start));
        }
        if (mCarOrder.getStopPoint() == null || mCarOrder.getStopPoint().equals("")) {
            error = String.format("%s\n- %s", error, getString(R.string.text_error_point_stop));
        }

        long currentTime = System.currentTimeMillis() / 1000L;
        if (mCarOrder.getDateStart() < currentTime) {
            error = String.format("%s\n- %s", error, getString(R.string.text_error_time_start));
        }

        if (mCarOrder.getDateStart() > mCarOrder.getDateFinish()) {
            error = String.format("%s\n- %s", error, getString(R.string.text_error_time_finish));
        }

        if (!mPoints.isEmpty()) {
            long lastTimePoint = 0;
            for (Point point : mPoints) {
                if (point.getAddress() != null && point.getAddress().equals("")) {
                    error = String.format("%s\n- %s", error, getString(R.string.text_error_point_address));
                }
                if (mCarOrder.getDateStart() >= point.getDatePoint()) {
                    error = String.format("%s\n- %s", error, getString(R.string.text_error_time_point));
                }
                if (point.getWaitOrTakeAway().equals(TAKE_AWAY)) {
                    if (mCarOrder.getDateStart() > point.getDateTakeAway()) {
                        error = String.format("%s\n- %s", error, getString(R.string.text_error_time_take_away_start));
                    }
                    if (point.getDateTakeAway() > mCarOrder.getDateFinish()) {
                        error = String.format("%s\n- %s", error, getString(R.string.text_error_time_take_away_finish));
                    }
                    if (point.getDatePoint() > point.getDateTakeAway()) {
                        error = String.format("%s\n-%s", error, getString(R.string.text_error_time_point_numb, point.getNumber()));
                    }
                    if (lastTimePoint > point.getDatePoint()) {
                        error = String.format("%s\n- %s", error, getString(R.string.text_error_time_take_away_point));
                    }
                    lastTimePoint = point.getDatePoint();
                }
            }
        }

        if (!error.equals("")) {
            new MyDialog(error).show(getFragmentManager(), "fragmentDialog");
            return false;
        } else {
            String nowDate = ConvertTime.dateToString(new Date());
            if (nowDate.equals(mEtDateStart.getText().toString())) {
                new MyDialog(getString(R.string.text_warning_date_start)).show(getFragmentManager(), "fragmentDialog");
            }
            return true;
        }
    }

    private boolean isValidationRating() {
        String error = "";

        if (mCarOrder.getEvaluation() == null || (mSpinnerRating.getSelectedItem().toString()).equals(getString(R.string.text_select_rating))) {
            error = String.format("- %s", getString(R.string.text_error_rating));
        } else if (mCarOrder.getEvaluation().equals("1") || mCarOrder.getEvaluation().equals("2")) {
            if (mCarOrder.getComment().equals("")) {
                error = String.format("%s\n- %s", error, getString(R.string.text_error_comment_rating));
            }
        }

        if (error.length() > 0) {
            new MyDialog(error).show(getFragmentManager(), "fragmentDialog");
            return false;
        } else {
            return true;
        }
    }

    @OnClick(R.id.ibtn_car_now)
    public void onMapClicked() {
        ((MainActivity) Objects.requireNonNull(getActivity())).addToFragment(MapCarFragment.Companion.newInstance());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // Реакция на получение данных от диалогового окна
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_DATE_START:
                    Date date = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    mEtDateStart.setText(ConvertTime.dateToString(date));
                    mEtDateEnd.setText(ConvertTime.dateToString(date));
                    break;
                case REQUEST_TIME_START:
                    Date time = (Date) data.getSerializableExtra(TimePickerDialog.EXTRA_TIME);
                    mEtTimeStart.setText(ConvertTime.timeToString(time)); // 17:11
                    Date newTime = new Date(time.getTime() + TimeUnit.HOURS.toMillis(1));
                    mEtTimeEnd.setText(ConvertTime.timeToString(newTime));
                    break;
                case REQUEST_DATE_END:
                    Date dateEnd = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
                    mEtDateEnd.setText(ConvertTime.dateToString(dateEnd)); // 22.11.2015
                    break;
                case REQUEST_TIME_END:
                    Date timeEnd = (Date) data.getSerializableExtra(TimePickerDialog.EXTRA_TIME);
                    mEtTimeEnd.setText(ConvertTime.timeToString(timeEnd)); // 17:11
                    break;
                case REQUEST_CODE_LOGIN:
                    getOrderId();
                    break;
            }
        }
    }
}