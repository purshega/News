package com.dtek.portal.ui.adapter.news;

import com.dtek.portal.R;
import com.dtek.portal.callback.PaginationAdapterCallback;

public class NewsDtekAdapter extends BaseNewsAdapter{
    private static final String TAG = "NewsDtekAdapter";
    private static final int LAYOUT_ITEM = R.layout.item_news_dtek_list;

    public NewsDtekAdapter(PaginationAdapterCallback callback) {
        super(callback);
    }

    @Override
    public int getLayoutItem() {
        return LAYOUT_ITEM;
    }

}
