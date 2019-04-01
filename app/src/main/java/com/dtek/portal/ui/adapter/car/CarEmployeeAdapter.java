package com.dtek.portal.ui.adapter.car;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.car.Employee;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class CarEmployeeAdapter extends ArrayAdapter<Employee> {

    private Context mContext;
    private List<Employee> list, tempItems, suggestions;
    private int mResource;
    private LayoutInflater mInflater;

    public CarEmployeeAdapter(Context context, int resource, List<Employee> list) {
        super(context, resource, list);
        this.mContext = context;
        this.mResource = resource;
        this.list = list;
        tempItems = new ArrayList<Employee>(list); // this makes the difference.
        suggestions = new ArrayList<Employee>();
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = mInflater.inflate(mResource, null);

        Employee employee = list.get(position);

//        ((TextView) view.findViewById(android.R.id.text1)).setText(employee.getFio());
        ((TextView) view.findViewById(R.id.tv_fio)).setText(employee.getFio());
        ((TextView) view.findViewById(R.id.tv_login)).setText(employee.getLogin());
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return foiFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    private Filter foiFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((Employee) resultValue).getFio();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Employee employee : tempItems) {
//                    if (employee.getFio().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(employee);
//                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Employee> filterList = (ArrayList<Employee>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Employee employee : filterList) {
                    add(employee);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
