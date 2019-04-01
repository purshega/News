package com.dtek.portal.models.reference;

import com.dtek.portal.models.itsm.ItPerson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferencePersons {

    @SerializedName("root")
    @Expose
    private Persons persons;

    public Persons getPersons() {
        return persons;
    }

    public class Persons{
       @SerializedName("person")
       @Expose
       private List<ItPerson> personsList;

        public List<ItPerson> getPersonsList() {
            return personsList;
        }
    }
}
