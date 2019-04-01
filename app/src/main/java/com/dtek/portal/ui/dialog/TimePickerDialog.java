package com.dtek.portal.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.dtek.portal.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerDialog extends DialogFragment {

    private static final String ARG_TIME = "time"; //аргумент для получения данных
    public static final String EXTRA_TIME = "com.dtek.portal.time"; // помещаем время возврата

    private TimePicker mTimePicker;

    public static TimePickerDialog newInstance(Date time) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, time);

        TimePickerDialog fragment = new TimePickerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date time = (Date) getArguments().getSerializable(ARG_TIME);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setIs24HourView(true);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int hour = mTimePicker.getCurrentHour();
                                int minute = mTimePicker.getCurrentMinute();
                                minute = setMinuteDivide15(minute);
                                Date time = new GregorianCalendar(0, 0, 0, hour, minute).getTime();
                                sendResult(Activity.RESULT_OK, time);
                            }
                        })
                .create();
    }

    public static int setMinuteDivide15(int minute) {
        if (minute < 8 ){
            minute = 0;
        }
        if (minute >= 8 && minute < 23 ){
            minute = 15 ;
        }
        if (minute >= 23 && minute < 38 ){
            minute = 30 ;
        }
        if (minute >= 38 && minute < 53 ){
            minute = 45 ;
        }
        if (minute >= 53){
            minute = 0;
        }
        return minute;
    }


    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
