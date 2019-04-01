package com.dtek.portal.ui.adapter.car;


import android.content.Context;
import android.widget.ArrayAdapter;

public class CarSpinnerAdapter extends ArrayAdapter<String> {

    public CarSpinnerAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}
