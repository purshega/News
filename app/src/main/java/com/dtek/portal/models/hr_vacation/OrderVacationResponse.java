package com.dtek.portal.models.hr_vacation;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class OrderVacationResponse implements Parcelable {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("message")
    @Expose
    private String message;

    // поля для получение
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("idLocation")
    @Expose
    private int idLocation;
    @SerializedName("nameLocation")
    @Expose
    private String nameLocation;
    @SerializedName("idVacationType")
    @Expose
    private int idVacation;
    @SerializedName("nameVacation")
    @Expose
    private String nameVacation;
    @SerializedName("days")
    @Expose
    private int days;
    @SerializedName("prepaymentDays")
    @Expose
    private int prepaymentDays;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("summDays")
    @Expose
    private int summDays;
    @SerializedName("skipChief")
    @Expose
    private boolean skipChief;
    @SerializedName("comment")
    @Expose
    private String comment;


    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }


    public Integer getId() {
        return id;
    }

    public int getIdLocation() {
        return idLocation;
    }

    public String getNameLocation() {
        return nameLocation;
    }

    public int getIdVacation() {
        return idVacation;
    }

    public String getNameVacation() {
        return nameVacation;
    }

    public int getDays() {
        return days;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getSummDays() {
        return summDays;
    }

    public int getPrepaymentDays() {
        return prepaymentDays;
    }

    public boolean isSkipChief() {
        return skipChief;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "OrderVacationResponse{" +
                ", id=" + id +
                ", idLocation=" + idLocation +
                ", nameLocation='" + nameLocation + '\'' +
                ", idVacation=" + idVacation +
                ", nameVacation='" + nameVacation + '\'' +
                ", days=" + days +
                ", prepaymentDays=" + prepaymentDays +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", summDays=" + summDays +
                ", skipChief=" + skipChief +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeInt(this.idLocation);
        dest.writeString(this.nameLocation);
        dest.writeInt(this.idVacation);
        dest.writeString(this.nameVacation);
        dest.writeInt(this.days);
        dest.writeInt(this.prepaymentDays);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeInt(this.summDays);
        dest.writeByte(this.skipChief ? (byte) 1 : (byte) 0);
        dest.writeString(this.comment);
    }

    public OrderVacationResponse() {
    }

    protected OrderVacationResponse(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.idLocation = in.readInt();
        this.nameLocation = in.readString();
        this.idVacation = in.readInt();
        this.nameVacation = in.readString();
        this.days = in.readInt();
        this.prepaymentDays = in.readInt();
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.summDays = in.readInt();
        this.skipChief = in.readByte() != 0;
        this.comment = in.readString();
    }

    public static final Creator<OrderVacationResponse> CREATOR = new Creator<OrderVacationResponse>() {
        @Override
        public OrderVacationResponse createFromParcel(Parcel source) {
            return new OrderVacationResponse(source);
        }

        @Override
        public OrderVacationResponse[] newArray(int size) {
            return new OrderVacationResponse[size];
        }
    };
}
