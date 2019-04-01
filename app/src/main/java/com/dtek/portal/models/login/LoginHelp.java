package com.dtek.portal.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginHelp {

    @SerializedName("Sms")
    @Expose
    private String sms;

    @SerializedName("FAQ")
    @Expose
    private String faq;

    public String getSms() {
        return sms;
    }

    public String getFaq() {
        return faq;
    }

}
