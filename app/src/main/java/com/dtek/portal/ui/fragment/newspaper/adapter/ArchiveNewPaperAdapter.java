package com.dtek.portal.ui.fragment.newspaper.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.newspaper.Newspaper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArchiveNewPaperAdapter extends RecyclerView.Adapter<ArchiveNewPaperAdapter.ArchiveViewHolder>{

    private List<Newspaper> newspaper_list = new ArrayList<>();

    public void setItems(Collection<Newspaper> newspapers){
        newspaper_list.addAll(newspapers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArchiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_newspaper, viewGroup, false);
        return new ArchiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArchiveViewHolder archiveViewHolder, int i) {
        archiveViewHolder.bind(newspaper_list.get(i));

        archiveViewHolder.itemView.setOnClickListener(v -> {
            sNewsPaperListener.onItemClick(newspaper_list.get(i));
        });
    }

    @Override
    public int getItemCount() {

        return newspaper_list.size();
    }


    protected class ArchiveViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_name)
        TextView title_name;

        public ArchiveViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Newspaper newspaper) {
            title_name.setText(newspaper.getNewspaper_name());
        }
    }

            //интерфейс
        private NewsPaperListener sNewsPaperListener; //переменная

        public void setNewsPaperListener(NewsPaperListener listener) {//кот. содержит в себе объект
            this.sNewsPaperListener = listener;
        }

        public interface NewsPaperListener { //этот объект реализует этот интерфейс
            void onItemClick(Newspaper newsPaper);
        }
}
