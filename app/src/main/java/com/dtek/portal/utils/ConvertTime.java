package com.dtek.portal.utils;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class ConvertTime {

    private static SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault());
    public static SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    private static SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm", Locale.getDefault());

//    public static SimpleDateFormat sdfSharp = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public static String formatDateTime(long unixSeconds) { // получаем дату и время в String
        Date date = new Date(unixSeconds * 1000L);
//        sdfDateTime.setTimeZone(TimeZone.getTimeZone("GMT-4"));
//        sdfDateTime.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
//        sdfDateTime.setTimeZone(TimeZone.getDefault());
        return sdfDateTime.format(date);
    }

    public static String formatDate(long unixSeconds) { // получаем дату в String
        Date date = new Date(unixSeconds * 1000L);
//        sdfDate.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
        return sdfDate.format(date);
    }

    public static String formatTime(long unixSeconds) { // получаем время в String
        Date date = new Date(unixSeconds * 1000L);
//        sdfTime.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
        return sdfTime.format(date);
    }

    public static long convertDateToUnix(String strDate, String strTime) {
        long unixSeconds;
        String dateTime = strDate + ", " + strTime;
//        sdfDateTime.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
        try {
            Date date = sdfDateTime.parse(dateTime);
            unixSeconds = date.getTime() / 1000L;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return unixSeconds;
    }

    public static long convertDateToUnixITSM(String strDate, String strTime) {
        long unixSeconds;
        String dateTime = strDate + ", " + strTime;
        Timber.d("COMING STRING DATE ------------------>>>>>>  " + dateTime);
//        sdfDateTime.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
        try {
            Date date = sdfDateTime.parse(dateTime);
            unixSeconds = date.getTime() / 1000L;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return unixSeconds;
    }

    @NonNull
    public static String dateToString(Date date) { // 22.11.2015
        return sdfDate.format(date);
    }

    @NonNull
    public static String timeToString(Date time) { // 17:11
        return sdfTime.format(time);
    }

    @NonNull
    public static Date stringToDate(String s) { // 22.11.2015
        Date date = new Date();
        try {
            if (!TextUtils.isEmpty(s)){
                date = sdfDate.parse(s);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @NonNull
    public static Date stringToTime(String s) { // 17:11
        Date time = new Date();
        try {
            time = sdfTime.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static Date addDaysToDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, days);
        date = c.getTime();
        return date;
    }


}
