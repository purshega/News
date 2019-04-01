package com.dtek.portal.ui.adapter.it;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.itsm.twoline.ItTaskList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ItTwoLineTaskAdapter extends RecyclerView.Adapter<ItTwoLineTaskAdapter.ViewHolder>
        implements Filterable {
    private static final String TAG = "ItTwoLineTaskAdapter";

    private List<ItTaskList> mTaskList;
    private List<ItTaskList> mTaskListFiltered;

    public ItTwoLineTaskAdapter(List<ItTaskList> list, TaskClickListener listener) {
        this.sTaskClickListener = listener;
        this.mTaskList = list;
        this.mTaskListFiltered = list;
//        this.mTaskList.addAll(list);
//        this.mTaskListFiltered.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_two_line_task, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ItTaskList task = mTaskListFiltered.get(position);
        Log.d(TAG, "bind, position = " + position);

        holder.tvNumber.setText(String.format("%s %s", task.getTypeNumber(), task.getNumber()));
        holder.tvAddress.setText(task.getFullAddress());
        holder.tvPriority.setText(task.getPriority());
        holder.tvDescription.setText(task.getDescription());

        if (task.isVip()) {
            holder.cardView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.color_status_pink));
        }
    }

    @Override
    public int getItemCount() {
        return mTaskListFiltered == null ? 0 : mTaskListFiltered.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит из
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_priority)
        TextView tvPriority;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.cardView)
        CardView cardView;


        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @OnClick(R.id.cardView)
        void onItemClick() {
            sTaskClickListener.onItemClick(mTaskListFiltered.get(getAdapterPosition()));
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mTaskListFiltered = mTaskList;
                } else {
                    List<ItTaskList> filteredList = new ArrayList<>();
                    for (ItTaskList row : mTaskList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getFullAddress().toLowerCase().contains(charString.toLowerCase())
                                /*|| row.getCity().toLowerCase().contains(charString.toLowerCase())*/) {
                            filteredList.add(row);
                        }
                    }

                    mTaskListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mTaskListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mTaskListFiltered = (ArrayList<ItTaskList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    //интерфейс
    private TaskClickListener sTaskClickListener; //переменная

    public interface TaskClickListener { //этот объект реализует этот интерфейс
        void onItemClick(ItTaskList task);
    }

}
