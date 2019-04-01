package com.dtek.portal.ui.adapter.news;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.models.news.NewsComment;

import java.util.ArrayList;
import java.util.List;

public class NewsCommentAdapter extends RecyclerView.Adapter<NewsCommentAdapter.ViewHolder> {
    private static final String TAG = "NewsCommentAdapter";

    private List<NewsComment> mCommentsList = new ArrayList<>();

    // Конструктор
    public NewsCommentAdapter(List<NewsComment> list) {
        this.mCommentsList.addAll(list);
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public NewsCommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_comment, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NewsComment comment = mCommentsList.get(position);
        Log.d(TAG, "bind, position = " + position);

        holder.tvBody.setText(String.valueOf(comment.getBody()));
        holder.tvAuthor.setText(String.valueOf(comment.getAuthor()));
        holder.tvDate.setText(String.valueOf(comment.getPublishedDate()));
    }


    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mCommentsList == null ? 0 : mCommentsList.size();
    }


    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит из
        private TextView tvBody;
        private TextView tvAuthor;
        private TextView tvDate;


        private ViewHolder(View v) {
            super(v);
            tvBody = (TextView) v.findViewById(R.id.tv_body_comment);
            tvAuthor = (TextView) v.findViewById(R.id.tv_author_comment);
            tvDate = (TextView) v.findViewById(R.id.tv_date_comment);
        }
    }
}
