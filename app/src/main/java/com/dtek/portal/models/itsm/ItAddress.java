package com.dtek.portal.models.itsm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItAddress {

    @SerializedName("root")
    @Expose
    private Root root;

    public Root getRoot() {
        return root;
    }

    public class Root {

        @SerializedName("address_info")
        @Expose
        private List<ItAddressInfo> mItAddressInfo = null;

        public List<ItAddressInfo> getItAddressInfo() {
            return mItAddressInfo;
        }

    }
}
