package com.dtek.portal.models.hr_vacation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalDaysResponse {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("countDays")
    @Expose
    private Integer countDays;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCountDays() {
        return countDays;
    }
}
