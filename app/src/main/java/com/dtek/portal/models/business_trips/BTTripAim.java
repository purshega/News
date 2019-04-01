package com.dtek.portal.models.business_trips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BTTripAim {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("tripTypeId")
    @Expose
    private String tripTypeId;

    @SerializedName("nameLong")
    @Expose
    private String nameLong;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTripTypeId() {
        return tripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId = tripTypeId;
    }

    public String getNameLong() {
        return nameLong;
    }

    public void setNameLong(String nameLong) {
        this.nameLong = nameLong;
    }

    @Override
    public String toString() {
        return name;
    }
}
