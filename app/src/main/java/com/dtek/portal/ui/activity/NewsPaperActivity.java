package com.dtek.portal.ui.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.newspaper.Download;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.ui.fragment.newspaper.adapter.NewsPaperPageAdapter;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.API_BASE_URL;
import static com.dtek.portal.Const.HTTP.NEWS_BASE64;

public class NewsPaperActivity extends AppCompatActivity {

    @BindView(R.id.container)
    ViewPager viewPager;

//    @BindView(R.id.pdfView)
//    PDFView pdfView;

    public static final String MESSAGE_PROGRESS = "message_progress";
    public static final String MESSAGE_ERROR = "message_error";
    public static final String MESSAGE_COMPLETED = "message_completed";

    private String newspaper_url;

    private static final int REQUEST_PERMISSION_CODE = 1;
    public static PdfRenderer pdfRenderer;
    public static PdfRenderer.Page currentPage;
    private ParcelFileDescriptor parcelFileDescriptor;

    private Unbinder unbinder;
    private Dialog waitDialog;

    private String newspaper_name;
    private File pdf_file;
    private Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper);
        registerReceiver();
        unbinder = ButterKnife.bind(this);
        initDialog();
        newspaper_url = getIntent().getStringExtra(Const.HTTP.NEWSPAPER_URL);
        newspaper_name = getIntent().getStringExtra(Const.PDF_NEWSPAPER.NEWSPAPER_NAME);

        if (isCheckPermission()) {
            initPdfFile();
        } else {
            requestPermission();
        }
    }

    private void initPdfFile() {
        try {
            pdf_file = new File(this.getExternalFilesDir(Const.PDF_NEWSPAPER.FILE_NAME), newspaper_name );
            if (!pdf_file.exists()) {
//                pdf_file.createNewFile();
                loadNewspaper();  // разгрузка газеты без прогресса загрузки
                // разгрузка газеты с прогрессом загрузки
//                waitDialog.show();
//                Intent intent = new Intent(this, DownloadService.class);
//                intent.putExtra("url",newspaper_url);
//                intent.putExtra("newspaperName",newspaper_name);
//                startService(intent);
            } else
                openPdfRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openPdfRenderer() throws IOException {
        if(pdf_file!=null)
            //чтение газеты через стороннюю библиотеку, но размер приложения на 16мб больше
//        pdfView.fromFile(pdf_file)
//                .onLoad(loadListener)
//                .load();
        parcelFileDescriptor = ParcelFileDescriptor.open(pdf_file, ParcelFileDescriptor.MODE_READ_ONLY);
        if (parcelFileDescriptor != null && viewPager != null) {
            pdfRenderer = new PdfRenderer(parcelFileDescriptor);
            NewsPaperPageAdapter newsPaperAdapter = new NewsPaperPageAdapter(getSupportFragmentManager());
            viewPager.setOffscreenPageLimit(1);
            viewPager.setAdapter(newsPaperAdapter);
        }
    }

    private void loadNewspaper() {
        waitDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        call = RestManager.getApi()
                .getImgForFirstPageNewspaper(map, API_BASE_URL + NEWS_BASE64 + newspaper_url);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        String strBase64 = response.body();
                        if (response.isSuccessful() && response.body() != null) {
                            byte[] fileData = Utils.getBytesFromBase64(strBase64);
                            try {
                                pdf_file.createNewFile();
                                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(pdf_file));
                                bos.write(fileData);
                                bos.flush();
                                bos.close();
                                openPdfRenderer();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (waitDialog != null)
                            waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        if (waitDialog != null)
                            waitDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Не удалось загрузить газету", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initDialog() {
        waitDialog = WaitDialog.setDialogWithProgress(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if(call!=null) {
            call.cancel();
        }

    }

    public boolean isCheckPermission() {
        int result = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean isStoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (isStoragePermission) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
                        initPdfFile();
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private void registerReceiver(){
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (Objects.requireNonNull(intent.getAction())){
                case MESSAGE_PROGRESS:
                    Download download = intent.getParcelableExtra("download");
                    WaitDialog.setProgressText(download.getProgress()+"");
                    if(download.getProgress()==101) {
                        try {
                            openPdfRenderer();
                            broadcastReceiver.clearAbortBroadcast();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        waitDialog.dismiss();
                    }
                    break;
            }
        }
    };
}