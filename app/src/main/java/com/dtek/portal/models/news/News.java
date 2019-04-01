package com.dtek.portal.models.news;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class News implements Parcelable {

    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("Title")
    @Expose
    private String title;

    @SerializedName("Category")
    @Expose
    private String category;

    @SerializedName("Subtitle")
    @Expose
    private String subtitle;

    @SerializedName("Body")
    @Expose
    private String body;

    @SerializedName("PublishedDate")
    @Expose
    private String publishedDate;

    @SerializedName("Image")
    @Expose
    private String imageLink;

    private Bitmap picture;
//    private boolean isFromDatabase;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

//    public boolean isFromDatabase() {
//        return isFromDatabase;
//    }
//
//    public void setFromDatabase(boolean fromDatabase) {
//        isFromDatabase = fromDatabase;
//    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.category);
        dest.writeString(this.subtitle);
        dest.writeString(this.body);
        dest.writeString(this.publishedDate);
        dest.writeString(this.imageLink);
        dest.writeParcelable(this.picture, flags);
//        dest.writeByte(this.isFromDatabase ? (byte) 1 : (byte) 0);
    }

    public News() {
    }

    public News(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.category = in.readString();
        this.subtitle = in.readString();
        this.body = in.readString();
        this.publishedDate = in.readString();
        this.imageLink = in.readString();
        this.picture = in.readParcelable(Bitmap.class.getClassLoader());
//        this.isFromDatabase = in.readByte() != 0;
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
