package com.dtek.portal.models.hr_vacation;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryList {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<History> historyList = null;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<History> getHistoryList() {
        return historyList;
    }

    public class History implements Parcelable {

        @SerializedName("id") // id
        @Expose
        public int id;
        @SerializedName("nameVacation") // тип отпуска
        @Expose
        private String nameVacation;
        @SerializedName("dates") // период
        @Expose
        private String period;
        @SerializedName("status") // статус
        @Expose
        private String status;
        @SerializedName("userName") // начальник
        @Expose
        private String userName;
        @SerializedName("canRejected") // можно отменить?
        @Expose
        private boolean canRejected;
        @SerializedName("canEdit") // можно редактировать?
        @Expose
        private boolean canEdit;

        public int getId() {
            return id;
        }

        public String getNameVacation() {
            return nameVacation;
        }

        public String getPeriod() {
            return period;
        }

        public String getStatus() {
            return status;
        }

        public String getUserName() {
            return userName;
        }

        public boolean isCanRejected() {
            return canRejected;
        }

        public boolean isCanEdit() {
            return canEdit;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeString(this.nameVacation);
            dest.writeString(this.period);
            dest.writeString(this.status);
            dest.writeString(this.userName);
            dest.writeByte(this.canRejected ? (byte) 1 : (byte) 0);
            dest.writeByte(this.canEdit ? (byte) 1 : (byte) 0);
        }

        public History() {
        }

        protected History(Parcel in) {
            this.id = (Integer) in.readValue(Integer.class.getClassLoader());
            this.nameVacation = in.readString();
            this.period = in.readString();
            this.status = in.readString();
            this.userName = in.readString();
            this.canRejected = in.readByte() != 0;
            this.canEdit = in.readByte() != 0;
        }

        public final Creator<History> CREATOR = new Creator<History>() {
            @Override
            public History createFromParcel(Parcel source) {
                return new History(source);
            }

            @Override
            public History[] newArray(int size) {
                return new History[size];
            }
        };
    }

}
