package com.dtek.portal.models.hr_vacation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubordinateList {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Subordinate> subordinateList = null;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Subordinate> getSubordinateList() {
        return subordinateList;
    }

    public class Subordinate {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("nameVacation")
        @Expose
        private String nameVacation;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("dates")
        @Expose
        private String period;
        @SerializedName("comment")
        @Expose
        private String comment;

        public Integer getId() {
            return id;
        }

        public String getNameVacation() {
            return nameVacation;
        }

        public String getName() {
            return name;
        }

        public String getPeriod() {
            return period;
        }

        public String getComment() {
            return comment;
        }

    }


}
