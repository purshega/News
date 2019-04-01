package com.dtek.portal.database;


import android.os.AsyncTask;
import android.util.Log;

public class RemoveTableDatabase extends AsyncTask<String, Void, Void> {

    private final String TAG = RemoveTableDatabase.class.getSimpleName();
    private DatabaseHelper mDatabase;

    public RemoveTableDatabase(DatabaseHelper database) {
        mDatabase = database;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {

        String tableName = params[0];

        try {
            mDatabase.removeTable(tableName);
            Log.i(TAG, "doInBackground: remove - " + tableName);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return null;
    }
}
