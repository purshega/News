package com.dtek.portal.models.hr_vacation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseChief {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("isVip")
    @Expose
    private boolean vip;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isVip() {
        return vip;
    }

}
