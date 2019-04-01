package com.dtek.portal.models.reference;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferenceDetail {

    @SerializedName("root")
    @Expose
    private Root root;

    public Root getRoot() {
        return root;
    }

    public class Root{
        @SerializedName("id_order")
        @Expose
        private String idOrder;

        @SerializedName("status")
        @Expose
        private String status;

        @SerializedName("author")
        @Expose
        private String author;

        @SerializedName("create_date")
        @Expose
        private String createDate;

        @SerializedName("end_date_p")
        @Expose
        private String endDatePlan;

        @SerializedName("end_date")
        @Expose
        private String endDate;

        @SerializedName("user")
        @Expose
        private String user;

        @SerializedName("siteid")
        @Expose
        private String siteId;

        @SerializedName("phone_number")
        @Expose
        private String phoneNumber;

        @SerializedName("address")
        @Expose
        private String address;

        @SerializedName("urgency")
        @Expose
        private String urgency;

        @SerializedName("subject")
        @Expose
        private String subject;

        @SerializedName("details")
        @Expose
        private String details;

        @SerializedName("rate")
        @Expose
        private String rate;

        @SerializedName("rate_comment")
        @Expose
        private String rate_comment;

        @SerializedName("solution")
        @Expose
        private String solution = "";

        @SerializedName("organization")
        @Expose
        private String organization;

        @SerializedName("tabnum")
        @Expose
        private String tabNum;

        @SerializedName("param")
        @Expose
        private List<Param> params;

        public String getIdOrder() {
            return idOrder;
        }

        public String getStatus() {
            return status;
        }

        public String getAuthor() {
            return author;
        }

        public String getCreateDate() {
            return createDate;
        }

        public String getEndDatePlan() {
            return endDatePlan;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getUser() {
            return user;
        }

        public String getSiteId() {
            return siteId;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public String getUrgency() {
            return urgency;
        }

        public String getSubject() {
            return subject;
        }

        public String getDetails() {
            return details;
        }

        public String getRate() {
            return rate;
        }

        public String getRate_comment() {
            return rate_comment;
        }

        public String getSolution() {
            return solution;
        }

        public String getOrganization() {
            return organization;
        }

        public String getTabNum() {
            return tabNum;
        }

        public List<Param> getParams() {
            return params;
        }
    }



}
