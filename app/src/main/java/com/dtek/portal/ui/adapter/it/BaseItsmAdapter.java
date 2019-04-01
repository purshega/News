package com.dtek.portal.ui.adapter.it;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.itsm.ItService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BaseItsmAdapter extends RecyclerView.Adapter<BaseItsmAdapter.ViewHolder> {
    private static final String TAG = "BaseItsmAdapter";

    public static final String STATE_NEW = "Новое";
    public static final String STATE_APPROVED = "Согласование";
    public static final String STATE_REFINEMENT = "Уточнение";
    public static final String STATE_QUEUE = "В очереди";
    public static final String STATE_WAITING = "Ожидает";
    public static final String STATE_IN_WORK = "В работе";
    public static final String STATE_SNOOZED = "Отложено";
    public static final String STATE_RESOLVED = "Решено";
    public static final String STATE_DISAPPROVED = "Отклонено";
    public static final String STATE_WITHDRAWN = "Отозвано";
    public static final String STATE_CLOSED = "Закрыто";

    private List<ItService> mItServiceList = new ArrayList<>();

    public BaseItsmAdapter(List<ItService> list) {
        this.mItServiceList.addAll(list);
    }

    // Создает новые views (вызывается layout manager-ом)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayoutItem(), parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        BaseItsmAdapter.ViewHolder vh = new BaseItsmAdapter.ViewHolder(v);
        return vh;
    }

    protected int getLayoutItem() {
        return R.layout.item_it_service;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ItService itOrder = mItServiceList.get(position);
        Log.d(TAG, "bind, position = " + position);

        holder.tvNumber.setText(String.format("%s%s", holder.tvNumber.getText(), itOrder.getIdOrder()));
        holder.tvSubject.setText(itOrder.getSubject());
        holder.tvState.setText(itOrder.getStatus());
        switch (itOrder.getStatus()) {
            case STATE_NEW:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_black));
                break;
            case STATE_APPROVED:
            case STATE_QUEUE:
            case STATE_WAITING:
            case STATE_IN_WORK:
            case STATE_SNOOZED:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_orange));
                break;
            case STATE_REFINEMENT:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_blue));
                break;
            case STATE_RESOLVED:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_green));
                break;
            case STATE_WITHDRAWN:
            case STATE_DISAPPROVED:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_red));
                break;
            case STATE_CLOSED:
                holder.tvState.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_purple));
                break;

        }

        holder.itemView.setOnClickListener(v -> {
            if (sItServiceListener != null) {
                sItServiceListener.onItemClick(itOrder);
            }
        });

    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mItServiceList == null ? 0 : mItServiceList.size() - 2;
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    protected class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит из
        @BindView(R.id.tv_number) TextView tvNumber;
        @BindView(R.id.tv_subject) TextView tvSubject;
        @BindView(R.id.tv_state) TextView tvState;

        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


    //интерфейс
    private ItServiceListener sItServiceListener; //переменная

    public void setItServiceListener(ItServiceListener listener) {//кот. содержит в себе объект
        this.sItServiceListener = listener;
    }

    public interface ItServiceListener { //этот объект реализует этот интерфейс
        void onItemClick(ItService currentOrder);
    }

}
