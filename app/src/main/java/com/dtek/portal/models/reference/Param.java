package com.dtek.portal.models.reference;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Param {
    @SerializedName("param_id")
    @Expose
    private String paramId;

    @SerializedName("param_name")
    @Expose
    private String paramName;

    @SerializedName("param_value")
    @Expose
    private String paramValue = "";

    public Param(String paramId, String paramValue) {
        this.paramId = paramId;
        this.paramValue = paramValue;
    }

    public String getParamId() {
        return paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "Param{" +
                "paramId='" + paramId + '\'' +
                ", paramName='" + paramName + '\'' +
                ", paramValue='" + paramValue + '\'' +
                '}';
    }
}
