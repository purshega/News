package com.dtek.portal.models.itsm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItPerson {

    @SerializedName("personid")
    @Expose
    private String personid;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("addresscode")
    @Expose
    private String addresscode;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("displayname")
    @Expose
    private String displayName;

    public String getPersonid() {
        return personid;
    }

    public String getRoom() {
        return room;
    }

    public String getAddresscode() {
        return addresscode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getDisplayName() {
        return displayName;
    }

}
