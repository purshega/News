package com.dtek.portal.ui.adapter.car;

import com.dtek.portal.R;
import com.dtek.portal.models.car.CarOrder;

import java.util.List;

public class CarDraftAdapter extends BaseCarAdapter {
    private static final String TAG = "CarDraftAdapter";
    private static final int LAYOUT_ITEM = R.layout.item_order_car_draft;

    public CarDraftAdapter(List<CarOrder> list) {
        super(list);
    }

    @Override
    public int getLayoutItem() {
        return LAYOUT_ITEM;
    }
}
