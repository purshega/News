package com.dtek.portal.models.business_trips;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BTHotel {

    @SerializedName("location")
    @Expose
    private BTLocation location;

    @SerializedName("arrivalDate")
    @Expose
    private Date arrivalDate;

    @SerializedName("departureDate")
    @Expose
    private Date departureDate;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("earlyCheckIn")
    @Expose
    private Boolean earlyCheckIn;

    @SerializedName("lateCheckOut")
    @Expose
    private Boolean lateCheckOut;

    @SerializedName("arrivalTime")
    @Expose
    private Date arrivalTime;

    @SerializedName("departureTime")
    @Expose
    private Date departureTime;

    public BTLocation getLocation() {
        return location;
    }

    public void setLocation(BTLocation location) {
        this.location = location;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getEarlyCheckIn() {
        return earlyCheckIn;
    }

    public void setEarlyCheckIn(Boolean earlyCheckIn) {
        this.earlyCheckIn = earlyCheckIn;
    }

    public Boolean getLateCheckOut() {
        return lateCheckOut;
    }

    public void setLateCheckOut(Boolean lateCheckOut) {
        this.lateCheckOut = lateCheckOut;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }
}
