package com.dtek.portal.models.itsm.twoline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItRootList {

    @SerializedName("root")
    @Expose
    private Root root;

    public Root getRoot() {
        return root;
    }

    public class Root {

        @SerializedName("orders_list")
        @Expose
        private List<ItTaskList> ordersList = null;

        public List<ItTaskList> getOrdersList() {
            return ordersList;
        }

    }
}
