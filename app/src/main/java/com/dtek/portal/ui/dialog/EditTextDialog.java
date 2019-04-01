package com.dtek.portal.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.dtek.portal.R;

import java.util.Objects;



@SuppressLint("ValidFragment")
public class EditTextDialog extends DialogFragment {
    public static final String EXTRA_STRING = "com.dtek.portal.string"; // помещаем string возврата

    private String mTitle;
    private String mBtnPositiveName;


    @SuppressLint("ValidFragment")
    public EditTextDialog(String title, String buttonPositiveName) {
        mTitle = title;
        mBtnPositiveName = buttonPositiveName;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle(R.string.text_reason_return)
        builder.setTitle(mTitle)
                .setView(inflater.inflate(R.layout.frag_edit_name, null))
//                .setPositiveButton(R.string.btn_it_return_ok, new DialogInterface.OnClickListener() {
                .setPositiveButton(mBtnPositiveName, (dialog, id) -> {
                    EditText et = EditTextDialog.this.getDialog().findViewById(R.id.et_tv);
                    String value = et.getText().toString();

                    sendResult(Activity.RESULT_OK, value); // Передача информации
                })
                .setNegativeButton(android.R.string.cancel, (dialog, id) -> {
                });
        return builder.create();
    }

    // Обратный вызов целевого фрагмента
    private void sendResult(int resultCode, String s) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent(); // создает интент,
        intent.putExtra(EXTRA_STRING, s); // помещает в него дату как дополнение,
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent); //вызывает CrimeFragment.onActivityResult(…)
    }

}
