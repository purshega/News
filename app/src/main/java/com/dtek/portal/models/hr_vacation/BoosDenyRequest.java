package com.dtek.portal.models.hr_vacation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoosDenyRequest {

    @SerializedName("userLogin")
    @Expose
    private String userLogin;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("comments")
    @Expose
    private String comments;

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
