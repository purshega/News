package com.dtek.portal.models.car;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Passenger implements Parcelable {

    @SerializedName("number")
    @Expose
    private int number; // порядковый номер

    @SerializedName("Type")
    @Expose
    private String type; // тип пассажира

    @SerializedName("FIO")
    @Expose
    private String fio; // фио

    @SerializedName("Login")
    @Expose
    private String login; // логин

    @SerializedName("NeedSMS")
    @Expose
    private boolean sms; // нужно ли смс

    @SerializedName("TelNum")
    @Expose
    private String phone; // номер смс

    @SerializedName("Comment")
    @Expose
    private String comment; // коментарий по пассажиру

    public Passenger(int number, String type, String fio, boolean sms, String phone, String comment) {
        this.number = number;
        this.type = type;
        this.fio = fio;
        this.sms = sms;
        this.phone = phone;
        this.comment = comment;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isSms() {
        return sms;
    }

    public void setSms(boolean sms) {
        this.sms = sms;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.number);
        dest.writeString(this.type);
        dest.writeString(this.fio);
        dest.writeString(this.login);
        dest.writeByte(this.sms ? (byte) 1 : (byte) 0);
        dest.writeString(this.phone);
        dest.writeString(this.comment);
    }

    protected Passenger(Parcel in) {
        this.number = in.readInt();
        this.type = in.readString();
        this.fio = in.readString();
        this.login = in.readString();
        this.sms = in.readByte() != 0;
        this.phone = in.readString();
        this.comment = in.readString();
    }

    public static final Creator<Passenger> CREATOR = new Creator<Passenger>() {
        @Override
        public Passenger createFromParcel(Parcel source) {
            return new Passenger(source);
        }

        @Override
        public Passenger[] newArray(int size) {
            return new Passenger[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger)) return false;

        Passenger passenger = (Passenger) o;

        if (number != passenger.number) return false;
        if (sms != passenger.sms) return false;
        if (type != null ? !type.equals(passenger.type) : passenger.type != null) return false;
        if (fio != null ? !fio.equals(passenger.fio) : passenger.fio != null) return false;
        if (login != null ? !login.equals(passenger.login) : passenger.login != null) return false;
        if (phone != null ? !phone.equals(passenger.phone) : passenger.phone != null) return false;
        return comment != null ? comment.equals(passenger.comment) : passenger.comment == null;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (fio != null ? fio.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (sms ? 1 : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "number=" + number +
                ", type='" + type + '\'' +
                ", fio='" + fio + '\'' +
                ", login='" + login + '\'' +
                ", sms=" + sms +
                ", phone='" + phone + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
