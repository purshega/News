package com.dtek.portal.ui.adapter.reference;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.reference.ReferenceList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseReferenceAdapter extends RecyclerView.Adapter<BaseReferenceAdapter.ViewHolder> {

    private List<ReferenceList> referencesList = new ArrayList<>();

    public BaseReferenceAdapter() {
    }

    // Создает новые views (вызывается layout manager-ом)
    @NonNull
    @Override
    public BaseReferenceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bt_list, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        BaseReferenceAdapter.ViewHolder vh = new BaseReferenceAdapter.ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BaseReferenceAdapter.ViewHolder holder, final int position) {
        final ReferenceList reference = referencesList.get(position);

//        holder.tvNumb.setText("Номер заявки: " + reference.getId().toString());
//        holder.tvCreateDate.setText("Дата створення: " + ConvertTime.dateToString(btForList.getCreatedDate()));
//        holder.tvState.setText(reference.getStatusRequestName());
//        switch (btForList.getStatusRequest()) {
//            case Const.BusinessTrips.BT_STATUS_APPROVING:
//                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_orange));
//                break;
//            case Const.BusinessTrips.BT_STATUS_NEW:
//                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_orange));
//                break;
//            case Const.BusinessTrips.BT_STATUS_WAITRATING:
//                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_green));
//                break;
//            case Const.BusinessTrips.BT_STATUS_APPROVED:
//                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_blue));
//                break;
//            case Const.BusinessTrips.BT_STATUS_REJECTED:
//                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_red));
//                break;
//            case Const.BusinessTrips.BT_STATUS_CANCELED:
//                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_red));
//                break;
//            case Const.BusinessTrips.BT_STATUS_CLOSED:
//                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_purple));
//                break;
//        }
        holder.itemView.setOnClickListener(v -> {
            sReferenceListener.onItemClick(reference);
        });
    }

    public void setItems(List<ReferenceList> list){
        referencesList.clear();
        referencesList.addAll(list);
        notifyDataSetChanged();
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return referencesList == null ? 0 : referencesList.size();
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    protected class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит из
        @BindView(R.id.tv_numb_bt)
        TextView tvNumb;
        @BindView(R.id.tv_create_date_bt) TextView tvCreateDate;
        @BindView(R.id.tv_state_bt) TextView tvState;

        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    //интерфейс
    private BaseReferenceAdapter.ReferenceListener sReferenceListener; //переменная

    public void setBTForListListener(BaseReferenceAdapter.ReferenceListener listener) {//кот. содержит в себе объект
        this.sReferenceListener = listener;
    }

    public interface ReferenceListener { //этот объект реализует этот интерфейс
        void onItemClick(ReferenceList currentReference);
    }
}
