package com.dtek.portal.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.login.Login;
import com.dtek.portal.models.login.LoginHelp;
import com.dtek.portal.models.login.ServicesList;
import com.dtek.portal.models.push.Push;
import com.dtek.portal.ui.dialog.LockScreenDialog;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.ui.dialog.MySpannedDialog;
import com.dtek.portal.utils.LockTest;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static com.dtek.portal.Const.HTTP.*;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int SMS_REQUEST_CODE = 101;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_login)
    EditText mEtLogin;
    @BindView(R.id.et_sms)
    EditText mEtSms;
    @BindView(R.id.btn_sms)
    Button mBtnSms;
    @BindView(R.id.btn_enter)
    Button mBtnEnter;
    @BindView(R.id.sms_login_form)
    LinearLayout mSmsForm;
    @BindView(R.id.progress)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_help_sms)
    TextView mTvHelp;
    private boolean isSmsFormActive; // отображение формы ввода СМС
    private Spanned faqSpanned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initToolbar();

        if (Utils.isNetworkAvailable(getApplicationContext())) {
            loadHelpSms();
        } else {
            Toast.makeText(this, getText(R.string.error_msg_no_internet), Toast.LENGTH_SHORT).show();
        }

        if (PreferenceUtils.isPhoneCorporate()) { // если тел корпоративный?
            mEtLogin.setText(PreferenceUtils.getLogin());
        }

        PreferenceUtils.saveToken("");
        PreferenceUtils.saveAccessServices(null);

        if (isSmsFormActive) {
            mSmsForm.setVisibility(View.VISIBLE);
        }
//        loadSmsReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SmsReceiver.unregisterReceiver(getApplicationContext());
    }

//    private void loadSmsReceiver() {
//        SmsReceiver.registerReceiver(getApplicationContext(), new SmsListener() {
//            @Override
//            public void messageReceived(String messageText) {
//                mEtSms.setText(messageText);
//            }
//        });
//    }

//    private void requestPermission() {
//        ActivityCompat.requestPermissions(this, new String[]{RECEIVE_SMS, READ_SMS}, SMS_REQUEST_CODE);
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case SMS_REQUEST_CODE:
//                if (grantResults.length > 0) {
//                    boolean SmsPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean SmsRead = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//
//                    if (SmsPermission && SmsRead) {
//                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
//                        getSms();
//                    } else {
//                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
//                    }
//                }
//                break;
//        }
//    }

//    public boolean checkPermission() {
//        int result = ContextCompat.checkSelfPermission(this, RECEIVE_SMS);
//        int result1 = ContextCompat.checkSelfPermission(this, READ_SMS);
//        return result == PackageManager.PERMISSION_GRANTED &&
//                result1 == PackageManager.PERMISSION_GRANTED;
//    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        //скрыть название mToolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void onButtonClick(View v) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            switch (v.getId()) {
                case R.id.btn_sms:
                    if (LockTest.isDeviceScreenLocked(getApplicationContext())) { // установлен ли пароль на телефоне?
                        if (mEtLogin.getText().toString().length() <= 3) {
                            new MyDialog(getString(R.string.text_dialog_login)).show(getSupportFragmentManager(), "fragmentDialog");
                        } else {
//                            if (!checkPermission()) {
//                                requestPermission();
//                            } else {
//                                getSms();
//                            }
                            getSms();
                            mSmsForm.setVisibility(View.VISIBLE); // открываем форму ввода СМС кода
                        }
                    } else {
                        new LockScreenDialog().show(getSupportFragmentManager(), "fragmentDialog");
                    }
                    break;
                case R.id.btn_enter:
                    if (TextUtils.isEmpty(mEtSms.getText().toString())) {
                        new MyDialog(getString(R.string.text_dialog_sms)).show(getSupportFragmentManager(), "fragmentDialog");
                    } else {
                        getToken();
                    }
                    break;
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.error_msg_no_internet, Toast.LENGTH_LONG).show();
        }
    }

    private void loadHelpSms() {
        RestManager.getApi()
                .getMassage()
                .enqueue(new Callback<LoginHelp>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginHelp> call, @NonNull Response<LoginHelp> response) {
                        RestManager.printResponseLog(response);
                        if (response.isSuccessful() && response.body() != null) {
                            LoginHelp help = response.body();
//                            mTvHelp.setText(help != null ? help.getSms() : null);
                            mTvHelp.setText(help != null ? Html.fromHtml(help.getSms()) : null);
                            faqSpanned = help != null ? Html.fromHtml(
                                    "<b>" + getString(R.string.text_menu_help) + "</b><br><br>" + help.getFaq()) : null;
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginHelp> call, @NonNull Throwable t) {

                    }
                });
    }

    private void getSms() {
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        String strLogin = mEtLogin.getText().toString().trim().toLowerCase().replaceAll("@dtek.com", "");
        mBtnSms.setClickable(false);
        RestManager.getApi()
                .getAuth(API_AUTH_SMS + strLogin)
                .enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                        RestManager.printResponseLog(response);
                        if (response.isSuccessful() && response.body() != null) { // ответ ОК
                            Login login = response.body();
                            if (login != null) { // объект Login существует
                                if (login.isStatus()) { // статус ответа сервера
                                    PreferenceUtils.saveLogin(strLogin);
                                    new MyDialog(login.getText()).show(getSupportFragmentManager(), "fragmentDialog"); // выводим сообщение ошибки
                                    isSmsFormActive = true;
                                }
                            }
                        }
                        mProgressBar.setVisibility(ProgressBar.GONE);
                        mBtnSms.setClickable(true);
                    }

                    @Override
                    public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                        mProgressBar.setVisibility(ProgressBar.GONE);
//                        new MyDialog(t.getMessage()).show(getSupportFragmentManager(), "fragmentDialog");
                        mBtnSms.setClickable(true);
                    }
                });
    }

    private void getToken() {
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        mBtnEnter.setClickable(false);

        String strLogin = mEtLogin.getText().toString().trim().toLowerCase().replaceAll("@dtek.com", "");
        String strSms = mEtSms.getText().toString().trim();
        RestManager.getApi()
                .getAuth( API_AUTH_TOKEN + strLogin + "/" + strSms)
//                .getAuth(API_AUTH_TEST)
                .enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                        mProgressBar.setVisibility(ProgressBar.GONE);
                        RestManager.printResponseLog(response);
                        if (response.isSuccessful() && response.body() != null) { // ответ ОК
                            Login login = response.body();
                            if (login != null) { // объект Login существует
                                if (login.isStatus()) { // статус ответа сервера
                                    PreferenceUtils.saveToken(login.getText()); // записуем токен
                                    PreferenceUtils.saveUserPhone(login.getTel()); // записуем телефон
                                    PreferenceUtils.savePhoneCorporate(login.isCorporate()); // записуем тип телефона
                                    PreferenceUtils.saveIsLogin(true);

                                    getServices();
                                } else { // если сервер выдает false
                                    mEtSms.setText("");
                                    mBtnEnter.setClickable(true);
                                    new MyDialog(login.getText()).show(getSupportFragmentManager(), "fragmentDialog"); // выводим сообщение ошибки
                                }
                            }
                        } else {
                            new MyDialog("Error: " + response.code() + " " + response.message())
                                    .show(getSupportFragmentManager(), "fragmentDialog");
                        }
//                        mProgressBar.setVisibility(ProgressBar.GONE);
                    }

                    @Override
                    public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                        mProgressBar.setVisibility(ProgressBar.GONE);
//                        new MyDialog(t.getMessage()).show(getSupportFragmentManager(), "fragmentDialog");
                        mBtnEnter.setClickable(true);
                    }

                });
    }

    private void getServices() {
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        RestManager.getApi()
                .getServices(API_AUTH_ACCESS + PreferenceUtils.getToken())
                .enqueue(new Callback<ServicesList>() {
                    @Override
                    public void onResponse(@NonNull Call<ServicesList> call, @NonNull Response<ServicesList> response) {
                        RestManager.printResponseLog(response);
                        if (response.isSuccessful() && response.body() != null) { // ответ ОК
                            ServicesList servicesList = response.body();
                            if (servicesList != null && servicesList.isValid()) {
                                PreferenceUtils.saveAccessServices(servicesList);
                                if (!PreferenceUtils.getPushToken().equals(""))
                                    sentPushTokenToServer();
                                else
                                    goMain();
                            }
                        } else {
                            new MyDialog("Error: " + response.code() + " " + response.message())
                                    .show(getSupportFragmentManager(), "fragmentDialog");
                        }
                        mProgressBar.setVisibility(ProgressBar.GONE);
                        mBtnEnter.setClickable(true);
                    }

                    @Override
                    public void onFailure(@NonNull Call<ServicesList> call, @NonNull Throwable t) {
                        new MyDialog(t.getMessage()).show(getSupportFragmentManager(), "fragmentDialog");
                        mProgressBar.setVisibility(ProgressBar.GONE);
                        mBtnEnter.setClickable(true);
                    }
                });
    }

    private void sentPushTokenToServer(){
        mProgressBar.setVisibility(ProgressBar.VISIBLE);

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        Push push = new Push(PreferenceUtils.getLogin(), PreferenceUtils.getPushToken());

        RestManager.getApi()
                .sendPushToken(map, push)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        RestManager.printResponseLog(response);
                        if (response.isSuccessful() && response.body() != null) {
                            goMain();
                            mProgressBar.setVisibility(ProgressBar.GONE);
                            mBtnEnter.setClickable(true);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                        new MyDialog(t.getMessage()).show(getSupportFragmentManager(), "fragmentDialog"); //todo нужно правильно распаристь ответ, но объект в ответе нам не нужен
                        mProgressBar.setVisibility(ProgressBar.GONE);
                        mBtnEnter.setClickable(true);
                        goMain();
                    }
                });
    }


    private void goMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // обнуляем Stack предидущей ACTIVITY
//                                startActivity(intent);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_help:
                if (faqSpanned != null) {
                    new MySpannedDialog(faqSpanned).show(getSupportFragmentManager(), "fragmentDialog");
                } else {
                    new MyDialog(getString(R.string.error_msg_no_internet)).show(getSupportFragmentManager(), "fragmentDialog");
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

