package com.dtek.portal.models.itsm;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItService implements Parcelable {

    @SerializedName("id_order") // id
    @Expose
    private String idOrder;
    @SerializedName("subject") // тема
    @Expose
    private String subject;
    @SerializedName("status") // статус
    @Expose
    private String status;

    public String getIdOrder() {
        return idOrder;
    }

    public String getSubject() {
        return subject;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idOrder);
        dest.writeString(this.subject);
        dest.writeString(this.status);
    }

    public ItService() {
    }

    public ItService(Parcel in) {
        this.idOrder = in.readString();
        this.subject = in.readString();
        this.status = in.readString();
    }

    public static final Creator<ItService> CREATOR = new Creator<ItService>() {
        @Override
        public ItService createFromParcel(Parcel source) {
            return new ItService(source);
        }

        @Override
        public ItService[] newArray(int size) {
            return new ItService[size];
        }
    };
}
