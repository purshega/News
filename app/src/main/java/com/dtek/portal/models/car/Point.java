package com.dtek.portal.models.car;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Point implements Parcelable {

    private int number; // порядковфй номер доп. пункта

    @SerializedName("Adress")
    @Expose
    private String address; // адрес

    @SerializedName("Date")
    @Expose
    private long datePoint; // время прибытия в доп. пункт в сек

    @SerializedName("WaitOrTakeAway")
    @Expose
    private String waitOrTakeAway; // ожидать/забрать

    @SerializedName("WaitMinutes")
    @Expose
    private String waitMinutes; // ожидать минут

    @SerializedName("DateTakeAway")
    @Expose
    private long dateTakeAway; // забрать в доп. пункт

    public Point(int number, String address, long datePoint, String waitOrTakeAway, String waitMinutes, long dateTakeAway) {
        this.number = number;
        this.address = address;
        this.datePoint = datePoint;
        this.waitOrTakeAway = waitOrTakeAway;
        this.waitMinutes = waitMinutes;
        this.dateTakeAway = dateTakeAway;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getDatePoint() {
        return datePoint;
    }

    public void setDatePoint(long datePoint) {
        this.datePoint = datePoint;
    }

    public String getWaitMinutes() {
        return waitMinutes;
    }

    public void setWaitMinutes(String waitMinutes) {
        this.waitMinutes = waitMinutes;
    }

    public String getWaitOrTakeAway() {
        return waitOrTakeAway;
    }

    public void setWaitOrTakeAway(String waitOrTakeAway) {
        this.waitOrTakeAway = waitOrTakeAway;
    }

    public long getDateTakeAway() {
        return dateTakeAway;
    }

    public void setDateTakeAway(long dateTakeAway) {
        this.dateTakeAway = dateTakeAway;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.number);
        dest.writeString(this.address);
        dest.writeLong(this.datePoint);
        dest.writeString(this.waitOrTakeAway);
        dest.writeString(this.waitMinutes);
        dest.writeLong(this.dateTakeAway);
    }

    protected Point(Parcel in) {
        this.number = in.readInt();
        this.address = in.readString();
        this.datePoint = in.readLong();
        this.waitOrTakeAway = in.readString();
        this.waitMinutes = in.readString();
        this.dateTakeAway = in.readLong();
    }

    public static final Creator<Point> CREATOR = new Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel source) {
            return new Point(source);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (number != point.number) return false;
        if (datePoint != point.datePoint) return false;
        if (dateTakeAway != point.dateTakeAway) return false;
        if (address != null ? !address.equals(point.address) : point.address != null)
            return false;
        if (waitOrTakeAway != null ? !waitOrTakeAway.equals(point.waitOrTakeAway) : point.waitOrTakeAway != null)
            return false;
        return waitMinutes != null ? waitMinutes.equals(point.waitMinutes) : point.waitMinutes == null;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (int) (datePoint ^ (datePoint >>> 32));
        result = 31 * result + (waitOrTakeAway != null ? waitOrTakeAway.hashCode() : 0);
        result = 31 * result + (waitMinutes != null ? waitMinutes.hashCode() : 0);
        result = 31 * result + (int) (dateTakeAway ^ (dateTakeAway >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "number=" + number +
                ", address='" + address + '\'' +
                ", datePoint=" + datePoint +
                ", waitOrTakeAway='" + waitOrTakeAway + '\'' +
                ", waitMinutes='" + waitMinutes + '\'' +
                ", dateTakeAway=" + dateTakeAway +
                '}';
    }
}
