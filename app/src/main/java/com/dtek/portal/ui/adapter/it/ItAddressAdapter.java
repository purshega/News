package com.dtek.portal.ui.adapter.it;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.itsm.ItAddressInfo;

import java.util.ArrayList;
import java.util.List;

public class ItAddressAdapter extends ArrayAdapter<ItAddressInfo> {

    private Context mContext;
    private List<ItAddressInfo> list, tempItems, suggestions;
    private int mResource;
    private LayoutInflater mInflater;

    public ItAddressAdapter(Context context, int resource, List<ItAddressInfo> list) {
        super(context, resource, list);
        this.mContext = context;
        this.mResource = resource;
        this.list = list;
        tempItems = new ArrayList<ItAddressInfo>(list); // this makes the difference.
        suggestions = new ArrayList<ItAddressInfo>();
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = mInflater.inflate(mResource, null);

        ItAddressInfo address = list.get(position);

        ((TextView) view.findViewById(R.id.tv_street)).setText(address.getStreet());
        ((TextView) view.findViewById(R.id.tv_city)).setText(address.getCity());
//        ((TextView) view.findViewById(R.id.tv_code)).setText(address.getAddresscode());

        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return streetFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    private Filter streetFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((ItAddressInfo) resultValue).getStreet();
//            String str = ((ItAddressInfo) resultValue).getCity()+ ", " + ((ItAddressInfo) resultValue).getStreet();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (ItAddressInfo address : tempItems) {
                    if (address.getStreet().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(address);
                    }
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
            List<ItAddressInfo> filterList = (ArrayList<ItAddressInfo>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (ItAddressInfo address : filterList) {
                    add(address);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
