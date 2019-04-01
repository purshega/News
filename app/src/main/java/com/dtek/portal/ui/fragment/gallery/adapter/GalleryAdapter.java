package com.dtek.portal.ui.fragment.gallery.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.gallery.Folder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private static final String TAG = "GalleryAdapter";

    private List<Folder> mFolderList = new ArrayList<>();

    public GalleryAdapter(List<Folder> list) {
        this.mFolderList.addAll(list);
    }

    // Создает новые views (вызывается layout manager-ом)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        GalleryAdapter.ViewHolder vh = new GalleryAdapter.ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Folder folder = mFolderList.get(position);

        holder.tvTitle.setText(folder.getName());


        holder.itemView.setOnClickListener(v -> {
//                if (sOrderCarListener != null) {
            sFolderListener.onItemClick(folder);
//                }
        });

    }


    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mFolderList == null ? 0 : mFolderList.size();
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    protected class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит из
        @BindView(R.id.tv_title) TextView tvTitle;

        private ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


    //интерфейс
    private FolderListener sFolderListener; //переменная

    public void setFolderListener(FolderListener listener) {//кот. содержит в себе объект
        this.sFolderListener = listener;
    }

    public interface FolderListener { //этот объект реализует этот интерфейс
        void onItemClick(Folder currentFolder);
    }
}
