package com.dtek.portal.utils;

import android.support.annotation.NonNull;

import com.dtek.portal.models.hr_vacation.VacationList;
import com.dtek.portal.models.login.ServicesList;
import com.orhanobut.hawk.Hawk;

import java.util.List;

public final class PreferenceUtils {

    private static final String IS_LOGIN = "isLogin";

    private static final String TOKEN_KEY = "token";
    private static final String PUSH_TOKEN_KEY = "push_token";
    private static final String SERVICES = "services";
    private static final String LOGIN = "login";
    private static final String LOGIN_ID = "loginID";
    private static final String USER_PHONE = "userPhone";
    private static final String PHONE_IS_CORPORATE = "IsCorporate";
    private static final String DATE_CACHE = "dateCache";
    private static final String LEAVE_LIST = "LeaveList";
    private static final String NOTIFICATION_ID = "notification_id";

    private PreferenceUtils() {
    }

    //токен
    @NonNull
    public static String getToken() {
        return Hawk.get(TOKEN_KEY, "");
    }

    //push токен
    @NonNull
    public static String getPushToken() {
        return Hawk.get(PUSH_TOKEN_KEY, "");
    }

    public static void saveToken(@NonNull String token) {
        Hawk.put(TOKEN_KEY, token);
    }

    public static void savePushToken(@NonNull String pushToken) {
        Hawk.put(PUSH_TOKEN_KEY, pushToken);
    }

    //gson сервисы
    public static ServicesList getAccessServices() {
        return Hawk.get(SERVICES, null);
    }

    public static void saveAccessServices(ServicesList services) {
        Hawk.put(SERVICES, services);
    }

    //логин ID
    @NonNull
    public static String getLogin() {
        return Hawk.get(LOGIN, "");
    }

    public static void saveLogin(String login) {
        Hawk.put(LOGIN, login);
    }

    //логин
    @NonNull
    public static String getLoginId() {
        return Hawk.get(LOGIN_ID, "");
    }

    public static void saveLoginId(String loginId) {
        Hawk.put(LOGIN_ID, loginId);
    }

    //телефон
    @NonNull
    public static String getUserPhone() {
        return Hawk.get(USER_PHONE, "");
    }

    public static void saveUserPhone(String userPhone) {
        Hawk.put(USER_PHONE, userPhone);
    }

    //телефон корпоративный?
    public static boolean isPhoneCorporate() {
        return Hawk.get(PHONE_IS_CORPORATE, false);
    }

    public static void savePhoneCorporate(boolean phoneIsCorporate) {
        Hawk.put(PHONE_IS_CORPORATE, phoneIsCorporate);
    }

    public static void saveIsLogin(boolean isLogin) {
        Hawk.put(IS_LOGIN, isLogin);
    }

    public static boolean isLogin() {
        return Hawk.get(IS_LOGIN, false);
    }


    //Время кеша новостей
    public static int getDay() {
        return Hawk.get(DATE_CACHE, 0);
    }

    public static void saveDay(int day) {
        Hawk.put(DATE_CACHE, day);
    }

    //id нотификаций
    public static int getNotificationId() {
        return Hawk.get(NOTIFICATION_ID, 0);
    }

    public static void saveNotificationId(int id) {
        Hawk.put(NOTIFICATION_ID, id);
    }

    //List Отпуска
    @NonNull
    public static List<VacationList.Vacation> getLeave() {
        return Hawk.get(LEAVE_LIST, null);
    }

    public static <T> void saveLeave(List<T> list) {
        Hawk.put(LEAVE_LIST, list);
    }

}
