package com.dtek.portal.models.itsm.twoline;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItRootDetail {

    @SerializedName("root")
    @Expose
    private TaskDetail root;

    public TaskDetail getRoot() {
        return root;
    }

    public static class TaskDetail implements Parcelable {

        @SerializedName("id_order")
        @Expose
        private String idOrder; // №
        @SerializedName("type_order")
        @Expose
        private String typeOrder; // тип заявки
        @SerializedName("create_date")
        @Expose
        private String createDate; // дата содания
        @SerializedName("act_finish_sr")
        @Expose
        private String planDate; // дата завершения
        @SerializedName("status")
        @Expose
        private String status; // статус
        @SerializedName("author")
        @Expose
        private String author; // инициатор
        @SerializedName("user")
        @Expose
        private String user; // на кого влияет
        @SerializedName("city")
        @Expose
        private String city; // город
        @SerializedName("address")
        @Expose
        private String address; // адрес
        @SerializedName("room")
        @Expose
        private String room; // комната
        @SerializedName("phone_number")
        @Expose
        private String phone; // телефон
        @SerializedName("email")
        @Expose
        private String email; // мыло
        @SerializedName("priority")
        @Expose
        private String priority; // приоритет
        @SerializedName("service")
        @Expose
        private String service; // сервис
        @SerializedName("commodity")
        @Expose
        private String commodity; // услуга
        @SerializedName("subject")
        @Expose
        private String subject; // тема
        @SerializedName("details")
        @Expose
        private String details; // описание
        @SerializedName("siteid")
        @Expose
        private String siteId; // siteid
        @SerializedName("origrecordclass")
        @Expose
        private String origRecordClass="";
        @SerializedName("fact_time")
        @Expose
        private String factTime;

        public String getIdOrder() {
            return idOrder;
        }

        public String getTypeOrder() {
            return typeOrder;
        }

        public String getCreateDate() {
            return createDate;
        }

        public String getPlanDate() {
            return planDate;
        }

        public String getStatus() {
            return status;
        }

        public String getAuthor() {
            return author;
        }

        public String getUser() {
            return user;
        }

        public String getCity() {
            return city;
        }

        public String getAddress() {
            return address;
        }

        public String getRoom() {
            return room;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getPriority() {
            return priority;
        }

        public String getService() {
            return service;
        }

        public String getCommodity() {
            return commodity;
        }

        public String getSubject() {
            return subject;
        }

        public String getDetails() {
            return details;
        }

        public String getSiteId() {
            return siteId;
        }

        public String getOrigRecordClass() {
            return origRecordClass;
        }

        public String getFactTime() {
            return factTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.idOrder);
            dest.writeString(this.typeOrder);
            dest.writeString(this.createDate);
            dest.writeString(this.planDate);
            dest.writeString(this.status);
            dest.writeString(this.author);
            dest.writeString(this.user);
            dest.writeString(this.city);
            dest.writeString(this.address);
            dest.writeString(this.room);
            dest.writeString(this.phone);
            dest.writeString(this.email);
            dest.writeString(this.priority);
            dest.writeString(this.service);
            dest.writeString(this.commodity);
            dest.writeString(this.subject);
            dest.writeString(this.details);
            dest.writeString(this.siteId);
            dest.writeString(this.origRecordClass);
            dest.writeString(this.factTime);
        }

        public TaskDetail() {
        }

        protected TaskDetail(Parcel in) {
            this.idOrder = in.readString();
            this.typeOrder = in.readString();
            this.createDate = in.readString();
            this.planDate = in.readString();
            this.status = in.readString();
            this.author = in.readString();
            this.user = in.readString();
            this.city = in.readString();
            this.address = in.readString();
            this.room = in.readString();
            this.phone = in.readString();
            this.email = in.readString();
            this.priority = in.readString();
            this.service = in.readString();
            this.commodity = in.readString();
            this.subject = in.readString();
            this.details = in.readString();
            this.siteId = in.readString();
            this.origRecordClass = in.readString();
            this.factTime = in.readString();
        }

        public static final Creator<TaskDetail> CREATOR = new Creator<TaskDetail>() {
            @Override
            public TaskDetail createFromParcel(Parcel source) {
                return new TaskDetail(source);
            }

            @Override
            public TaskDetail[] newArray(int size) {
                return new TaskDetail[size];
            }
        };
    }

}
