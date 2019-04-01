package com.dtek.portal.ui.adapter.car;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.car.CarOrder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BaseCarAdapter extends RecyclerView.Adapter<BaseCarAdapter.ViewHolder> {
    private static final String TAG = "BaseCarAdapter";

    public static final String STATE_DRAFT = "Черновик";
    public static final String STATE_WAITING = "Ожидание";
    public static final String STATE_IN_WORK = "В работе";
    public static final String STATE_ASSIGNED = "Назначена";
    public static final String STATE_RATING = "Ожидает оценку";
    public static final String STATE_CANCELED = "Отменена";
    public static final String STATE_CLOSED = "Закрыта";

    private List<CarOrder> mCarOrderList = new ArrayList<>();

    public BaseCarAdapter(List<CarOrder> list) {
        this.mCarOrderList.addAll(list);
    }

    // Создает новые views (вызывается layout manager-ом)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayoutItem(), parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        BaseCarAdapter.ViewHolder vh = new BaseCarAdapter.ViewHolder(v);
        return vh;
    }

    protected int getLayoutItem() {
        return R.layout.item_order_car_all;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CarOrder carOrder = mCarOrderList.get(position);

        holder.tvTitle.setText(carOrder.getTitle());
        holder.tvRoute.setText(carOrder.getRoute());
        holder.tvState.setText(carOrder.getState());
        switch (carOrder.getState()) {
            case STATE_WAITING:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_orange));
                break;
            case STATE_IN_WORK:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_orange));
                break;
            case STATE_ASSIGNED:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_green));
                break;
            case STATE_RATING:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_blue));
                break;
            case STATE_CANCELED:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_red));
                break;
            case STATE_CLOSED:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_purple));
                break;
            case STATE_DRAFT:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_black));
                break;
        }

        holder.itemView.setOnClickListener(v -> {
//                if (sOrderCarListener != null) {
            sOrderCarListener.onItemClick(carOrder);
//                }
        });

    }


    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mCarOrderList == null ? 0 : mCarOrderList.size();
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    protected class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит из
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_route) TextView tvRoute;
        @BindView(R.id.tv_state) TextView tvState;

        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


    //интерфейс
    private OrderCarListener sOrderCarListener; //переменная

    public void setOrderCarListener(OrderCarListener listener) {//кот. содержит в себе объект
        this.sOrderCarListener = listener;
    }

    public interface OrderCarListener { //этот объект реализует этот интерфейс
        void onItemClick(CarOrder currentOrder);
    }
}
