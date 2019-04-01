package com.dtek.portal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtek.portal.BuildConfig;
import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.QrRequest;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.login.ServicePortal;
import com.dtek.portal.models.login.ServicesList;
import com.dtek.portal.models.push.Push;
import com.dtek.portal.models.push.PushData;
import com.dtek.portal.models.push.PushRemove;
import com.dtek.portal.ui.dialog.LockScreenDialog;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.ui.fragment.business_trip.TabBusinessTripFragment;
import com.dtek.portal.ui.fragment.car.TabCarFragment;
import com.dtek.portal.ui.fragment.gallery.GalleryGridFragment;
import com.dtek.portal.ui.fragment.hr_vacation.TabVacationFragment;
import com.dtek.portal.ui.fragment.itsm.TabItsmFragment;
import com.dtek.portal.ui.fragment.itsm.it.TabItFragment;
import com.dtek.portal.ui.fragment.itsm.room.TabRoomFragment;
import com.dtek.portal.ui.fragment.news.TabNewsFragment;
import com.dtek.portal.ui.fragment.newspaper.TabNewsPaperFragment;
import com.dtek.portal.ui.fragment.qr.QrScanFragment;
import com.dtek.portal.ui.fragment.reference.TabReferenceFragment;
import com.dtek.portal.ui.fragment.youtube.YouTubeFragment;
import com.dtek.portal.utils.LockTest;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.QrUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;

public class MainActivity extends AppCompatActivity {
//        implements NavigationView.OnNavigationItemSelectedListener  {

    private static final String TAG = "MainActivity";
    public static final int REQUEST_CODE_LOGIN = 1;
    public static final String CARS = "Cars";
    public static final String ITSM = "OrderItsm";
    public static final String AHO = "Aho";
    public static final String HR = "Hr";
    public static final String QR = "QR";
    public static final String BTRIPS = "BTrips";
    public static final String REFERENCE = "Forms";
    public static final String PHOTO = "Photo";
    public static final String NEWSPAPER = "Newspaper";

    private Menu nav_Menu;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    public TextView mToolbarTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    private ActionBar mActionBar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    private ImageView mIvLogin;

    @BindView(R.id.service_item)
    LinearLayout service_item;
    @BindView(R.id.news_group_layout)
    LinearLayout news_group_layout;
    @BindView(R.id.service_group_layout)
    LinearLayout service_group_layout;
    @BindView(R.id.media_group_layout)
    LinearLayout media_group_layout;
    @BindView(R.id.nav_car)
    LinearLayout nav_car;
    @BindView(R.id.nav_it_service)
    LinearLayout nav_it_service;
    @BindView(R.id.nav_room_service)
    LinearLayout nav_room_service;
    @BindView(R.id.nav_hr_vacation)
    LinearLayout nav_hr_vacation;
    @BindView(R.id.nav_qr)
    LinearLayout nav_qr;
    @BindView(R.id.nav_business_trip)
    LinearLayout nav_business_trip;
    @BindView(R.id.nav_references)
    LinearLayout nav_references;
    @BindView(R.id.nav_newspaper)
    LinearLayout nav_newspaper;
    @BindView(R.id.nav_video)
    LinearLayout nav_video;
    @BindView(R.id.service_separator_layout)
    LinearLayout service_separator_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Timber.plant(new Timber.DebugTree());

        initToolbar();
        initDrawer();
        initNavigationView();

        initDataFromIntent();
        setVisibleMenu(); //todo расскрываем все пункты меню

//        showAccessAllServices();  //todo версия для подрядчика
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (PreferenceUtils.getToken().length() > 0) { // токен есть?
            if (!LockTest.isDeviceScreenLocked(getApplicationContext())) { //  пароль не установлен?
                new LockScreenDialog().show(getSupportFragmentManager(), "fragmentDialog");
            }
        }
    }

    private void initDataFromIntent() {
        Intent intent = getIntent();
        try {
            if (intent.getStringExtra(Const.QR.QR_DATA) != null) {
                if (checkLogin())
                    getDataFromQR();
                else
                    goToLogin();
            }
            if (intent.getStringExtra(Const.PUSH.DATA_TYPE) != null) {
                if (checkLogin())
                    getDataFromPush();
            }
        } catch (NullPointerException e) {
        }
    }

    private boolean checkLogin() {
        if (PreferenceUtils.getToken().equals("")) {
            return false;
        } else
            return true;
    }

    private void getDataFromQR() {
        String data = getIntent().getStringExtra(Const.QR.QR_DATA);
        if (!data.equals("")) {
            if (PreferenceUtils.getToken().length() > 0) {
                ServicesList servicesList = PreferenceUtils.getAccessServices();
                for (ServicePortal servicePortal : servicesList.getServices()) {
                    if (servicePortal.getServiceName().equals(MainActivity.QR)) {
                        if (servicePortal.isSuccess()) {
                            if (!QrUtils.getCodeFromParse(data).equals(""))
                                checkDataFromServer(data);
                            else {
                                if (!data.equals("https://mobile.dtek.com/MobileApp"))
                                    QrUtils.wrongQqCode(this);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Функция сканирования QR-кода недоступна на вашем предприятии", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            } else {
                goToLogin();
            }
        }
    }

    private void checkDataFromServer(String data) {
        QrRequest qrRequest = new QrRequest(this);
        qrRequest.getActionByQrCode(data, WaitDialog.setDialog(this));
        mDrawer.closeDrawer(GravityCompat.START);
    }

    private void getDataFromPush() {
//        PushData pushData = getIntent().getParcelableExtra(Const.PUSH.JSON_BODY);
        String jsonBody = getIntent().getStringExtra(Const.PUSH.JSON_BODY);
        Gson gson = new Gson();
        PushData pushData = gson.fromJson(jsonBody, PushData.class);
        switch (getIntent().getStringExtra(Const.PUSH.DATA_TYPE)) {
            case "HR":
                if (pushData.getAction().equals(Const.PUSH.TYPE_HISTORY)) {
                    switchToFragment(new TabVacationFragment(TabVacationFragment.TAB_HISTORY), true);
                } else if (pushData.getAction().equals(Const.PUSH.TYPE_OPEN)) {
                    switchToFragment(new TabVacationFragment(TabVacationFragment.TAB_SUBORDINATE), true);
                }
                break;
        }
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        //скрыть название mToolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        mToolbarTitle.setText("");
    }

    private void initDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mDrawerToggle);
//        mDrawer.setDrawerListener(mDrawerToggle); //deprecated
        mDrawerToggle.syncState();

        mDrawer.openDrawer(GravityCompat.START); // при старте боковое меню раскрыто
    }

    private void initNavigationView() {
//        mNavigationView.setNavigationItemSelectedListener(this);

        View header = mNavigationView.getHeaderView(0);
        mIvLogin = header.findViewById(R.id.iv_login);
        setAccess();
    }

    public void onLoginClick(View v) {
        if (PreferenceUtils.isLogin()) {
            globalLogout();
        } else
            goToLogin();
    }

    private void goToLogin() {
        Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(intentLogin, REQUEST_CODE_LOGIN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // запишем в лог значения requestCode и resultCode
        Timber.d("requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (resultCode == RESULT_OK) {        // если пришло ОК
            switch (requestCode) {
                case REQUEST_CODE_LOGIN:
                    setAccess();
                    initDataFromIntent();
                    break;
            }
        }
    }

    private void setAccess() {
        nav_Menu = mNavigationView.getMenu();

        ServicesList servicesList = PreferenceUtils.getAccessServices();

        if (PreferenceUtils.getToken().length() > 0 && servicesList != null && servicesList.isValid()) { // залогинились
            if (BuildConfig.DEBUG) {
                mIvLogin.setImageResource(R.drawable.ic_exit);
            } else {
                mIvLogin.setVisibility(View.GONE); // скрываем иконку логина
            }
//            showAccessServices(nav_Menu, servicesList);
            showAccessServices(servicesList);
        } else {
            if (BuildConfig.DEBUG) {
                mIvLogin.setImageResource(R.drawable.ic_account_circle);
            } else {
                mIvLogin.setVisibility(View.VISIBLE); // отображаем иконку логина
            }
        }
    }

    //    private void showAccessServices(Menu nav_Menu, ServicesList servicesList) {
    private void showAccessServices(ServicesList servicesList) {
        service_item.setVisibility(View.VISIBLE);
        service_separator_layout.setVisibility(View.VISIBLE);
        List<ServicePortal> portalList = servicesList.getServices();
        for (ServicePortal servicePortal : portalList) {
            switch (servicePortal.getServiceName()) {
                case CARS:
//                    nav_Menu.findItem(R.id.nav_car).setVisible(servicePortal.isSuccess()); // отображаем/скрваем заказ авто
                    if (servicePortal.isSuccess()) nav_car.setVisibility(View.VISIBLE);
                    break;
                case ITSM:
//                    nav_Menu.findItem(R.id.nav_it_service).setVisible(servicePortal.isSuccess()); // отображаем/скрваем it-услуги
                    if (servicePortal.isSuccess()) nav_it_service.setVisibility(View.VISIBLE);
                    break;
                case AHO:
//                    nav_Menu.findItem(R.id.nav_room_service).setVisible(servicePortal.isSuccess()); // отображаем/скрваем офисные помещения
                    if (servicePortal.isSuccess()) nav_room_service.setVisibility(View.VISIBLE);
                    break;
                case HR:
//                    nav_Menu.findItem(R.id.nav_hr_vacation).setVisible(servicePortal.isSuccess()); // отображаем/скрваем hr-отпуска
                    if (servicePortal.isSuccess()) nav_hr_vacation.setVisibility(View.VISIBLE);
                    break;
                case QR:
//                    nav_Menu.findItem(R.id.nav_qr).setVisible(servicePortal.isSuccess()); // отображаем/скрваем qr
                    if (servicePortal.isSuccess()) nav_qr.setVisibility(View.VISIBLE);
                    break;
                case BTRIPS:
                    if (servicePortal.isSuccess()) nav_business_trip.setVisibility(View.VISIBLE);
                    break;
//                case REFERENCE:
//                    if (servicePortal.isSuccess()) nav_references.setVisibility(View.VISIBLE);
//                    break;
            }
        }

//            nav_Menu.findItem(R.id.nav_newspaper).setVisible(true);
        nav_newspaper.setVisibility(View.VISIBLE);
//        nav_references.setVisibility(View.VISIBLE);
//        nav_video.setVisibility(View.VISIBLE); // todo отображение видео YouTube
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Timber.d("backStackCount:" + getSupportFragmentManager().getBackStackEntryCount() + " currentFragment-" + currentFragment);
            if (getSupportFragmentManager().getBackStackEntryCount() == 0)
                mToolbarTitle.setText(getString(R.string.app_name));
//                openQuitDialog();

        }
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(MainActivity.this);
        quitDialog.setTitle("Выход: Вы уверены?");
        quitDialog.setPositiveButton("Да!", (dialog, which) -> finish());
        quitDialog.setNegativeButton("Нет", (dialog, which) -> {
        });
        quitDialog.show();
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE); // обнуляем Stack фрагментов
//        mToolbarTitle.setText(getString(R.string.app_name));
//
//        switch (item.getItemId()) {
//            case R.id.nav_news_dtek:
//                switchToFragment(new TabNewsFragment(TabNewsFragment.TAB_NEWS_DTEK));
//                break;
//            case R.id.nav_not_miss:
//                switchToFragment(new TabNewsFragment(TabNewsFragment.TAB_NEWS_NO_MISS));
//                break;
//            case R.id.nav_news_company:
//                switchToFragment(new TabNewsFragment(TabNewsFragment.TAB_NEWS_COMPANY));
//                break;
//            case R.id.nav_car:
//                switchToFragment(new TabCarFragment(TabCarFragment.TAB_ACTIVE));
//                break;
//            case R.id.nav_it_service:
//                switchToFragment(new TabItFragment(TabItsmFragment.TAB_ACTIVE));
//                break;
//            case R.id.nav_room_service:
//                switchToFragment(new TabRoomFragment(TabItsmFragment.TAB_ACTIVE));
//                break;
//            case R.id.nav_hr_vacation:
//                switchToFragment(new TabVacationFragment(TabVacationFragment.TAB_LIMIT));
//                break;
//            case R.id.nav_qr:
//                switchToFragment(QrScanFragment.newInstance());
//                break;
//            case R.id.nav_photo:
//                switchToFragment(GalleryGridFragment.newInstance());
////                Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
////                startActivity(intent);
//                break;
//            case R.id.nav_send:
////                feedback();
//                break;
//            case R.id.nav_newspaper:
//                switchToFragment(new TabNewsPaperFragment(TabNewsPaperFragment.TAB_RUS));
//                break;
//        }
//        mDrawer.closeDrawer(GravityCompat.START);
//        Timber.d("NavigationItemSelected backStackCount:" + getSupportFragmentManager().getBackStackEntryCount() + " currentFragment-" + currentFragment);
//        return true;
//    }

    private void feedback() {
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", getString(R.string.address_email), null));
        mailIntent.putExtra(Intent.EXTRA_SUBJECT,
                String.format("Android %s - %s v%s", Build.VERSION.RELEASE, getString(R.string.app_name), BuildConfig.VERSION_NAME));
        mailIntent.putExtra(Intent.EXTRA_TEXT,
                String.format("%s %s\n___________________________________\n\n\n", Build.MANUFACTURER, Build.MODEL));
        startActivity(Intent.createChooser(mailIntent, getString(R.string.choose_client)));
    }

    private Fragment currentFragment;

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public void switchToFragment(Fragment fragment, boolean stackReset) {
        chooseNewFragment(stackReset);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)// добавляем фрагменты в BackStack
                .replace(R.id.fragmentContainer, fragment)
                .commitAllowingStateLoss();
        setCurrentFragment(fragment);
        Timber.d("switch backStackCount:" + getSupportFragmentManager().getBackStackEntryCount() + " currentFragment-" + currentFragment);
    }

    public void addToFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)// добавляем фрагменты в BackStack
                .add(R.id.fragmentContainer, fragment)
                .commitAllowingStateLoss();
        setCurrentFragment(fragment);
        Timber.d("add backStackCount:" + getSupportFragmentManager().getBackStackEntryCount() + " currentFragment-" + currentFragment);
    }

    public void showUpButton() {
        mDrawerToggle.setDrawerIndicatorEnabled(false); // Remove hamburger
        mActionBar.setDisplayHomeAsUpEnabled(true); // Show back button
        //добавляем слушателя кнопки
        mDrawerToggle.setToolbarNavigationClickListener(v -> { // клик по кнопке
            onBackPressed(); // возврат назад
            hideKeyboard(v);
        });
    }

    public void showBurgerButton() {
        mActionBar.setDisplayHomeAsUpEnabled(false); // Remove back button
        mDrawerToggle.setDrawerIndicatorEnabled(true); // Show hamburger
        mDrawerToggle.setToolbarNavigationClickListener(null);//убираем слушателя drawer toggle
        setAccess(); // применение проверки логина
    }

    private void hideKeyboard(View v) { // прячем клавиатуру
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void setVisibleMenu() {
        news_group_layout.setVisibility(View.VISIBLE);
        service_group_layout.setVisibility(View.VISIBLE);
        media_group_layout.setVisibility(View.VISIBLE);
    }

    /*
     заменено стандартное меню на кастомный Layout, в связи с тем, что невозможно было достучатся до SubMenu, которе должно было
     при клике скрывать пункты SubMenu
    */
    @OnClick({R.id.news_item, R.id.nav_news_dtek, R.id.nav_not_miss, R.id.nav_news_company, R.id.service_item, R.id.nav_car,
            R.id.nav_it_service, R.id.nav_room_service, R.id.nav_hr_vacation, R.id.nav_qr, R.id.nav_business_trip, R.id.nav_references,
            R.id.media_item, R.id.nav_photo, R.id.nav_newspaper, R.id.nav_video})
    void onClick(View view) {
        switch (view.getId()) {
//            case R.id.news_item:
//                if (news_group_layout.getVisibility() == View.VISIBLE)
//                    news_group_layout.setVisibility(View.GONE);
//                else {
//                    closeAllGroup();
//                    news_group_layout.setVisibility(View.VISIBLE);
//                }
//                return;
//            case R.id.service_item:
//                if (service_group_layout.getVisibility() == View.VISIBLE)
//                    service_group_layout.setVisibility(View.GONE);
//                else {
//                    closeAllGroup();
//                    service_group_layout.setVisibility(View.VISIBLE);
//                }
//                return;
//            case R.id.media_item:
//                if (media_group_layout.getVisibility() == View.VISIBLE)
//                    media_group_layout.setVisibility(View.GONE);
//                else {
//                    closeAllGroup();
//                    media_group_layout.setVisibility(View.VISIBLE);
//                }
//                return;
            case R.id.nav_news_dtek:
                switchToFragment(new TabNewsFragment(TabNewsFragment.TAB_NEWS_DTEK), true);
                break;
            case R.id.nav_not_miss:
                switchToFragment(new TabNewsFragment(TabNewsFragment.TAB_NEWS_NO_MISS), true);
                break;
            case R.id.nav_news_company:
                switchToFragment(new TabNewsFragment(TabNewsFragment.TAB_NEWS_COMPANY), true);
                break;
            case R.id.nav_car:
                switchToFragment(new TabCarFragment(TabCarFragment.TAB_ACTIVE), true);
                break;
            case R.id.nav_it_service:
                switchToFragment(new TabItFragment(TabItsmFragment.TAB_ACTIVE), true);
                break;
            case R.id.nav_room_service:
                switchToFragment(new TabRoomFragment(TabItsmFragment.TAB_ACTIVE), true);
                break;
            case R.id.nav_hr_vacation:
                switchToFragment(new TabVacationFragment(TabVacationFragment.TAB_LIMIT), true);
                break;
            case R.id.nav_qr:
                switchToFragment(QrScanFragment.newInstance(), true);
                break;
            case R.id.nav_business_trip:
                switchToFragment(new TabBusinessTripFragment(TabBusinessTripFragment.TAB_ACTIVE), true);
                break;
            case R.id.nav_references:
                switchToFragment(new TabReferenceFragment(TabReferenceFragment.TAB_ALL), true);
                break;
            case R.id.nav_photo:
                switchToFragment(GalleryGridFragment.newInstance(), true);
                break;
            case R.id.nav_send:
//                feedback();
                break;
            case R.id.nav_newspaper:
                switchToFragment(new TabNewsPaperFragment(TabNewsPaperFragment.TAB_RUS), true);
                break;
            case R.id.nav_video:
                switchToFragment(new YouTubeFragment(), true);
                break;
        }
    }


    private void closeAllGroup() {
        news_group_layout.setVisibility(View.GONE);
        service_group_layout.setVisibility(View.GONE);
        media_group_layout.setVisibility(View.GONE);
    }

    private void chooseNewFragment(boolean stackReset) {
        if (stackReset)
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE); // обнуляем Stack фрагментов
        mDrawer.closeDrawer(GravityCompat.START);
        Timber.d("NavigationItemSelected backStackCount:" + getSupportFragmentManager().getBackStackEntryCount() + " currentFragment-" + currentFragment);
    }

    public void logout() {
        updateViewLogout();
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivityForResult(intentLogin, REQUEST_CODE_LOGIN);
    }

    private void updateViewLogout() {
        mIvLogin.setImageResource(R.drawable.ic_account_circle);
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        service_item.setVisibility(View.GONE);
        for (int i = 0; i < service_group_layout.getChildCount(); i++) {
            View view = service_group_layout.getChildAt(i);
            view.setVisibility(View.GONE);
        }
        service_separator_layout.setVisibility(View.GONE);
        nav_newspaper.setVisibility(View.GONE);
        nav_video.setVisibility(View.GONE);

        mToolbarTitle.setText("");
    }

    public void showMenu() {
        mDrawer.openDrawer(GravityCompat.START);
    }

    private void globalLogout() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        Push push = new Push(PreferenceUtils.getLogin(), PreferenceUtils.getPushToken());
        RestManager.getApi()
                .removedPushToken(map, push)
                .enqueue(new Callback<PushRemove>() {
                    @Override
                    public void onResponse(@NonNull Call<PushRemove> call, @NonNull Response<PushRemove> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            PreferenceUtils.saveIsLogin(false);
                            PreferenceUtils.saveToken("");
                            updateViewLogout();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PushRemove> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void showAccessAllServices() {  //todo версия для подрядчика
        PreferenceUtils.saveToken("12345qwty");
        service_item.setVisibility(View.VISIBLE);
        service_separator_layout.setVisibility(View.VISIBLE);
        nav_car.setVisibility(View.VISIBLE);
        nav_it_service.setVisibility(View.VISIBLE);
        nav_room_service.setVisibility(View.VISIBLE);
        nav_hr_vacation.setVisibility(View.VISIBLE);
        nav_qr.setVisibility(View.VISIBLE);
        nav_newspaper.setVisibility(View.VISIBLE);
        nav_video.setVisibility(View.VISIBLE);
    }


}
