package com.dtek.portal.models.itsm.twoline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItRootStatus {

    @SerializedName("root")
    @Expose
    private ItStatus root;

    public ItStatus getRoot() {
        return root;
    }

    public void setRoot(ItStatus root) {
        this.root = root;
    }

    public static class ItStatus {

        @SerializedName("status")
        @Expose
        private String status; // статус

        @SerializedName("reason")
        @Expose
        private String reason; // причина

        @SerializedName("comment")
        @Expose
        private String comment; // примечание

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}
