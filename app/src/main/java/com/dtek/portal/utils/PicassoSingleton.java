package com.dtek.portal.utils;


import android.content.Context;

import com.squareup.picasso.Picasso;

public class PicassoSingleton {

    private static Picasso instance;

    public static Picasso getInstance(Context context) {
        if (instance == null) {

            instance = new Picasso.Builder(context)
//                    .executor(Executors.newSingleThreadExecutor())
                    .downloader(new Base64ImageDownloader())
//                    .memoryCache(Cache.NONE)
//                    .indicatorsEnabled(true)
                    .build();
        }
        return instance;
    }
}
