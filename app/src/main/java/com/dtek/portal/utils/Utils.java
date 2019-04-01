package com.dtek.portal.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import com.dtek.portal.api.RestManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;

public class Utils {

    //проверка инета
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (cm != null) {
            networkInfo = cm.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    // URL -> Base64
    public static String getBase64FromUrl(String url) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        String strBase64 = null;
        retrofit2.Response<String> response = RestManager.getApi()
                .getImageHtml(map, url)
                .execute();
        if (response.isSuccessful() && response.body() != null) {
            strBase64 = response.body();
        }
        return strBase64;
    }

    // Base64 -> bytes
    public static byte[] getBytesFromBase64(String strBase64) {
        byte[] bytes = new byte[0];
        try {
            bytes = Base64.decode(strBase64, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    //  byte -> bitmap
    public static Bitmap getBitmapFromByte(byte[] image) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    // bitmap -> byte
    public static byte[] getPictureByteOfArray(Bitmap bitmap) {
        byte[] bytes = new byte[0];
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
            bytes = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

}
