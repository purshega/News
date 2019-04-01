package com.dtek.portal.models.business_trips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BTPreview {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("createdDate")
    @Expose
    private Date createdDate;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("personnelNumber")
    @Expose
    private Long personnelNumber;

    @SerializedName("startDate")
    @Expose
    private Date startDate;

    @SerializedName("startTime")
    @Expose
    private Date startTime;

    @SerializedName("endDate")
    @Expose
    private Date endDate;

    @SerializedName("endTime")
    @Expose
    private Date endTime;

    @SerializedName("employeeName")
    @Expose
    private String employeeName;

    @SerializedName("processStatus")
    @Expose
    private String processStatus;

    @SerializedName("approvalStatus")
    @Expose
    private String approvalStatus;

    @SerializedName("statusRequest")
    @Expose
    private String statusRequest;

    @SerializedName("statusRequestName")
    @Expose
    private String statusRequestName;

    public Long getId() {
        return id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getCompany() {
        return company;
    }

    public Long getPersonnelNumber() {
        return personnelNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public String getStatusRequest() {
        return statusRequest;
    }

    public String getStatusRequestName() {
        return statusRequestName;
    }
}
