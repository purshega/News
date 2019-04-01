package com.dtek.portal.models.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ServicePortal implements Parcelable {

    @SerializedName("ServiceName")
    @Expose
    private String serviceName;
    @SerializedName("Success")
    @Expose
    private boolean success;

    public String getServiceName() {
        return serviceName;
    }

    public boolean isSuccess() {
        return success;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.serviceName);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
    }

    public ServicePortal() {
    }

    protected ServicePortal(Parcel in) {
        this.serviceName = in.readString();
        this.success = in.readByte() != 0;
    }

    public static final Creator<ServicePortal> CREATOR = new Creator<ServicePortal>() {
        @Override
        public ServicePortal createFromParcel(Parcel source) {
            return new ServicePortal(source);
        }

        @Override
        public ServicePortal[] newArray(int size) {
            return new ServicePortal[size];
        }
    };
}
