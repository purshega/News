package com.dtek.portal.models.business_trips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BT {

    @SerializedName("reqRatingValue")
    @Expose
    private Long reqRatingValue;

    @SerializedName("reqRatingComment")
    @Expose
    private String reqRatingComment;

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("createdDate")
    @Expose
    private Date createdDate;

    @SerializedName("createdTime")
    @Expose
    private Date createdTime;

    @SerializedName("statusRequest")
    @Expose
    private String statusRequest;

    @SerializedName("statusRequestName")
    @Expose
    private String statusRequestName;

    @SerializedName("userLogin")
    @Expose
    private String userLogin;

    @SerializedName("processStatus")
    @Expose
    private String processStatus;

    @SerializedName("approvalStatus")
    @Expose
    private String approvalStatus;

    @SerializedName("startDate")
    @Expose
    private Date startDate;

    @SerializedName("endDate")
    @Expose
    private Date endDate;

    @SerializedName("tripTypeId")
    @Expose
    private String tripTypeId;

    @SerializedName("tripFundId")
    @Expose
    private String tripFundId;

    @SerializedName("employee")
    @Expose
    private BTEmployee employee;

    @SerializedName("isDelay")
    @Expose
    private Boolean isDelay;

    @SerializedName("delayDate")
    @Expose
    private Date delayDate;

    @SerializedName("passportNumber")
    @Expose
    private String passportNumber;

    @SerializedName("passportDate")
    @Expose
    private Date passportDate;

    @SerializedName("hotels")
    @Expose
    private List<BTHotel> hotels;

    @SerializedName("destinations")
    @Expose
    private List<BTDestination> destinations;

    @SerializedName("tickets")
    @Expose
    private List<BTTicket> tickets;

    @SerializedName("additionalServices")
    @Expose
    private List<BTAdditionalService> additionalServices;

    @SerializedName("limits")
    @Expose
    private List<BTLimit> limits;

    @SerializedName("approver")
    @Expose
    private List<BTApprover> approver;

    @SerializedName("userFlag")
    @Expose
    private String userFlag;

    public BT() {
        hotels = new ArrayList<>();
        destinations = new ArrayList<>();
        tickets = new ArrayList<>();
        additionalServices = new ArrayList<>();
    }

    public Long getReqRatingValue() {
        return reqRatingValue;
    }

    public void setReqRatingValue(Long reqRatingValue) {
        this.reqRatingValue = reqRatingValue;
    }

    public String getReqRatingComment() {
        return reqRatingComment;
    }

    public void setReqRatingComment(String reqRatingComment) {
        this.reqRatingComment = reqRatingComment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getStatusRequest() {
        return statusRequest;
    }

    public void setStatusRequest(String statusRequest) {
        this.statusRequest = statusRequest;
    }

    public String getStatusRequestName() {
        return statusRequestName;
    }

    public void setStatusRequestName(String statusRequestName) {
        this.statusRequestName = statusRequestName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
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

    public String getTripTypeId() {
        return tripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId = tripTypeId;
    }

    public String getTripFundId() {
        return tripFundId;
    }

    public void setTripFundId(String tripFundId) {
        this.tripFundId = tripFundId;
    }

    public BTEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(BTEmployee employee) {
        this.employee = employee;
    }

    public Boolean getDelay() {
        return isDelay;
    }

    public void setDelay(Boolean delay) {
        isDelay = delay;
    }

    public Date getDelayDate() {
        return delayDate;
    }

    public void setDelayDate(Date delayDate) {
        this.delayDate = delayDate;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Date getPassportDate() {
        return passportDate;
    }

    public void setPassportDate(Date passportDate) {
        this.passportDate = passportDate;
    }

    public List<BTHotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<BTHotel> hotels) {
        this.hotels = hotels;
    }

    public List<BTDestination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<BTDestination> destinations) {
        this.destinations = destinations;
    }

    public List<BTTicket> getTickets() {
        return tickets;
    }

    public void setTickets(List<BTTicket> tickets) {
        this.tickets = tickets;
    }

    public List<BTAdditionalService> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(List<BTAdditionalService> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public List<BTLimit> getLimits() {
        return limits;
    }

    public void setLimits(List<BTLimit> limits) {
        this.limits = limits;
    }

    public List<BTApprover> getApprover() {
        return approver;
    }

    public void setApprover(List<BTApprover> approver) {
        this.approver = approver;
    }

    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }

    @Override
    public String toString() {
        return "BT{" +
                "reqRatingValue=" + reqRatingValue +
                ", reqRatingComment='" + reqRatingComment + '\n' +
                ", id=" + id +
                ", createdDate=" + createdDate +
                ", createdTime=" + createdTime +
                ", statusRequest='" + statusRequest + '\n' +
                ", statusRequestName='" + statusRequestName + '\n' +
                ", userLogin='" + userLogin + '\n' +
                ", processStatus='" + processStatus + '\n' +
                ", approvalStatus='" + approvalStatus + '\n' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", tripTypeId='" + tripTypeId + '\n' +
                ", tripFundId='" + tripFundId + '\n' +
                ", employee=" + employee +
                ", isDelay=" + isDelay +
                ", delayDate=" + delayDate +
                ", passportNumber='" + passportNumber + '\n' +
                ", passportDate=" + passportDate +
                ", hotels=" + hotels +
                ", destinations=" + destinations +
                ", tickets=" + tickets +
                ", additionalServices=" + additionalServices +
                ", limits=" + limits +
                ", approver=" + approver +
                ", userFlag='" + userFlag + '\n' +
                '}';
    }
}
