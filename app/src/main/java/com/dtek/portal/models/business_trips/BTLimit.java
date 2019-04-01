package com.dtek.portal.models.business_trips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BTLimit {

    @SerializedName("countryOfBussinesTrip")
    @Expose
    private String countryOfBussinesTrip;

    @SerializedName("rigionOfBussinesTrip")
    @Expose
    private String rigionOfBussinesTrip;

    @SerializedName("typeTripActivityByLaw")
    @Expose
    private String typeTripActivityByLaw;

    @SerializedName("specificTypeTrip")
    @Expose
    private String specificTypeTrip;

    @SerializedName("numberOfDays")
    @Expose
    private Long numberOfDays;

    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;

    @SerializedName("sumOfCompensation")
    @Expose
    private Long sumOfCompensation;

    @SerializedName("countryName")
    @Expose
    private String countryName;

    @SerializedName("regionName")
    @Expose
    private String regionName;

    @SerializedName("tripType")
    @Expose
    private String tripType;

    @SerializedName("tripTypeOrg")
    @Expose
    private String tripTypeOrg;

    public String getCountryOfBussinesTrip() {
        return countryOfBussinesTrip;
    }

    public void setCountryOfBussinesTrip(String countryOfBussinesTrip) {
        this.countryOfBussinesTrip = countryOfBussinesTrip;
    }

    public String getRigionOfBussinesTrip() {
        return rigionOfBussinesTrip;
    }

    public void setRigionOfBussinesTrip(String rigionOfBussinesTrip) {
        this.rigionOfBussinesTrip = rigionOfBussinesTrip;
    }

    public String getTypeTripActivityByLaw() {
        return typeTripActivityByLaw;
    }

    public void setTypeTripActivityByLaw(String typeTripActivityByLaw) {
        this.typeTripActivityByLaw = typeTripActivityByLaw;
    }

    public String getSpecificTypeTrip() {
        return specificTypeTrip;
    }

    public void setSpecificTypeTrip(String specificTypeTrip) {
        this.specificTypeTrip = specificTypeTrip;
    }

    public Long getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Long numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getSumOfCompensation() {
        return sumOfCompensation;
    }

    public void setSumOfCompensation(Long sumOfCompensation) {
        this.sumOfCompensation = sumOfCompensation;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getTripTypeOrg() {
        return tripTypeOrg;
    }

    public void setTripTypeOrg(String tripTypeOrg) {
        this.tripTypeOrg = tripTypeOrg;
    }
}
