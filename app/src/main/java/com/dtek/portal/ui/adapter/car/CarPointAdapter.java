package com.dtek.portal.ui.adapter.car;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dtek.portal.R;
import com.dtek.portal.callback.PointDiffCallback;
import com.dtek.portal.models.car.Point;
import com.dtek.portal.utils.ConvertTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dtek.portal.ui.dialog.TimePickerDialog.setMinuteDivide15;

public class CarPointAdapter extends RecyclerView.Adapter<CarPointAdapter.ViewHolder> {
    private static final String TAG = "CarPointAdapter";
    public static final String WAIT = "Wait";
    public static final String TAKE_AWAY = "TakeAway";

    private Context mContext;
    private List<Point> mPoints = new ArrayList<>();

    // Конструктор
    public CarPointAdapter(Context context, List<Point> list) {
        mContext = context;
//        mPassengers = list;
        this.mPoints.addAll(list);
    }

    // Создает новые views (вызывается layout manager-ом)
    @NonNull
    @Override
    public CarPointAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_car_point, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Point point = mPoints.get(position);
        Log.d(TAG, "bind, position = " + position);

        holder.tvNumbPoint.setText(String.valueOf(point.getNumber()));

        holder.etAddressPoint.setText(String.valueOf(point.getAddress()));
        holder.etAddressPoint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                point.setAddress(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        holder.etDatePoint.setText(ConvertTime.formatDate(point.getDatePoint()));
        holder.etDatePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Date date = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                        holder.etDatePoint.setText(ConvertTime.dateToString(date));
                        point.setDatePoint(ConvertTime.convertDateToUnix(holder.etDatePoint.getText().toString(),
                                holder.etTimePoint.getText().toString()));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime() - 1000);
                datePickerDialog.setTitle(R.string.date_picker_title);
                datePickerDialog.show();
            }
        });

        holder.etTimePoint.setText(ConvertTime.formatTime(point.getDatePoint()));
        holder.etTimePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog tpd = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int minute15 = setMinuteDivide15(minute);
                        Date time = new GregorianCalendar(0, 0, 0, hourOfDay, minute15).getTime();
                        holder.etTimePoint.setText(ConvertTime.timeToString(time));
                        point.setDatePoint(ConvertTime.convertDateToUnix(holder.etDatePoint.getText().toString(),
                                holder.etTimePoint.getText().toString()));
                    }
                }, mHour, mMinute, true);
                tpd.setTitle(R.string.time_picker_title);
                tpd.show();
            }
        });

        switch (point.getWaitOrTakeAway()){
            case WAIT:
                holder.rbWaitPoint.setChecked(true);
                holder.rbTakeAwayPoint.setChecked(false);
                holder.rlWait.setVisibility(View.VISIBLE);
                break;
            case TAKE_AWAY:
                holder.rbWaitPoint.setChecked(false);
                holder.rbTakeAwayPoint.setChecked(true);
                holder.llTakeAway.setVisibility(View.VISIBLE);
                break;
        }

        holder.rbWaitPoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.rlWait.setVisibility(View.VISIBLE);
                    holder.llTakeAway.setVisibility(View.GONE);
                    point.setWaitOrTakeAway(WAIT);
//                    point.setDateTakeAway(0);
                } else {
                    holder.rlWait.setVisibility(View.GONE);
                    holder.llTakeAway.setVisibility(View.VISIBLE);
                }
                holder.rbTakeAwayPoint.setChecked(!isChecked);
            }
        });

        holder.rbTakeAwayPoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.rlWait.setVisibility(View.GONE);
                    holder.llTakeAway.setVisibility(View.VISIBLE);
                    holder.etDateTakeAwayPoint.setText(ConvertTime.formatDate(point.getDateTakeAway()));
                    holder.etTimeTakeAwayPoint.setText(ConvertTime.formatTime(point.getDateTakeAway()));
                    point.setWaitOrTakeAway(TAKE_AWAY);
                } else {
                    holder.rlWait.setVisibility(View.VISIBLE);
                    holder.llTakeAway.setVisibility(View.GONE);
                }
                holder.rbWaitPoint.setChecked(!isChecked);
            }
        });

        String orderCity = point.getWaitMinutes();
        if (orderCity != null && !orderCity.equals("")) {
            final ArrayAdapter<CharSequence> adapterMin = ArrayAdapter.createFromResource(mContext,
                    R.array.minute, android.R.layout.simple_spinner_item);
            int posMin = adapterMin.getPosition(orderCity);
            holder.spinnerWaitMinutes.setSelection(posMin);
        }
        holder.spinnerWaitMinutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View itemSelected,
                                       int selectedItemPosition, long selectedId) {
                String[] spinnerType = mContext.getResources().getStringArray(R.array.minute);
                point.setWaitMinutes(spinnerType[selectedItemPosition]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        holder.etDateTakeAwayPoint.setText(ConvertTime.formatDate(point.getDateTakeAway()));
        holder.etDateTakeAwayPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Date date = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                        holder.etDateTakeAwayPoint.setText(ConvertTime.dateToString(date));
                        point.setDateTakeAway(ConvertTime.convertDateToUnix(holder.etDateTakeAwayPoint.getText().toString(),
                                holder.etTimeTakeAwayPoint.getText().toString()));
                    }
                }, mYear, mMonth, mDay);
                dpd.getDatePicker().setMinDate(new Date().getTime() - 1000);
                dpd.setTitle(R.string.date_picker_title);
                dpd.show();
            }
        });

        holder.etTimeTakeAwayPoint.setText(ConvertTime.formatTime(point.getDateTakeAway()));
        holder.etTimeTakeAwayPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog tpd = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int minute15 = setMinuteDivide15(minute);
                        Date time = new GregorianCalendar(0, 0, 0, hourOfDay, minute15).getTime();
                        holder.etTimeTakeAwayPoint.setText(ConvertTime.timeToString(time));
                        point.setDateTakeAway(ConvertTime.convertDateToUnix(holder.etDateTakeAwayPoint.getText().toString(),
                                holder.etTimeTakeAwayPoint.getText().toString()));
                    }
                }, mHour, mMinute, true);
                tpd.setTitle(R.string.time_picker_title);
                tpd.show();
            }
        });
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mPoints == null ? 0 : mPoints.size();
    }

    public void removeItem(int position) {
        mPoints.remove(position);

//        Log.i(TAG, "onRemoveClick: " + mPassengers.size());
        //обновляем порядковые номера элементов
        int countNumb = position + 1;
        for (int i = position; i < mPoints.size(); i++) { // пробнгаемся по порядковым номерам после удаленного элемента
            mPoints.get(i).setNumber(countNumb++); // изменяем порядковые номера
        }

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mPoints.size());
    }

    public void updateListFragment(List<Point> list) {
        list.clear();
        list.addAll(mPoints);
    }

    public void updatePointListItems(List<Point> points) {
        final PointDiffCallback diffCallback = new PointDiffCallback(this.mPoints, points);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.mPoints.clear();
        this.mPoints.addAll(points);
        diffResult.dispatchUpdatesTo(this);
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит из
        @BindView(R.id.iv_remove_point) ImageView ivRemovePoint;
        @BindView(R.id.tv_numb_point) TextView tvNumbPoint;
        @BindView(R.id.et_address_point) EditText etAddressPoint;
        @BindView(R.id.et_date_point) EditText etDatePoint;
        @BindView(R.id.et_time_point) EditText etTimePoint;
        @BindView(R.id.rb_wait_point) RadioButton rbWaitPoint;
        @BindView(R.id.rl_wait) RelativeLayout rlWait;
        @BindView(R.id.spinner_wait_minute) Spinner spinnerWaitMinutes;
        @BindView(R.id.rb_take_away_point) RadioButton rbTakeAwayPoint;
        @BindView(R.id.ll_take_away) LinearLayout llTakeAway;
        @BindView(R.id.et_date_take_away_point) EditText etDateTakeAwayPoint;
        @BindView(R.id.et_time_take_away_point) EditText etTimeTakeAwayPoint;

        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @OnClick(R.id.iv_remove_point)
        void onRemoveClick(View v) {
            switch (v.getId()) {
                case R.id.iv_remove_point:
                    if (sPointListener != null) {
                        sPointListener.onRemovePoint(getAdapterPosition());
                    }
                    break;
            }
        }
    }


    //интерфейс
    private static PointListener sPointListener; //переменная

    public void setPointListener(PointListener listener) {//кот. содержит в себе объект
        CarPointAdapter.sPointListener = listener;
    }

    public interface PointListener { //этот объект реализует этот интерфейс
        void onRemovePoint(int adapterPosition); // удаляем
    }
}
