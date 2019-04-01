package com.dtek.portal.ui.adapter.businees_trips;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.models.business_trips.BTPreview;
import com.dtek.portal.utils.ConvertTime;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseBTAdapter extends RecyclerView.Adapter<BaseBTAdapter.ViewHolder> {

    private List<BTPreview> btList = new ArrayList<>();

    public BaseBTAdapter() {
    }

    // Создает новые views (вызывается layout manager-ом)
    @NonNull
    @Override
    public BaseBTAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayoutItem(), parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        BaseBTAdapter.ViewHolder vh = new BaseBTAdapter.ViewHolder(v);
        return vh;
    }

    protected int getLayoutItem() {
        return R.layout.item_bt_list;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BaseBTAdapter.ViewHolder holder, final int position) {
        final BTPreview btForList = btList.get(position);

        holder.tvNumb.setText("Номер заявки: " + btForList.getId().toString());
        holder.tvCreateDate.setText("Дата створення: " + ConvertTime.dateToString(btForList.getCreatedDate()));
        holder.tvState.setText(btForList.getStatusRequestName());
        switch (btForList.getStatusRequest()) {
            case Const.BusinessTrips.BT_STATUS_APPROVING:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_orange));
                break;
            case Const.BusinessTrips.BT_STATUS_NEW:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_orange));
                break;
            case Const.BusinessTrips.BT_STATUS_WAITRATING:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_green));
                break;
            case Const.BusinessTrips.BT_STATUS_APPROVED:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_blue));
                break;
            case Const.BusinessTrips.BT_STATUS_REJECTED:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_red));
                break;
            case Const.BusinessTrips.BT_STATUS_CANCELED:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_red));
                break;
            case Const.BusinessTrips.BT_STATUS_CLOSED:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_purple));
                break;
        }
        holder.itemView.setOnClickListener(v -> {
            sBTForListListener.onItemClick(btForList);
        });
    }

    public void setItems(List<BTPreview> list){
        btList.clear();
        btList.addAll(list);
        notifyDataSetChanged();
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return btList == null ? 0 : btList.size();
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    protected class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит из
        @BindView(R.id.tv_numb_bt) TextView tvNumb;
        @BindView(R.id.tv_create_date_bt) TextView tvCreateDate;
        @BindView(R.id.tv_state_bt) TextView tvState;

        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


    //интерфейс
    private BaseBTAdapter.BTForListListener sBTForListListener; //переменная

    public void setBTForListListener(BaseBTAdapter.BTForListListener listener) {//кот. содержит в себе объект
        this.sBTForListListener = listener;
    }

    public interface BTForListListener { //этот объект реализует этот интерфейс
        void onItemClick(BTPreview currentBTPreview);
    }
}
