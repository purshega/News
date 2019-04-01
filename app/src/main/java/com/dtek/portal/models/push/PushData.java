package com.dtek.portal.models.push;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PushData implements Parcelable {

    @SerializedName("action")
    @Expose
    private String action;

    @SerializedName("request_id")
    @Expose
    private int request_id;

    public PushData() {
    }

    public PushData(String action, int request_id) {
        this.action = action;
        this.request_id = request_id;
    }

    public String getAction() {
        return action;
    }

    public int getRequest_id() {
        return request_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected PushData(Parcel in) {
        this.action = in.readString();
        this.request_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(action);
        dest.writeInt(request_id);
    }

    public static final Parcelable.Creator<PushData> CREATOR = new Parcelable.Creator<PushData>() {
        public PushData createFromParcel(Parcel in) {
            return new PushData(in);
        }

        public PushData[] newArray(int size) {
            return new PushData[size];
        }
    };

    @Override
    public String toString() {
        return "PushData{" +
                "action='" + action + '\'' +
                ", request_id=" + request_id +
                '}';
    }
}
