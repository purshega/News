package com.dtek.portal.ui.adapter.hr;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.hr_vacation.HistoryList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HrVacationHistoryAdapter extends RecyclerView.Adapter<HrVacationHistoryAdapter.ViewHolder> {
    private static final String TAG = "HrVacationHistoryAdapter";

    public static final String STATE_APPROVED = "На согласовании";
    public static final String STATE_APPROVED_HR = "На рассмотрении HR";
    public static final String STATE_WORK_HR = "Принято в работу HR";
    public static final String STATE_SIGNED = "Приказ подписан";
    public static final String STATE_DISAPPROVED = "Отклонено";
    public static final String STATE_DISAPPROVED_HR = "Отклонено HR";
    public static final String STATE_SIGNED_NOT = "Приказ не подписан";
    public static final String STATE_WITHDRAWN = "Отозвано";

    private List<HistoryList.History> mHistoryList = new ArrayList<>();

    public HrVacationHistoryAdapter(/*Context context/*, List<HistoryList.History> list*/) {
//        this.mContext = context;
//        this.mHistoryList.addAll(list);
    }

    // Создает новые views (вызывается layout manager-ом)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(mContext).inflate(R.layout.item_hr_leave_history, parent, false);
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hr_leave_history, parent, false);

        return new ViewHolder(v);
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryList.History history = mHistoryList.get(position);
        Log.d(TAG, "bind, position = " + position);

        holder.tvType.setText(history.getNameVacation());
        holder.tvPeriod.setText(history.getPeriod());

        String chief = history.getUserName() != null ? history.getUserName() : "";
        holder.tvStatus.setText(String.format("%s %s", history.getStatus(), chief));

        switch (history.getStatus()) {
            case STATE_APPROVED:
                holder.tvStatus.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_orange));
                break;
            case STATE_APPROVED_HR:
                holder.tvStatus.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_blue));
                break;
            case STATE_WORK_HR:
                holder.tvStatus.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_purple));
                break;
            case STATE_SIGNED:
                holder.tvStatus.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_green));
                break;
            case STATE_DISAPPROVED:
            case STATE_DISAPPROVED_HR:
            case STATE_SIGNED_NOT:
            case STATE_WITHDRAWN:
                holder.tvStatus.setTextColor(holder.itemView.getResources().getColor(R.color.color_status_red));
                break;
        }
        if (history.isCanRejected()){
            holder.ivStatus.setImageResource(R.drawable.ic_block_24dp);
        }
        if (history.isCanEdit()){
            holder.ivStatus.setImageResource(R.drawable.ic_edit_24dp);
        }

        holder.itemView.setOnClickListener(v -> {
            if (sHistoryClickListener != null) {
                sHistoryClickListener.onItemClick(history);
            }
        });

    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mHistoryList == null ? 0 : mHistoryList.size();
    }

    public void setItems(List<HistoryList.History> historyList) {
        mHistoryList = historyList;
        notifyDataSetChanged();
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    protected class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит из
        @BindView(R.id.tv_type_leave)
        TextView tvType;
        @BindView(R.id.tv_period)
        TextView tvPeriod;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.iv_status)
        ImageView ivStatus;

        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


    //интерфейс
    private HistoryClickListener sHistoryClickListener; //переменная

    public void setHistoryClickListener(HistoryClickListener listener) {//кот. содержит в себе объект
        this.sHistoryClickListener = listener;
    }

    public interface HistoryClickListener { //этот объект реализует этот интерфейс
        void onItemClick(HistoryList.History currentOrder);
    }

}
