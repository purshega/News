package com.dtek.portal.ui.adapter.businees_trips;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.models.business_trips.BTApprover;
import com.dtek.portal.utils.BusinessTrip;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApproversListAdapter extends RecyclerView.Adapter<ApproversListAdapter.ItemViewHolder> {

    Context mContext;
    private List<BTApprover> list = new ArrayList<>();

    @NonNull
    @Override
    public ApproversListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_business_trips_item_list2, parent, false);
        mContext = v.getContext();
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ApproversListAdapter.ItemViewHolder holder, int position) {
        holder.bind(list.get(position), position+1);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(Collection<BTApprover> items) {
        list.addAll(items);
        notifyDataSetChanged();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_name_1)
        TextView tvItemName_1;
        @BindView(R.id.tv_item_name_2)
        TextView tvItemName_2;
        @BindView(R.id.tv_item_name_3)
        TextView tvItemName_3;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        public void bind(BTApprover approver, int position) {
            tvItemName_1.setText("Погоджуючий " + position +":");
            tvItemName_2.setText(approver.getName());
            tvItemName_3.setText(approver.getStatus());

            switch (BusinessTrip.getInstance().getBusinessTrip().getStatusRequest()) {
                case Const.BusinessTrips.BT_STATUS_APPROVING:
                    tvItemName_3.setTextColor(itemView.getResources().getColor(R.color.color_status_orange));
                    break;
                case Const.BusinessTrips.BT_STATUS_NEW:
                    tvItemName_3.setTextColor(itemView.getResources().getColor(R.color.color_status_orange));
                    break;
                case Const.BusinessTrips.BT_STATUS_WAITRATING:
                    tvItemName_3.setTextColor(itemView.getResources().getColor(R.color.color_status_green));
                    break;
                case Const.BusinessTrips.BT_STATUS_APPROVED:
                    tvItemName_3.setTextColor(itemView.getResources().getColor(R.color.color_status_blue));
                    break;
                case Const.BusinessTrips.BT_STATUS_REJECTED:
                    tvItemName_3.setTextColor(itemView.getResources().getColor(R.color.color_status_red));
                    break;
                case Const.BusinessTrips.BT_STATUS_CANCELED:
                    tvItemName_3.setTextColor(itemView.getResources().getColor(R.color.color_status_red));
                    break;
                case Const.BusinessTrips.BT_STATUS_CLOSED:
                    tvItemName_3.setTextColor(itemView.getResources().getColor(R.color.color_status_purple));
                    break;
            }

        }
    }
}
