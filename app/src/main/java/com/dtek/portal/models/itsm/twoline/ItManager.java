package com.dtek.portal.models.itsm.twoline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItManager {

    @SerializedName("root")
    @Expose
    private Manager root;

    public Manager getRoot() {
        return root;
    }

    public class Manager {

        @SerializedName("isManager")
        @Expose
        private Boolean manager;
        @SerializedName("personid")
        @Expose
        private String personId;

        public Boolean isManager() {
            return manager;
        }

        public String getPersonId() {
            return personId;
        }
    }

}
