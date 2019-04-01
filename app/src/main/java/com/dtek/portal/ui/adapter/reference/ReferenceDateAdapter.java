package com.dtek.portal.ui.adapter.reference;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.models.reference.Param;
import com.dtek.portal.utils.ConvertTime;
import com.dtek.portal.utils.PickerUtils;
import com.dtek.portal.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReferenceDateAdapter extends RecyclerView.Adapter<ReferenceDateAdapter.ViewHolder> {

    private List<Param> paramList = new ArrayList<>();

    @NonNull
    @Override
    public ReferenceDateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reference_date, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        ReferenceDateAdapter.ViewHolder vh = new ReferenceDateAdapter.ViewHolder(v);
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReferenceDateAdapter.ViewHolder holder, final int position) {
        final Param param = paramList.get(position);

        holder.tvDateName.setText(param.getParamName());
        holder.etDate.setText(param.getParamValue());

        holder.etDate.setOnClickListener(v -> dateListener.onItemClick(param, position));
    }

    public void setItems(List<Param> list) {
        paramList.addAll(list);
        notifyDataSetChanged();
    }

    public void setChangeItems(Param param, int index) {
        paramList.set(index, param);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return paramList == null ? 0 : paramList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date_name)
        TextView tvDateName;
        @BindView(R.id.et_date)
        EditText etDate;

        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    //интерфейс
    private ReferenceDateAdapter.DateListener dateListener; //переменная

    public void setDateListener(ReferenceDateAdapter.DateListener listener) {//кот. содержит в себе объект
        this.dateListener = listener;
    }

    public interface DateListener { //этот объект реализует этот интерфейс
        void onItemClick(Param currentParam, int index);
    }

}
