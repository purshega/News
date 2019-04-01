package com.dtek.portal.utils;

import android.graphics.Bitmap;
import android.net.Uri;

import com.squareup.picasso.Downloader;

import java.io.IOException;

public class Base64ImageDownloader implements Downloader {

    @Override
    public Response load(Uri uri, int networkPolicy) throws IOException {
        try {
            String url = uri.toString();
            String strBase64 = Utils.getBase64FromUrl(url);
            byte[] imageBytes = Utils.getBytesFromBase64(strBase64);
            Bitmap bitmap = Utils.getBitmapFromByte(imageBytes);


            Response resp = new Response(bitmap, true);
            return resp;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void shutdown() {

    }
}
