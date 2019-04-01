package com.dtek.portal.ui.adapter.hr;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.hr_vacation.SubordinateList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HrVacationSubordinateAdapter extends RecyclerView.Adapter<HrVacationSubordinateAdapter.ViewHolder> {
    private static final String TAG = "HrVacationSubordinateAdapter";

    private List<SubordinateList.Subordinate> mSubordinateList = new ArrayList<>();

    public HrVacationSubordinateAdapter(List<SubordinateList.Subordinate> list) {
        this.mSubordinateList.addAll(list);
    }

    // Создает новые views (вызывается layout manager-ом)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hr_leave_suorditate, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        HrVacationSubordinateAdapter.ViewHolder vh = new HrVacationSubordinateAdapter.ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final SubordinateList.Subordinate subordinate = mSubordinateList.get(position);
        Log.d(TAG, "bind, position = " + position);

        holder.tvSubordinate.setText(subordinate.getName());
        holder.tvType.setText(subordinate.getNameVacation());
        holder.tvPeriod.setText(subordinate.getPeriod());

        holder.itemView.setOnClickListener(v -> {
            if (sHrLeaveSubordinateListener != null) {
                sHrLeaveSubordinateListener.onItemClick(subordinate);
            }
        });
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mSubordinateList == null ? 0 : mSubordinateList.size();
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    protected class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит из
        @BindView(R.id.tv_subordinate) TextView tvSubordinate;
        @BindView(R.id.tv_type) TextView tvType;
        @BindView(R.id.tv_period) TextView tvPeriod;

        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


    //интерфейс
    private HrLeaveSubordinateListener sHrLeaveSubordinateListener; //переменная

    public void setHrLeaveSubordinateListener(HrLeaveSubordinateListener listener) {//кот. содержит в себе объект
        this.sHrLeaveSubordinateListener = listener;
    }

    public interface HrLeaveSubordinateListener { //этот объект реализует этот интерфейс
        void onItemClick(SubordinateList.Subordinate subordinate);
    }

}
