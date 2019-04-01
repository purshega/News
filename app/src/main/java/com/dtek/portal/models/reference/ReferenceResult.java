package com.dtek.portal.models.reference;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ReferenceResult {
    @SerializedName("root")
    @Expose
    private Root root;

    public Root getRoot() {
        return root;
    }

    public class Root{
        @SerializedName("result")
        @Expose
        private String result;

        @SerializedName("order_id")
        @Expose
        private String order_id;

        public String getOrder_id() {
            return order_id;
        }

        public String getResult() {
            return result;
        }
    }
}
