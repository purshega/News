package com.dtek.portal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dtek.portal.BuildConfig;
import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.database.DatabaseHelper;
import com.dtek.portal.database.RemoveTableDatabase;
import com.dtek.portal.models.login.ServicePortal;
import com.dtek.portal.models.login.ServicesList;
import com.dtek.portal.models.push.PushData;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.DATABASE.TABLE_NAME_NEWS_COMPANY;
import static com.dtek.portal.Const.DATABASE.TABLE_NAME_NEWS_DTEK;
import static com.dtek.portal.Const.DATABASE.TABLE_NAME_NEWS_NO_MISS;
import static com.dtek.portal.Const.HTTP.API_AUTH_ACCESS;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.imageView6)
    ImageView imageView6;
    @BindView(R.id.imageView7)
    ImageView imageView7;
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    private CountDownTimer timer;
    private DatabaseHelper mDatabase;
    private String data_url = "";
    private Intent intent;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        intent = new Intent(SplashActivity.this, MainActivity.class);

        checkQrData();
        checkPushData();

        getVersion(mTvVersion, getApplicationContext());

        animation();
        removeCacheNews();
        startTimerApp(2500, 2500);

    }

    public static void getVersion(TextView txtVersion, Context context) {
//        txtVersion.setText(String.format("%s\n%s", context.getString(R.string.app_version, BuildConfig.VERSION_NAME),
//                context.getString(R.string.app_copyright)));
        txtVersion.setText(context.getString(R.string.app_version, BuildConfig.VERSION_NAME));
    }

    private void animation() {
        startAnim(300, 300, imageView1);
        startAnim(600, 600, imageView2);
        startAnim(900, 900, imageView3);
        startAnim(1200, 1200, imageView4);
        startAnim(1500, 1500, imageView5);
        startAnim(1800, 1800, imageView6);
        startAnim(2100, 2100, imageView7);
    }

    private void removeCacheNews() {
        Date currentTime = Calendar.getInstance().getTime();
        int day = currentTime.getDate(); // число месяца

        if (day != PreferenceUtils.getDay()) {
            mDatabase = new DatabaseHelper(this);
            RemoveTableDatabase taskDel1 = new RemoveTableDatabase(mDatabase);
            taskDel1.execute(TABLE_NAME_NEWS_DTEK);

            RemoveTableDatabase taskDel2 = new RemoveTableDatabase(mDatabase);
            taskDel2.execute(TABLE_NAME_NEWS_NO_MISS);

            RemoveTableDatabase taskDel3 = new RemoveTableDatabase(mDatabase);
            taskDel3.execute(TABLE_NAME_NEWS_COMPANY);

            PreferenceUtils.saveDay(day);
        }
    }

    private void startTimerApp(int finish, int tick) {
        timer = new CountDownTimer(finish, tick) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (Utils.isNetworkAvailable(getApplicationContext())) {
                    if (PreferenceUtils.getToken().equals("")) {
                        goToMain();
                    } else {
                        getServices();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
//                    goToMain();
                }
            }
        }.start();
    }

    private void checkQrData() {
        if(getIntent().getData()!=null){
            data_url = getIntent().getData().toString();
            intent.putExtra(Const.QR.QR_DATA, data_url);
        }
//        try {
//            ServicesList servicesList = PreferenceUtils.getAccessServices();
//            for (ServicePortal servicePortal : servicesList.getServices()) {
//                if (servicePortal.getServiceName().equals(MainActivity.QR)) {
//                    data_url = getIntent().getData().toString();
//                    intent.putExtra(Const.QR.QR_DATA, data_url);
//                    break;
//                }
//            }
//        } catch (NullPointerException e) {
//        }
    }

    private void checkPushData(){
        if(getIntent().getStringExtra(Const.PUSH.DATA_TYPE)!=null && getIntent().getStringExtra(Const.PUSH.JSON_BODY)!=null ){
            intent.putExtra(Const.PUSH.DATA_TYPE, getIntent().getStringExtra(Const.PUSH.DATA_TYPE));
            intent.putExtra(Const.PUSH.JSON_BODY, getIntent().getStringExtra(Const.PUSH.JSON_BODY));
        }
    }

    private void goToMain() {
        startActivity(intent);
        finish();
    }

    private void startAnim(int finish, int tick, final ImageView imageView) {
        new CountDownTimer(finish, tick) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
                animation.setDuration(500);
                animation.setInterpolator(new DecelerateInterpolator());
                imageView.setAlpha(1f);
                imageView.startAnimation(animation);
            }
        }.start();
    }

    public void getServices() {
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
                            } else {
                                PreferenceUtils.saveToken("");
                                PreferenceUtils.saveAccessServices(null);
                            }
                        } else {
                            new MyDialog("Error: " + response.code() + " " + response.message())
                                    .show(getSupportFragmentManager(), "fragmentDialog");
                        }
                        goToMain();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ServicesList> call, @NonNull Throwable t) {
                        new MyDialog(t.getMessage()).show(getSupportFragmentManager(), "fragmentDialog");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
        if (mDatabase != null) mDatabase.close();
    }
}
