package com.dtek.portal.ui.adapter.hr;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.hr_vacation.VacationList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HrVacationLimitsAdapter extends RecyclerView.Adapter<HrVacationLimitsAdapter.ViewHolder> {
    private static final String TAG = "HrVacationLimitsAdapter";

    private List<VacationList.Vacation> mVacationList = new ArrayList<>();

    public HrVacationLimitsAdapter(List<VacationList.Vacation> list) {
        this.mVacationList.addAll(list);
    }

    // Создает новые views (вызывается layout manager-ом)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hr_leave_limit, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        HrVacationLimitsAdapter.ViewHolder vh = new HrVacationLimitsAdapter.ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final VacationList.Vacation vacation = mVacationList.get(position);

        holder.tvType.setText(vacation.getName());
        if (vacation.getDays() != null) {
            if (vacation.getDays() >=0) {
                holder.tvBalance.setText(String.valueOf(vacation.getDays()));
            }else{
                int i = Math.abs(vacation.getDays());
                holder.tvPrepayment.setText(String.valueOf(i));
                holder.tvBalance.setText(String.valueOf(0));
            }
        }
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mVacationList == null ? 0 : mVacationList.size();
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    protected class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит из
        @BindView(R.id.tv_name_type)
        TextView tvType;
        @BindView(R.id.tv_balance_day)
        TextView tvBalance;
        @BindView(R.id.tv_prepayment_days)
        TextView tvPrepayment;

        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

}
