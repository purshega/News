package com.dtek.portal.models.hr_vacation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TotalDaysRequest {

    @SerializedName("idVacation")
    @Expose
    private int idVacation;
    @SerializedName("startDate")
    @Expose
    private Date startDate;
    @SerializedName("finishDate")
    @Expose
    private Date finishDate;

    public int getIdVacation() {
        return idVacation;
    }

    public void setIdVacation(int idVacation) {
        this.idVacation = idVacation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

}
