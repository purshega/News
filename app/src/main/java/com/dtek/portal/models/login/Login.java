package com.dtek.portal.models.login;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login implements Parcelable {

    @SerializedName("Status")
    @Expose
    private boolean status;

    @SerializedName("IsCorporate")
    @Expose
    private boolean corporate;

    @SerializedName("Tel")
    @Expose
    private String tel;

    @SerializedName("Text")
    @Expose
    private String text;

    public boolean isStatus() {
        return status;
    }

    public boolean isCorporate() {
        return corporate;
    }

    public String getTel() {
        return tel;
    }

    public String getText() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
        dest.writeByte(this.corporate ? (byte) 1 : (byte) 0);
        dest.writeString(this.tel);
        dest.writeString(this.text);
    }

    public Login() {
    }

    protected Login(Parcel in) {
        this.status = in.readByte() != 0;
        this.corporate = in.readByte() != 0;
        this.tel = in.readString();
        this.text = in.readString();
    }

    public static final Creator<Login> CREATOR = new Creator<Login>() {
        @Override
        public Login createFromParcel(Parcel source) {
            return new Login(source);
        }

        @Override
        public Login[] newArray(int size) {
            return new Login[size];
        }
    };
}
