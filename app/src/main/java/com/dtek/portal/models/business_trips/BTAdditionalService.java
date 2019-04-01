package com.dtek.portal.models.business_trips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BTAdditionalService {

    @SerializedName("additionalService")
    @Expose
    private AdditionalService additionalService;

    @SerializedName("comment")
    @Expose
    private String comment;

    public AdditionalService getAdditionalService() {
        return additionalService;
    }

    public void setAdditionalService(AdditionalService additionalService) {
        this.additionalService = additionalService;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public class AdditionalService{
        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("name")
        @Expose
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }


}
