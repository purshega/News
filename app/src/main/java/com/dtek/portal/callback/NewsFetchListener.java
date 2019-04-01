package com.dtek.portal.callback;

import com.dtek.portal.models.news.News;

import java.util.List;

public interface NewsFetchListener {

    void onDeliverAllPosts(List<News> news);

    void onDeliverPost(News news);

    void onHideDialog();
}
