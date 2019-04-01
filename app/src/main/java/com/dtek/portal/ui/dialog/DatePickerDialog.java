package com.dtek.portal.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.dtek.portal.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerDialog extends DialogFragment {

    private static final String ARG_DATE = "date"; //аргумент для получения данных
    private static final String ARG_DATE_MIN = "date_min"; //аргумент для получения данных
    private static final String ARG_DATE_MAX = "date_max"; //аргумент для получения данных
    public static final String EXTRA_DATE = "com.dtek.portal.date"; // помещаем дату возврата

    private DatePicker mDatePicker;
    private int year;
    private int month;
    private int day;
//    private int hour;
//    private int minute;
//    private int second;
    Date date = null; // получите объект Date из аргументов и используем его с Calendar
    Date minDate = null; // минимальная дата
    Date maxDate = null; // максимальная дата

    public static DatePickerDialog newInstance(Date date, Date minDate, Date maxDate) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        args.putSerializable(ARG_DATE_MIN, minDate);
        args.putSerializable(ARG_DATE_MAX, maxDate);

        DatePickerDialog fragment = new DatePickerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    private void readArguments() {
        if (getArguments() != null) {
            date = (Date) getArguments().getSerializable(ARG_DATE);
            minDate = (Date) getArguments().getSerializable(ARG_DATE_MIN);
            maxDate = (Date) getArguments().getSerializable(ARG_DATE_MAX);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null); // заполняем представление
//        DatePicker dp = new DatePicker(getActivity()); // вариант в коде без представления
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        readArguments();

        Calendar calendar = Calendar.getInstance(); // календарь для получения нужных значений(месяца, дня и года)
        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
//        hour = calendar.get(Calendar.HOUR_OF_DAY);
//        minute = calendar.get(Calendar.MINUTE);
//        second = calendar.get(Calendar.SECOND);


        if (minDate != null) {
            mDatePicker.setMinDate(minDate.getTime()/* - 1000*/);
        }
        if (maxDate != null) {
            mDatePicker.setMaxDate(maxDate.getTime());
        }

        mDatePicker.init(year, month, day, null); // сообщаем DatePicker что нам нужно использовать

        return new AlertDialog.Builder(getActivity())
                .setView(v) // назначаем представление диалоговому окну.
//                .setView(dp) // вариант в коде без представления
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, //кнопка и слушатель
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                 year = mDatePicker.getYear();
                                 month = mDatePicker.getMonth();
                                 day = mDatePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year, month, day/*, hour, minute, second*/).getTime();
                                sendResult(Activity.RESULT_OK, date); // Передача информации
                            }
                        })
                .create();
    }

    // Обратный вызов целевого фрагмента
    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent(); // создает интент,
        intent.putExtra(EXTRA_DATE, date); // помещает в него дату как дополнение,
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent); //вызывает CrimeFragment.onActivityResult(…)
    }

}
