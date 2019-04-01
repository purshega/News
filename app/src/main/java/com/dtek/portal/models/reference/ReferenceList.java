package com.dtek.portal.models.reference;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferenceList {

    @SerializedName("root")
    @Expose
    private Root root;

    public Root getRoot() {
        return root;
    }

    public class Root {

        @SerializedName("class")
        @Expose
        private List<Reference> referenceList = null;

        public List<Reference> getReferenceList() {
            return referenceList;
        }
    }

}
