package com.dtek.portal.ui.adapter.businees_trips;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.business_trips.BTHotel;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.fragment.business_trip.AddHotelFragment;
import com.dtek.portal.utils.ConvertTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotelsListAdapter extends RecyclerView.Adapter<HotelsListAdapter.ItemViewHolder> {

    Context mContext;
    private List<BTHotel> list = new ArrayList<>();
    private Boolean isClick = true;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_business_trips_item_list, parent, false);
        mContext = v.getContext();
        final ItemViewHolder holder = new ItemViewHolder(v);
        holder.itemView.setOnClickListener(v1 -> {
            if(isClick) {
                ((MainActivity) mContext).switchToFragment(new AddHotelFragment(list.get(holder.getAdapterPosition())), false);
                ((MainActivity) mContext).mToolbarTitle.setText(mContext.getResources().getString(R.string.final_destination));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(Collection<BTHotel> items) {
        list.addAll(items);
        notifyDataSetChanged();
    }

    public void setClick(Boolean click) {
        isClick = click;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_name_1)
        TextView tvItemName_1;
        @BindView(R.id.tv_item_name_value_1)
        TextView tvItemNameValue_1;
        @BindView(R.id.tv_item_name_2)
        TextView tvItemName_2;
        @BindView(R.id.tv_item_name_value_2)
        TextView tvItemNameValue_2;
        @BindView(R.id.tv_item_name_3)
        TextView tvItemName_3;
        @BindView(R.id.tv_item_name_value_3)
        TextView tvItemNameValue_3;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        public void bind(BTHotel hotel) {
            tvItemName_1.setText(mContext.getResources().getString(R.string.settlement) + ": ");
            tvItemNameValue_1.setText(hotel.getLocation().getCity().getName());
            tvItemName_2.setText(mContext.getResources().getString(R.string.date_of_departure) + ": ");
            tvItemNameValue_2.setText(ConvertTime.dateToString(hotel.getArrivalDate()));
            tvItemName_3.setText(mContext.getResources().getString(R.string.date_of_departure) + ": ");
            tvItemNameValue_3.setText(ConvertTime.dateToString(hotel.getDepartureDate()));
        }
    }
}
