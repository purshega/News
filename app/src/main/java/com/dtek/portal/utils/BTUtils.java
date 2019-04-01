package com.dtek.portal.utils;

import com.dtek.portal.Const;
import com.dtek.portal.models.business_trips.BTPreview;

import java.util.ArrayList;
import java.util.List;

public class BTUtils {

    public static List<BTPreview> getActiveList(List<BTPreview> list) {
        List<BTPreview> sortList = new ArrayList<>();
        for (BTPreview bt : list) {
            if (
                    bt.getStatusRequest().equals(Const.BusinessTrips.BT_STATUS_APPROVED)
                            || bt.getStatusRequest().equals(Const.BusinessTrips.BT_STATUS_APPROVING)
                            || bt.getStatusRequest().equals(Const.BusinessTrips.BT_STATUS_WAITRATING)
            )
                sortList.add(bt);
        }
        return sortList;
    }


    public static List<BTPreview> getArchiveList(List<BTPreview> list) {
        List<BTPreview> sortList = new ArrayList<>();
        for (BTPreview bt : list) {
            if (
                    bt.getStatusRequest().equals(Const.BusinessTrips.BT_STATUS_CANCELED)
                            || bt.getStatusRequest().equals(Const.BusinessTrips.BT_STATUS_CLOSED)
                            || bt.getStatusRequest().equals(Const.BusinessTrips.BT_STATUS_REJECTED)
            )
                sortList.add(bt);
        }
        return sortList;
    }
}
