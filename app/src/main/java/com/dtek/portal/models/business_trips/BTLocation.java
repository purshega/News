package com.dtek.portal.models.business_trips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BTLocation {
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("country")
    @Expose
    private Country country;
    @SerializedName("region")
    @Expose
    private Region region;

    public City getCity() {
        return city;
    }

    public Country getCountry() {
        return country;
    }

    public Region getRegion() {
        return region;
    }

    public class City {
        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("name")
        @Expose
        private String name;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public class Country {
        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("name")
        @Expose
        private String name;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public class Region {
        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("name")
        @Expose
        private String name;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}
