package com.dtek.portal.ui.adapter.businees_trips;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.business_trips.BTEmployee;

import java.util.ArrayList;
import java.util.List;

public class BTEmployeesAdapter extends ArrayAdapter<BTEmployee> {

    private Context mContext;
    private int mResource;
    private List<BTEmployee> list, tempItems, suggestions;
    private LayoutInflater mInflater;

    public BTEmployeesAdapter(Context context, int resource, List<BTEmployee> list) {
        super(context, resource, list);
        this.mContext = context;
        this.list = list;
        mResource = resource;
        mInflater = LayoutInflater.from(mContext);
        tempItems = new ArrayList<>(list); // this makes the difference.
        suggestions = new ArrayList<>();
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = mInflater.inflate(mResource, null);

        BTEmployee employee = list.get(position);

        ((TextView) view.findViewById(R.id.tv_name)).setText(employee.getFullName());
        ((TextView) view.findViewById(R.id.tv_role)).setText(employee.getPosition());
        ((TextView) view.findViewById(R.id.tv_company)).setText(employee.getCompany());
        if (employee.getDepartment() != null && !employee.getDepartment().equals(""))
            ((TextView) view.findViewById(R.id.tv_department)).setText(employee.getDepartment());
        else
            view.findViewById(R.id.tv_department).setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.tv_number)).setText(employee.getPersonnelNumber() + "");

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
            String str = ((BTEmployee) resultValue).getSurname();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (BTEmployee employee : tempItems) {
                    suggestions.add(employee);
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
            List<BTEmployee> filterList = (ArrayList<BTEmployee>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (BTEmployee employee : filterList) {
                    add(employee);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
