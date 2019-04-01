package com.dtek.portal.api;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dtek.portal.BuildConfig;
import com.dtek.portal.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.dtek.portal.Const.HTTP.API_BASE_URL;
import static com.dtek.portal.Const.HTTP.BASE_AUTH;

public class RestManager {

    private static final int CONNECT_TIMEOUT = 20; // timeout prod
    private static final int CONNECT_TIMEOUT_DEBUG = 60; // timeout debug
    private static final int DISK_CACHE_SIZE = 10 * 1024 * 1024; // 10MB

    private static ApiService mApiService;

    public static ApiService getApi() {

        if (mApiService == null) {

            Gson gson = new GsonBuilder()
//                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            mApiService = retrofit.create(ApiService.class);
        }
        return mApiService;
    }

    public static ApiService getApiSimpleFormatDate() {
        if (mApiService == null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat("dd.MM.yyyy")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            mApiService = retrofit.create(ApiService.class);
        }
        return mApiService;
    }

    private static OkHttpClient getClient() {
//        Cache cache = new Cache(new File(App.sContext.getCacheDir(), CACHE_DIR), CACHE_SIZE);
        return new OkHttpClient
                .Builder()
//                .sslSocketFactory(getSSLSocketFactory()) // не проверяем сертификат
                .connectTimeout(getTimeout(), TimeUnit.SECONDS)
                .writeTimeout(getTimeout(), TimeUnit.SECONDS)
                .readTimeout(getTimeout(), TimeUnit.SECONDS)
//                .cache(getCache())
                .addInterceptor(getHttpLoggingInterceptor())
                .build();
    }


    private static int getTimeout(){
        if (BuildConfig.DEBUG) {
            return CONNECT_TIMEOUT_DEBUG;
        } else {
            return CONNECT_TIMEOUT;
        }
    }

    private static SSLSocketFactory getSSLSocketFactory() { // не проверяем сертификат https
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return sslSocketFactory;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            return null;
        }

    }

//    private static Cache getCache() {
//        Cache cache = null;
//        // Install an HTTP cache in the application cache directory.
//        try {
//            File cacheDir = new File(App.getCacheDirectory(), "http");
//            cache = new Cache(cacheDir, DISK_CACHE_SIZE);
//        } catch (Exception e) {
//            Timber.e(e, "Unable to install disk cache.");
//        }
//        return cache;
//    }

    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        if (BuildConfig.DEBUG) {
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        } else {
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
//        }
//        return httpLoggingInterceptor;
        return new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ?
                        HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    }

    public static void printResponseLog(@NonNull Response<?> response) {
        if (BuildConfig.DEBUG) {
            Log.d("onResponse", "responseCode " + response.code());
            Log.d("onResponse", "responseBody " + response.body());
            Log.d("onResponse", "responseError " + response.errorBody());
            Log.d("onResponse", "responseMessage " + response.message());
            Log.d("onResponse", "responseSuccessful " + response.isSuccessful());
        }
    }
}
