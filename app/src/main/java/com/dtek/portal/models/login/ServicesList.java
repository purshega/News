package com.dtek.portal.models.login;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicesList implements Parcelable {

    @SerializedName("isValid")
    @Expose
    private boolean valid;

    @SerializedName("services")
    @Expose
    private List<ServicePortal> services = null;

    public boolean isValid() {
        return valid;
    }

    public List<ServicePortal> getServices() {
        return services;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.valid ? (byte) 1 : (byte) 0);
        dest.writeList(this.services);
    }

    public ServicesList() {
    }

    protected ServicesList(Parcel in) {
        this.valid = in.readByte() != 0;
        this.services = new ArrayList<ServicePortal>();
        in.readList(this.services, ServicePortal.class.getClassLoader());
    }

    public static final Creator<ServicesList> CREATOR = new Creator<ServicesList>() {
        @Override
        public ServicesList createFromParcel(Parcel source) {
            return new ServicesList(source);
        }

        @Override
        public ServicesList[] newArray(int size) {
            return new ServicesList[size];
        }
    };
}
