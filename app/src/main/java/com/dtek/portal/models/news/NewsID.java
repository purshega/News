package com.dtek.portal.models.news;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NewsID implements Parcelable {

    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("Title")
    @Expose
    private String title;

    @SerializedName("Body")
    @Expose
    private String body;

    @SerializedName("Image")
    @Expose
    private String image;

    @SerializedName("PublishedDate")
    @Expose
    private String publishedDate;

    @SerializedName("Subtitle")
    @Expose
    private String subtitle;

    @SerializedName("LikesCount")
    @Expose
    private Integer likesCount;

    @SerializedName("NumComments")
    @Expose
    private Integer numComments;


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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public void setNumComments(Integer numComments) {
        this.numComments = numComments;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeString(this.image);
        dest.writeString(this.publishedDate);
        dest.writeString(this.subtitle);
        dest.writeValue(this.likesCount);
        dest.writeValue(this.numComments);
    }

    public NewsID() {
    }

    protected NewsID(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.body = in.readString();
        this.image = in.readString();
        this.publishedDate = in.readString();
        this.subtitle = in.readString();
        this.likesCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.numComments = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<NewsID> CREATOR = new Creator<NewsID>() {
        @Override
        public NewsID createFromParcel(Parcel source) {
            return new NewsID(source);
        }

        @Override
        public NewsID[] newArray(int size) {
            return new NewsID[size];
        }
    };
}
