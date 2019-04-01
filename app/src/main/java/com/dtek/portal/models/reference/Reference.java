package com.dtek.portal.models.reference;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reference {
    @SerializedName("id_class")
    @Expose
    private Long id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("param")
    @Expose
    private List<Param> paramList;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Param> getParamList() {
        return paramList;
    }

    @Override
    public String toString() {
        return name;
    }
}
