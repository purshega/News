package com.dtek.portal.models.hr_vacation;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VacationList {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Vacation> limitsList = null;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Vacation> getLimitsList() {
        return limitsList;
    }

    public class Vacation implements Parcelable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("days")
        @Expose
        private Integer days;

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Integer getDays() {
            return days;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeString(this.name);
            dest.writeValue(this.days);
        }

        public Vacation() {
        }

        protected Vacation(Parcel in) {
            this.id = (Integer) in.readValue(Integer.class.getClassLoader());
            this.name = in.readString();
            this.days = (Integer) in.readValue(Integer.class.getClassLoader());
        }

        public final Creator<Vacation> CREATOR = new Creator<Vacation>() {
            @Override
            public Vacation createFromParcel(Parcel source) {
                return new Vacation(source);
            }

            @Override
            public Vacation[] newArray(int size) {
                return new Vacation[size];
            }
        };
    }
}
