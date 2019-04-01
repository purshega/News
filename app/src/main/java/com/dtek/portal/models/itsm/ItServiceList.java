package com.dtek.portal.models.itsm;

import com.dtek.portal.models.itsm.ItService;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItServiceList {

    @SerializedName("root")
    @Expose
    private Root root;

    public Root getRoot() {
        return root;
    }

    public class Root {

        @SerializedName("orders_list")
        @Expose
        private List<ItService> ordersList = null;

        public List<ItService> getOrdersList() {
            return ordersList;
        }

    }

}
