package com.dtek.portal.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import com.dtek.portal.R;

public class LockScreenDialog extends DialogFragment {
    public static final String EXTRA_STRING = "com.dtek.portal.string"; // помещаем string возврата


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        builder.setTitle(R.string.text_title_lock_screen)
//                .setView(view)
                .setMessage(R.string.text_message_lock_screen)
                .setNegativeButton(R.string.text_title_exit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();
                    }
                })
                .setPositiveButton(R.string.text_btn_lock_screen, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD); // вызов настроек блокировки экрана
                        startActivity(intent);
                    }
                });
//        return builder.create();
        AlertDialog alert = builder.create();
        setCancelable(false);
        alert.setCanceledOnTouchOutside(false);

        return alert;
    }
}
