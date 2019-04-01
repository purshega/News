package com.dtek.portal.models.push;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Push {

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("strAppID")
    @Expose
    private String strAppID;

    public Push() {
    }

    public Push(String login, String strAppID) {
        this.login = login;
        this.strAppID = strAppID;
    }
}
