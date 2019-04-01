package com.dtek.portal.downloadProgress.service;

import android.app.IntentService;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dtek.portal.Const;
import com.dtek.portal.api.ApiService;
import com.dtek.portal.models.newspaper.Download;
import com.dtek.portal.ui.activity.NewsPaperActivity;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.API_BASE_URL;
import static com.dtek.portal.Const.HTTP.NEWS_BASE64;

public class DownloadService extends IntentService implements LifecycleObserver {
    private int totalFileSize;
    private String newspaper_url;
    private String newspaper_name;
    private File pdf_file;

    private Call <ResponseBody> request;

    public DownloadService() {
        super("Download Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        newspaper_url = Objects.requireNonNull(intent).getStringExtra("url");
        newspaper_name = Objects.requireNonNull(intent).getStringExtra("newspaperName");
        initPdfFile();
        initDownload();
    }

    private void initPdfFile() {
        try {
            pdf_file = new File(this.getExternalFilesDir(Const.PDF_NEWSPAPER.FILE_NAME), newspaper_name);
            if (!pdf_file.exists()) {
                pdf_file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDownload(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .build();

        ApiService retrofitInterface = retrofit.create(ApiService.class);

        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        request = retrofitInterface.getNewspaper(map,API_BASE_URL + NEWS_BASE64 + newspaper_url);
        try {
            downloadFile(request.execute().body());
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void downloadFile(ResponseBody body) throws IOException {

        int count;
        byte data[] = new byte[512 * 4];
        long fileSize = body.contentLength();
        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
        OutputStream output = new FileOutputStream(pdf_file);
        long total = 0;
        long startTime = System.currentTimeMillis();
        int timeCount = 1;
        while ((count = bis.read(data)) != -1) {

            total += count;
            totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
            double current = Math.round(total / (Math.pow(1024, 2)));

            int progress = (int) ((total * 100) / fileSize);

            long currentTime = System.currentTimeMillis() - startTime;

            Download download = new Download();
            download.setTotalFileSize(totalFileSize);

            if (currentTime > 2 * timeCount) {

                download.setCurrentFileSize((int) current);
                download.setProgress(progress);
                sendIntent(download);
                timeCount++;
            }

            output.write(data, 0, count);
        }
        output.flush();
        output.close();
        bis.close();
        String strBase64 = getStringFromFile();
        decodeToPdfFormat(strBase64);
        onDownloadComplete();

    }

    private String getStringFromFile(){
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(pdf_file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
        }
        return text.toString();
    }

    private void decodeToPdfFormat(String strBase64) {
        byte[] fileData = Utils.getBytesFromBase64(strBase64);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(pdf_file));
            bos.write(fileData);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendIntent(Download download){
        Intent intent = new Intent(NewsPaperActivity.MESSAGE_PROGRESS);
        intent.putExtra("download",download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(){
        Download download = new Download();
        download.setProgress(101);
        sendIntent(download);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void disconnect(){
        if(request!=null)
            request.cancel();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void connect(){
    }
}
