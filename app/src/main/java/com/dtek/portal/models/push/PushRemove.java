package com.dtek.portal.models.push;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PushRemove {

    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("Text")
    @Expose
    private String text;

    public PushRemove() {
    }

}
