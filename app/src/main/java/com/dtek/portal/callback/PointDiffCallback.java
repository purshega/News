package com.dtek.portal.callback;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.dtek.portal.models.car.Point;

import java.util.List;

public class PointDiffCallback extends DiffUtil.Callback {

    private final List<Point> mOldPointList;
    private final List<Point> mNewPointList;

    public PointDiffCallback(List<Point> oldPointList, List<Point> newPointList) {
        this.mOldPointList = oldPointList;
        this.mNewPointList = newPointList;
    }

    @Override
    public int getOldListSize() {
        return mOldPointList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewPointList.size();
    }

//    @Override
//    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//        return false;
//    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Point oldPoint = mOldPointList.get(oldItemPosition);
        Point newPoint = mNewPointList.get(newItemPosition);
        return oldPoint.getNumber() == newPoint.getNumber();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Point oldPoint = mOldPointList.get(oldItemPosition);
        final Point newPoint = mNewPointList.get(newItemPosition);

        boolean b = oldPoint.getNumber() == newPoint.getNumber() &&
                oldPoint.getAddress().equals(newPoint.getAddress()) &&
                oldPoint.getDatePoint() == newPoint.getDatePoint() &&
                oldPoint.getWaitOrTakeAway().equals(newPoint.getWaitOrTakeAway()) &&
                oldPoint.getWaitMinutes().equals(newPoint.getWaitMinutes()) &&
                oldPoint.getDateTakeAway() == newPoint.getDateTakeAway();
        return b;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
