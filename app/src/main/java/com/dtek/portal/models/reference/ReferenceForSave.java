package com.dtek.portal.models.reference;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferenceForSave {
    @SerializedName("root")
    @Expose
    private Root root;

    public void setRoot(Root root) {
        this.root = root;
    }

    public static class Root{
        @SerializedName("address")
        @Expose
        private String address;

        @SerializedName("room")
        @Expose
        private String room;

        @SerializedName("urgency")
        @Expose
        private String urgency = "3";

        @SerializedName("subject")
        @Expose
        private String subject;

        @SerializedName("details")
        @Expose
        private String details;

        @SerializedName("personid")
        @Expose
        private String personId;

        @SerializedName("affectedid")
        @Expose
        private String affectedid;

        @SerializedName("class")
        @Expose
        private String classNumber;

        public void setAddress(String address) {
            this.address = address;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public void setUrgency(String urgency) {
            this.urgency = urgency;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public void setClassNumber(String classNumber) {
            this.classNumber = classNumber;
        }

        public void setAffectedid(String affectedid) {
            this.affectedid = affectedid;
        }
    }
}
