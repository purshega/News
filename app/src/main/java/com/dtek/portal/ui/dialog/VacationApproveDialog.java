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

import com.dtek.portal.R;
import com.dtek.portal.models.hr_vacation.SubordinateList;

@SuppressLint("ValidFragment")
public class VacationApproveDialog extends DialogFragment {

    private String mFio;
    private String mType;
    private String mPeriod;
    private String mComment;

    @SuppressLint("ValidFragment")
    public VacationApproveDialog(SubordinateList.Subordinate subordinate) {
        mFio = subordinate.getName();
        mType = subordinate.getNameVacation();
        mPeriod = subordinate.getPeriod();
        mComment = subordinate.getComment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_leave_approve, null);
//        ButterKnife.bind(this, v);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mFio)
                .setMessage(mType + "\n" + mPeriod + "\n\n" + mComment)
//                .setView(v)
                .setPositiveButton(R.string.text_btn_boss_approve, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendResult(Activity.RESULT_OK); // Передача информации
                    }
                })
                .setNegativeButton(R.string.text_btn_boss_deny, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendResult(Activity.RESULT_CANCELED);
                    }
                })
                .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
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
