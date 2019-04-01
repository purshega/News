package com.dtek.portal.models.itsm;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItDefaultInfo {

    @SerializedName("root")
    @Expose
    private ItPerson root;

    public ItPerson getRoot() {
        return root;
    }

}
