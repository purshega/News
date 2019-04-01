package com.dtek.portal.models.business_trips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BTTicket {

    @SerializedName("isReturn")
    @Expose
    private Boolean isReturn;

    @SerializedName("ticketType")
    @Expose
    private String ticketType;

    @SerializedName("to")
    @Expose
    private BTLocation to;

    @SerializedName("from")
    @Expose
    private BTLocation from;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("departureDate")
    @Expose
    private Date departureDate;

    @SerializedName("departureTime")
    @Expose
    private Date departureTime;

    public Boolean getReturn() {
        return isReturn;
    }

    public void setReturn(Boolean aReturn) {
        isReturn = aReturn;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public BTLocation getTo() {
        return to;
    }

    public void setTo(BTLocation to) {
        this.to = to;
    }

    public BTLocation getFrom() {
        return from;
    }

    public void setFrom(BTLocation from) {
        this.from = from;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }
}
