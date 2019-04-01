package com.dtek.portal.models.news;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NewsComment implements Parcelable {

    @SerializedName("PostId")
    @Expose
    private Integer PostId;

    @SerializedName("Title")
    @Expose
    private String title;

    @SerializedName("Body")
    @Expose
    private String body;

    @SerializedName("Author")
    @Expose
    private String author;

    @SerializedName("PublishedDate")
    @Expose
    private String publishedDate;

    public Integer getPostId() {
        return PostId;
    }

    public void setPostId(Integer postId) {
        PostId = postId;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.PostId);
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeString(this.author);
        dest.writeString(this.publishedDate);
    }

    public NewsComment() {
    }

    protected NewsComment(Parcel in) {
        this.PostId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.body = in.readString();
        this.author = in.readString();
        this.publishedDate = in.readString();
    }

    public static final Creator<NewsComment> CREATOR = new Creator<NewsComment>() {
        @Override
        public NewsComment createFromParcel(Parcel source) {
            return new NewsComment(source);
        }

        @Override
        public NewsComment[] newArray(int size) {
            return new NewsComment[size];
        }
    };
}
