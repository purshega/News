package com.dtek.portal.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.dtek.portal.R;

@SuppressLint("ValidFragment")
public class ConfirmDialog extends DialogFragment {

    private String mString;

    @SuppressLint("ValidFragment")
    public ConfirmDialog(String string) {
        mString = string;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mString)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                    sendResult(Activity.RESULT_OK); // Передача информации
                })
                .setNegativeButton(android.R.string.cancel, (dialog, id) -> {
                });
        return builder.create();
    }

    // Обратный вызов целевого фрагмента
    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent(); // создает интент,
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent); //вызывает CrimeFragment.onActivityResult(…)
    }

}
