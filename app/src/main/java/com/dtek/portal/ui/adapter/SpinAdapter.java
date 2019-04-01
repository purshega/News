package com.dtek.portal.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dtek.portal.R;

import java.util.List;

public class SpinAdapter<T> extends ArrayAdapter<T> {
    private Context context;
    private List<T> values;

    public SpinAdapter(Context context, int textViewResourceId, List<T> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount() {
        return values == null ? 0 : values.size();
    }

    public T getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_spinner, null);

        ((TextView) view.findViewById(android.R.id.text1))
                .setText(values.toArray(new Object[values.size()])[position].toString());
        return view;
//        TextView label = new TextView(context);
//        label.setTextColor(Color.BLACK);
//        label.setTextSize(16);
//        label.setText(values.toArray(new Object[values.size()])[position].toString());
//        return label;

    }

//    @Override
//    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
//        TextView label = new TextView(context);
//        label.setTextColor(Color.BLACK);
//        label.setTextSize(16);
//        label.setText(values.toArray(new Object[values.size()])[position].toString());
//
//        return label;
//    }
}
