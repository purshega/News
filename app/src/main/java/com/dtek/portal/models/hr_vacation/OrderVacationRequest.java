package com.dtek.portal.models.hr_vacation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class OrderVacationRequest {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;

// поля для отправки
    @SerializedName("userLogin")
    @Expose
    private String userLogin;
    @SerializedName("idVacation")
    @Expose
    private Integer idVacation;
    @SerializedName("VacationTypeId")
    @Expose
    private int vacationTypeId;
    @SerializedName("DestinationID")
    @Expose
    private int destinationID;
    @SerializedName("startDate")
    @Expose
    private Date dateStart;
    @SerializedName("finishDate")
    @Expose
    private Date dateFinish;
    @SerializedName("skipChief")
    @Expose
    private boolean skipChief;
    @SerializedName("comments")
    @Expose
    private String comments;


    public Boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }


    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public void setIdVacation(Integer idVacation) {
        this.idVacation = idVacation;
    }

    public void setVacationTypeId(int vacationTypeId) {
        this.vacationTypeId = vacationTypeId;
    }

    public void setDestinationID(int destinationID) {
        this.destinationID = destinationID;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public void setSkipChief(boolean skipChief) {
        this.skipChief = skipChief;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "OrderVacationRequest{" +
                "userLogin='" + userLogin + '\'' +
                ", idVacation=" + idVacation +
                ", vacationTypeId=" + vacationTypeId +
                ", destinationID=" + destinationID +
                ", dateStart=" + dateStart +
                ", dateFinish=" + dateFinish +
                ", skipChief=" + skipChief +
                ", comments='" + comments + '\'' +
                '}';
    }
}
