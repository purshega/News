package com.dtek.portal.database;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.news.News;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.*;

public class SaveIntoDatabase extends AsyncTask<News, Void, Void> {

    private final String TAG = SaveIntoDatabase.class.getSimpleName();
    private DatabaseHelper mDatabase;
    private String mTable;

    public SaveIntoDatabase(DatabaseHelper database, String table) {
        mDatabase = database;
        mTable = table;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(News... params) {

        News news = params[0];

        try {
            if (news.getCategory().equals(NEWS_CAT_DTEK)) {
                if (news.getImageLink() != null && !(news.getImageLink().equals(""))) {

                    Map<String, String> map = new HashMap<>();
//                    map.put(API_AUTHORITY, App.prefs.getToken());
                    map.put(API_AUTHORITY, PreferenceUtils.getToken());

                    Response response = RestManager.getApi()
                            .getImageHtml(map, NEWS_BASE64 + news.getImageLink() + NEWS_PARAM_IMAGE)
                            .execute();
                    if (response.isSuccessful() && response.body() != null) {
                        String imageAsBase64 = response.body().toString();
                        Bitmap bitmap = Utils.getBitmapFromByte(Utils.getBytesFromBase64(imageAsBase64));
                        news.setPicture(bitmap);
                    }
                }
            }
//            news.setFromDatabase(true);
            mDatabase.addItemNews(news, mTable);
            Log.i(TAG, "doInBackground: add - " + mTable);

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        return null;
    }
}
