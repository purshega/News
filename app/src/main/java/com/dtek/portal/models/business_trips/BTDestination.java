package com.dtek.portal.models.business_trips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class BTDestination {

    @SerializedName("location")
    @Expose
    private BTLocation location;

    @SerializedName("startDate")
    @Expose
    private Date startDate;

    @SerializedName("endDate")
    @Expose
    private Date endDate;

    @SerializedName("tripAim")
    @Expose
    private BTTripAim tripAim;

    @SerializedName("organization")
    @Expose
    private BTOrganization organization;

    @SerializedName("isCompensationNeeded")
    @Expose
    private Boolean isCompensationNeeded;

    @SerializedName("compensationStartDate")
    @Expose
    private Date compensationStartDate;

    @SerializedName("compensationEndDate")
    @Expose
    private Date compensationEndDate;

    public BTDestination() {
        isCompensationNeeded = false;
    }

    public BTLocation getLocation() {
        return location;
    }

    public void setLocation(BTLocation location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BTTripAim getTripAim() {
        return tripAim;
    }

    public void setTripAim(BTTripAim tripAim) {
        this.tripAim = tripAim;
    }

    public BTOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(BTOrganization organization) {
        this.organization = organization;
    }

    public Boolean getCompensationNeeded() {
        return isCompensationNeeded;
    }

    public void setCompensationNeeded(Boolean compensationNeeded) {
        isCompensationNeeded = compensationNeeded;
    }

    public Date getCompensationStartDate() {
        return compensationStartDate;
    }

    public void setCompensationStartDate(Date compensationStartDate) {
        this.compensationStartDate = compensationStartDate;
    }

    public Date getCompensationEndDate() {
        return compensationEndDate;
    }

    public void setCompensationEndDate(Date compensationEndDate) {
        this.compensationEndDate = compensationEndDate;
    }
}
