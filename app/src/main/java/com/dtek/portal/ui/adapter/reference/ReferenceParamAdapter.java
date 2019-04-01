package com.dtek.portal.ui.adapter.reference;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.reference.Param;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReferenceParamAdapter extends RecyclerView.Adapter<ReferenceParamAdapter.ViewHolder> {

    private List<Param> paramList = new ArrayList<>();
    private ReferenceParamAdapter.ViewHolder viewHolder;

    @NonNull
    @Override
    public ReferenceParamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reference_param, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        viewHolder = new ReferenceParamAdapter.ViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReferenceParamAdapter.ViewHolder holder, final int position) {
        final Param param = paramList.get(position);

        holder.tvParamName.setText(param.getParamName());
        holder.tvParamValue.setText(param.getParamValue());
    }

    public void setItems(List<Param> list){
        paramList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return paramList == null ? 0 : paramList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_param_name)
        TextView tvParamName;
        @BindView(R.id.tv_param_value)
        TextView tvParamValue;

        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
