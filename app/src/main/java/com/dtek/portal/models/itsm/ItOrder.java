package com.dtek.portal.models.itsm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItOrder {

    @SerializedName("root")
    @Expose
    private Root root;

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

    public static class Root {

        @SerializedName("address")
        @Expose
        private String address; // адресс заявки

        @SerializedName("room")
        @Expose
        private String room; // комната

        @SerializedName("urgency")
        @Expose
        private String urgency; // срочность

        @SerializedName("subject")
        @Expose
        private String subject; // тема

        @SerializedName("details")
        @Expose
        private String details; // описание

        @SerializedName("personid")
        @Expose
        private String personId;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getUrgency() {
            return urgency;
        }

        public void setUrgency(String urgency) {
            this.urgency = urgency;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

    }
}
