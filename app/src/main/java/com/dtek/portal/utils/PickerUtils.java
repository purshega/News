package com.dtek.portal.utils;


import android.support.v4.app.Fragment;

import com.dtek.portal.ui.dialog.DatePickerDialog;
import com.dtek.portal.ui.dialog.TimePickerDialog;

import java.util.Date;

public class PickerUtils {

    // вызов DatePickerDialog и передача ему даты
    public static void initDatePicker(Fragment fragment, Date date, Date minDate, Date maxDate, int request) {
//        Date date = stringToDate(etDate.getText().toString());
        DatePickerDialog dialog = DatePickerDialog.newInstance(date, minDate, maxDate);
        dialog.setTargetFragment(fragment, request); // назначаем Fragment целевым фрагментом экземпляра DatePickerDialog
        if (fragment.getFragmentManager() != null) {
            dialog.show(fragment.getFragmentManager(), null);
        }
    }

    // вызов TimePickerDialog и передача ему времени
    public static void initTimePicker(Fragment fragment, Date time, int request) {
//        Date time = stringToTime(etTime.getText().toString());
        TimePickerDialog dialog = TimePickerDialog.newInstance(time);
        dialog.setTargetFragment(fragment, request); // назначаем Fragment целевым фрагментом экземпляра DatePickerDialog
        if (fragment.getFragmentManager() != null) {
            dialog.show(fragment.getFragmentManager(), null);
        }
    }
}
