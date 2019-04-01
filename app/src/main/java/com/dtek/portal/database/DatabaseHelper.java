package com.dtek.portal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dtek.portal.callback.NewsFetchListener;
import com.dtek.portal.models.news.News;
import com.dtek.portal.utils.Utils;

import static com.dtek.portal.Const.DATABASE.*;
import static com.dtek.portal.Const.createTable;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(createTable(TABLE_NAME_NEWS_DTEK));
            db.execSQL(createTable(TABLE_NAME_NEWS_NO_MISS));
            db.execSQL(createTable(TABLE_NAME_NEWS_COMPANY));
        } catch (SQLException ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(Const.DATABASE.DROP_QUERY); // удаляет одну или несколько таблиц
//        this.onCreate(db);
    }

    public void removeTable(String tableName) {

        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        try {
            db.execSQL(DROP_QUERY + tableName); // удаляет одну или несколько таблиц
            db.execSQL(createTable(tableName));
            this.onCreate(db);
//            db.delete(tableName, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    //добавляем новость в БД
    public void addItemNews(News news, String table) {

        Log.d(TAG, "Values Got " + news.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NEWS_ID, news.getId());
        values.put(TITLE, news.getTitle());
        values.put(CATEGORY, news.getCategory());
        values.put(SUBTITLE, news.getSubtitle());
        values.put(PUBLISHED_DATE, news.getPublishedDate());
        if (!news.getImageLink().equals("")) {
            values.put(IMAGE_URL, news.getImageLink());
            if (news.getPicture() != null) {
                values.put(PHOTO, Utils.getPictureByteOfArray(news.getPicture()));
            }
        }

        try {
            db.insert(table, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
    }

    public void fetchPosts(NewsFetchListener listener, String table) {
        PostFetcher fetcher = new PostFetcher(listener, this.getWritableDatabase(), table);
        fetcher.start();
    }

//    public class PostFetcher extends Thread {
//
//        private final NewsFetchListener mListener;
//        private final SQLiteDatabase mDb;
//
//        public PostFetcher(NewsFetchListener listener, SQLiteDatabase db) {
//            mListener = listener;
//            mDb = db;
//        }
//
//        @Override
//        public void run() {
//            Cursor cursor = mDb.rawQuery(Const.DATABASE.GET_NEWS_QUERY, null);
//
//            final List<News> postList = new ArrayList<>();
//
//            if (cursor.getCount() > 0) {
//
//                if (cursor.moveToFirst()) {
//                    do {
//                        News post = new News();
//                        post.setFromDatabase(true);
//                        post.setName(cursor.getString(cursor.getColumnIndex(Const.DATABASE.NAME)));
//                        post.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Const.DATABASE.PRICE))));
//                        post.setInstructions(cursor.getString(cursor.getColumnIndex(Const.DATABASE.INSTRUCTIONS)));
//                        post.setCategory(cursor.getString(cursor.getColumnIndex(Const.DATABASE.CATEGORY)));
//                        post.setPicture(Utils.getBitmapFromByte(cursor.getBlob(cursor.getColumnIndex(Const.DATABASE.PHOTO))));
//                        post.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Const.DATABASE.PRODUCT_ID))));
//                        post.setPhoto(cursor.getString(cursor.getColumnIndex(Const.DATABASE.PHOTO_URL)));
//
//                        postList.add(post);
//                        publishPost(post);
//
//                    } while (cursor.moveToNext());
//                }
//            }
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mListener.onDeliverAllPosts(postList);
//                    mListener.onHideDialog();
//                }
//            });
//        }
//
//        public void publishPost(final News post) {
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mListener.onDeliverPost(post);
//                }
//            });
//        }
//    }
}
