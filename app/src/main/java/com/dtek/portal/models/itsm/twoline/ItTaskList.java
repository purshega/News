package com.dtek.portal.models.itsm.twoline;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItTaskList implements Parcelable {

    @SerializedName("order_type") // класс
    @Expose
    private String typeNumber;
    @SerializedName("id_order") // номер
    @Expose
    private String number;
    @SerializedName("address1") // адресс
    @Expose
    private String address;
    @SerializedName("address2") // город
    @Expose
    private String city;
    @SerializedName("room") // комната
    @Expose
    private String room;

    private String fullAddress;

    @SerializedName("priority") // приоритет
    @Expose
    private String priority;
    @SerializedName("subject") // описание
    @Expose
    private String description;
    @SerializedName("vip") // заявка вип?
    @Expose
    private Boolean vip;

    public String getTypeNumber() {
        return typeNumber;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getRoom() {
        return room;
    }

    public String getFullAddress() {
        return fullAddress = String.format("%s %s %s", getCity(), getAddress(), getRoom());
    }

    public String getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isVip() {
        return vip;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.typeNumber);
        dest.writeString(this.number);
//        dest.writeString(this.address);
//        dest.writeString(this.city);
//        dest.writeString(this.room);
        dest.writeString(this.fullAddress);
        dest.writeString(this.priority);
        dest.writeString(this.description);
        dest.writeValue(this.vip);
    }

    protected ItTaskList(Parcel in) {
        this.typeNumber = in.readString();
        this.number = in.readString();
//        this.address = in.readString();
//        this.city = in.readString();
//        this.room = in.readString();
        this.fullAddress = in.readString();
        this.priority = in.readString();
        this.description = in.readString();
        this.vip = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<ItTaskList> CREATOR = new Creator<ItTaskList>() {
        @Override
        public ItTaskList createFromParcel(Parcel source) {
            return new ItTaskList(source);
        }

        @Override
        public ItTaskList[] newArray(int size) {
            return new ItTaskList[size];
        }
    };
}
