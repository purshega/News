package com.dtek.portal.callback;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.dtek.portal.models.car.Passenger;

import java.util.List;

public class PassengerDiffCallback extends DiffUtil.Callback {

    private final List<Passenger> mOldPassengerList;
    private final List<Passenger> mNewPassengerList;

    public PassengerDiffCallback(List<Passenger> oldPassengerList, List<Passenger> newPassengerList) {
        this.mOldPassengerList = oldPassengerList;
        this.mNewPassengerList = newPassengerList;
    }

    @Override
    public int getOldListSize() {
        return mOldPassengerList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewPassengerList.size();
    }

//    @Override
//    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//        return false;
//    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Passenger oldPassenger = mOldPassengerList.get(oldItemPosition);
        Passenger newPassenger = mNewPassengerList.get(newItemPosition);
        return oldPassenger.getNumber() == newPassenger.getNumber();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Passenger oldPassenger = mOldPassengerList.get(oldItemPosition);
        final Passenger newPassenger = mNewPassengerList.get(newItemPosition);

        boolean b = oldPassenger.getNumber() == newPassenger.getNumber() &&
                oldPassenger.getType().equals(newPassenger.getType()) &&
                oldPassenger.getFio().equals(newPassenger.getFio()) &&
                oldPassenger.isSms() == newPassenger.isSms() &&
                oldPassenger.getPhone().equals(newPassenger.getPhone()) &&
                oldPassenger.getComment().equals(newPassenger.getComment());
        return b;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
