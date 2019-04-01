package com.dtek.portal.models.itsm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItResponse {

    @SerializedName("root")
    @Expose
    private Root root;

    public Root getRoot() {
        return root;
    }

    public class Root {

        @SerializedName("result")
        @Expose
        private boolean result;
        @SerializedName("message")
        @Expose
        private String message;

        public boolean isResult() {
            return result;
        }

        public String getMessage() {
            return message;
        }

    }
}
