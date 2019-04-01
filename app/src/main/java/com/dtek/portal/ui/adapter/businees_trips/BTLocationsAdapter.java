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
import com.dtek.portal.models.business_trips.BTLocation;

import java.util.ArrayList;
import java.util.List;

public class BTLocationsAdapter extends ArrayAdapter<BTLocation> {

    private Context mContext;
    private int mResource;
    private List<BTLocation> list, tempItems, suggestions;
    private LayoutInflater mInflater;

    public BTLocationsAdapter(Context context, int resource, List<BTLocation> list) {
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

        BTLocation bsLocation = list.get(position);

        ((TextView) view.findViewById(R.id.tv_city)).setText(bsLocation.getCity().getName());
        ((TextView) view.findViewById(R.id.tv_country_region)).setText(bsLocation.getCountry().getName() + ", " + bsLocation.getRegion().getName());

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
            String str = ((BTLocation) resultValue).getCity().getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (BTLocation bsLocation : tempItems) {
                    suggestions.add(bsLocation);
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
            List<BTLocation> filterList = (ArrayList<BTLocation>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (BTLocation bsLocation : filterList) {
                    add(bsLocation);
                    notifyDataSetChanged();
                }
            }
        }
    };
}


