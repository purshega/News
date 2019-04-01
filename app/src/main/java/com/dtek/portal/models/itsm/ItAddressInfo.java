package com.dtek.portal.models.itsm;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItAddressInfo {

    @SerializedName("addresscode")
    @Expose
    private String addresscode;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("city")
    @Expose
    private String city;

    public String getAddresscode() {
        return addresscode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public ItAddressInfo(String addresscode, String street, String city) {
        this.addresscode = addresscode;
        this.street = street;
        this.city = city;
    }

    public String toString(){
        return String.format("addresscode:%s,street:%s,city:%s", addresscode, street, city);
    }
}
