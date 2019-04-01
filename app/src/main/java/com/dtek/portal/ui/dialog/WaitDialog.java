package com.dtek.portal.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dtek.portal.R;


public class WaitDialog {

    private static TextView downloadProgressText;

    public static ProgressDialog showDialog(Context context, String message){
        ProgressDialog mDialog = new ProgressDialog(context);
        mDialog.setMessage(message);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setCancelable(false);
        return mDialog;
    }

    public static Dialog setDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(false);
        return builder.create();
    }

    public static Dialog setDialogWithProgress(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(getView(context));
        builder.setCancelable(true);
        return builder.create();
    }

    private static View getView(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        assert inflater != null;
        View view = inflater.inflate( R.layout.progress_dialog, null );
        downloadProgressText = view.findViewById(R.id.loading_msg);
        return view;
    }

    public static void setProgressText(String text) {
        WaitDialog.downloadProgressText.setText("Загружено  " + text + " %");
    }
}
