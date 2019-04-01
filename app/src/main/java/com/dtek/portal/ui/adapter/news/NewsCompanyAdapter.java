package com.dtek.portal.ui.adapter.news;

import com.dtek.portal.R;
import com.dtek.portal.callback.PaginationAdapterCallback;
import com.dtek.portal.models.news.News;

public class NewsCompanyAdapter extends BaseNewsAdapter {
    private static final String TAG = "NewsCompanyAdapter";
    private static final int LAYOUT_ITEM = R.layout.item_news_company_list;

    public NewsCompanyAdapter(PaginationAdapterCallback callback) {
        super(callback);
    }

    @Override
    public int getLayoutItem() {
        return LAYOUT_ITEM;
    }

    @Override
    public void loadImageFromInet(News currentNews, ItemVH itemVH) {
        return;
    }
}
