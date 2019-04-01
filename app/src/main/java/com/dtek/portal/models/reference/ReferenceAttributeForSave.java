package com.dtek.portal.models.reference;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferenceAttributeForSave {
    @SerializedName("root")
    @Expose
    private Root root;

    public void setRoot(Root root) {
        this.root = root;
    }

    public Root getRoot() {
        return root;
    }

    public static class Root{
        @SerializedName("order_id")
        @Expose
        private String order_id;

        @SerializedName("param")
        @Expose
        private List<Param> params;

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public void setParams(List<Param> params) {
            this.params = params;
        }
    }
}
