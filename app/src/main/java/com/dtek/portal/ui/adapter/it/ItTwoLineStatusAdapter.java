package com.dtek.portal.ui.adapter.it;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.dtek.portal.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItTwoLineStatusAdapter extends RecyclerView.Adapter<ItTwoLineStatusAdapter.ViewHolder> {

    private final String[] list;
    private static int sSelected = -1;

    public ItTwoLineStatusAdapter(String[] list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_it_2line_status, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.radioButton.setText(list[position]);
        holder.radioButton.setChecked(position == sSelected);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public void selectedItem() {
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.choice_select)
        RadioButton radioButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.choice_select)
        void onItemClick(View view) {
            sSelected = getAdapterPosition();
            sClickListener.onItemClickListener(getAdapterPosition(), view);
        }
    }


    private static SingleClickListener sClickListener;

    public void setOnItemClickListener(SingleClickListener clickListener) {
        sClickListener = clickListener;
    }

    public interface SingleClickListener {
        void onItemClickListener(int position, View view);
    }
}
