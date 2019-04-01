package com.dtek.portal.models.newspaper;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Newspaper implements Parcelable {

    @SerializedName("Name")
    @Expose
    private String newspaper_name;

    @SerializedName("Language")
    @Expose
    private String language;

    @SerializedName("URL")
    @Expose
    private String newspaper_url;

    @SerializedName("PicURL")
    @Expose
    private String pic_url;

    public Newspaper() {
    }

    public String getNewspaper_name() {
        return newspaper_name;
    }

    public void setNewspaper_name(String newspaper_name) {
        this.newspaper_name = newspaper_name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNewspaper_url() {
        return newspaper_url;
    }

    public void setNewspaper_url(String newspaper_url) {
        this.newspaper_url = newspaper_url;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    protected Newspaper(Parcel in) {
        this.newspaper_name = in.readString();
        this.language = in.readString();
        this.newspaper_url = in.readString();
        this.pic_url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(newspaper_name);
        dest.writeString(language);
        dest.writeString(newspaper_url);
        dest.writeString(pic_url);
    }

    public static final Creator<Newspaper> CREATOR = new Creator<Newspaper>() {
        @Override
        public Newspaper createFromParcel(Parcel in) {
            return new Newspaper(in);
        }

        @Override
        public Newspaper[] newArray(int size) {
            return new Newspaper[size];
        }
    };

    @Override
    public String toString() {
        return "Newspaper{" +
                "newspaper_name='" + newspaper_name + '\'' +
                ", language='" + language + '\'' +
                ", newspaper_url='" + newspaper_url + '\'' +
                ", pic_url='" + pic_url + '\'' +
                '}';
    }
}
