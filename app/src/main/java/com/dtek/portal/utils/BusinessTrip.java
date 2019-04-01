package com.dtek.portal.utils;

import com.dtek.portal.models.business_trips.BT;

public class BusinessTrip {

    private static BusinessTrip bInstance = null;
    private BT businessTrip;
    private Boolean changeStatus;

    public static synchronized BusinessTrip getInstance() {
        if (null == bInstance) {
            bInstance = new BusinessTrip();
        }
        return bInstance;
    }

    public void createNewBusinessTrip() {
        businessTrip = new BT();
        changeStatus = false;
    }

    public void setBusinessTrip(BT businessTrip) {
        this.businessTrip = businessTrip;
        changeStatus = false;
    }


    public BT getBusinessTrip() {
        return businessTrip;
    }

    public Boolean getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(Boolean changeStatus) {
        this.changeStatus = changeStatus;
    }
}
