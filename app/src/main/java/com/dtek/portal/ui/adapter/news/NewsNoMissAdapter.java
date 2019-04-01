package com.dtek.portal.ui.adapter.news;

import com.dtek.portal.R;
import com.dtek.portal.callback.PaginationAdapterCallback;
import com.dtek.portal.ui.adapter.news.BaseNewsAdapter;

public class NewsNoMissAdapter extends BaseNewsAdapter {
    private static final String TAG = "NewsNoMissAdapter";
    private static final int LAYOUT_ITEM = R.layout.item_news_no_miss_list;

    public NewsNoMissAdapter(PaginationAdapterCallback callback) {
        super(callback);
    }

    @Override
    public int getLayoutItem() {
        return LAYOUT_ITEM;
    }
}
