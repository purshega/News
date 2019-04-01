package com.dtek.portal.models.itsm.twoline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

//модель смены статусов заявки работу
public class ItChangeRequest {

    @SerializedName("root")
    @Expose
    private Root root;

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

    public static class Root {

        @SerializedName("id_order")
        @Expose
        private String idOrder; // id заявки
        @SerializedName("siteid")
        @Expose
        private String siteId; // siteid
        @SerializedName("personid")
        @Expose
        private String personId; // personid

        @SerializedName("subject")
        @Expose
        private String subject; // тест1

        @SerializedName("details")
        @Expose
        private String details; //

        @SerializedName("date")
        @Expose
        private String date; //

        @SerializedName("reason")
        @Expose
        private String reason; //

        @SerializedName("returnreason")
        @Expose
        private String returnReason; //

        @SerializedName("fact_time")
        @Expose
        private String factTime;

        public String getIdOrder() {
            return idOrder;
        }

        public void setIdOrder(String idOrder) {
            this.idOrder = idOrder;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }


        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getFactTime() {
            return factTime;
        }

        public void setFactTime(String factTime) {
            this.factTime = factTime;
        }

        public String getReturnReason() {
            return returnReason;
        }

        public void setReturnReason(String returnReason) {
            this.returnReason = returnReason;
        }
    }
}
