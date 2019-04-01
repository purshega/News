package com.dtek.portal.ui.adapter.reference;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.reference.Param;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReferenceAttributeAdapter extends RecyclerView.Adapter<ReferenceAttributeAdapter.ViewHolder> {

    private List<Param> paramList = new ArrayList<>();
    private ReferenceAttributeAdapter.ViewHolder viewHolder;

    @NonNull
    @Override
    public ReferenceAttributeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reference_attribute_item, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        viewHolder = new ReferenceAttributeAdapter.ViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReferenceAttributeAdapter.ViewHolder holder, final int position) {
        final Param param = paramList.get(position);

        holder.tvAttributeName.setText(param.getParamName());
        holder.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                paramList.get(holder.getAdapterPosition()).setParamValue(s.toString());
            }
        });

    }

    public void setItems(List<Param> list){
        paramList.addAll(list);
        notifyDataSetChanged();
    }

    public List<Param> getParamList() {
        return paramList;
    }

    @Override
    public int getItemCount() {
        return paramList == null ? 0 : paramList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_attribute_name)
        TextView tvAttributeName;
        @BindView(R.id.et_attribute_content)
        EditText etContent;

        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public EditText getEtContent() {
            return etContent;
        }
    }
}
