package com.dtek.portal.models.qr;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QrResp implements Parcelable {

    @SerializedName("Service")
    @Expose
    private String service;

    @SerializedName("Location")
    @Expose
    private String location;

    @SerializedName("Title")
    @Expose
    private String title;

    @SerializedName("Choices")
    @Expose
    private List<String> infoList = null;

    @SerializedName("Status")
    @Expose
    private boolean status;

    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<String> infoList) {
        this.infoList = infoList;
    }

    public boolean getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "QrResp{" +
                "service='" + service + '\'' +
                ", location='" + location + '\'' +
                ", title='" + title + '\'' +
                ", infoList=" + infoList +
                ", status='" + status + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public QrResp() {
    }

    protected QrResp(Parcel in) {
        this.service = in.readString();
        this.location = in.readString();
        this.title = in.readString();
        this.infoList = in.createStringArrayList();
    }

    public static final Creator<QrResp> CREATOR = new Creator<QrResp>() {
        @Override
        public QrResp createFromParcel(Parcel source) {
            return new QrResp(source);
        }

        @Override
        public QrResp[] newArray(int size) {
            return new QrResp[size];
        }
    };
}
