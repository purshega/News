package com.dtek.portal.database;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;

import com.dtek.portal.callback.NewsFetchListener;
import com.dtek.portal.models.news.News;
import com.dtek.portal.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.dtek.portal.Const.DATABASE.*;

//получение постов из DB
public class PostFetcher extends Thread {

    private final NewsFetchListener mListener;
    private final SQLiteDatabase mDb;
    private final String mTable;


    public PostFetcher(NewsFetchListener listener, SQLiteDatabase db, String table) {
        mListener = listener;
        mDb = db;
        mTable = table;
    }

//записуем данные в колонки
    @Override
    public void run() {
        Cursor cursor = mDb.rawQuery(GET_NEWS_QUERY + mTable, null);

        final List<News> newsList = new ArrayList<>();

        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                do {
                    News news = new News();
//                    news.setFromDatabase(true);
                    news.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(NEWS_ID))));
                    news.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                    news.setCategory(cursor.getString(cursor.getColumnIndex(CATEGORY)));
                    news.setSubtitle(cursor.getString(cursor.getColumnIndex(SUBTITLE)));
                    news.setPublishedDate(cursor.getString(cursor.getColumnIndex(PUBLISHED_DATE)));
                    news.setImageLink(cursor.getString(cursor.getColumnIndex(IMAGE_URL)));
                    byte[] blob = cursor.getBlob(cursor.getColumnIndex(PHOTO));
                    if (blob !=null) {
                        news.setPicture(Utils.getBitmapFromByte(blob));
                    }

                    newsList.add(news);
                    publishPost(news);

                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onDeliverAllPosts(newsList);
                mListener.onHideDialog();
            }
        });
    }

    public void publishPost(final News news) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onDeliverPost(news);
            }
        });
    }
}
