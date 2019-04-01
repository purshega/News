package com.dtek.portal.models.gallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Folder {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Url")
    @Expose
    private String url;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
