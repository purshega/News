package com.dtek.portal.models.youtube;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoYouTube implements Parcelable {

    @SerializedName("VideoUrl")
    @Expose
    private String video_url;

    @SerializedName("Title")
    @Expose
    private String title;

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VideoYouTube(String video_url, String title) {
        this.video_url = video_url;
        this.title = title;
    }

    @Override
    public String toString() {
        return "VideoYouTube{" +
                "video_url='" + video_url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public VideoYouTube() {
    }

    protected VideoYouTube(Parcel in) {
        this.video_url = in.readString();
        this.title = in.readString();
    }

    public static final Creator<VideoYouTube> CREATOR = new Creator<VideoYouTube>() {
        @Override
        public VideoYouTube createFromParcel(Parcel source) {
            return new VideoYouTube(source);
        }

        @Override
        public VideoYouTube[] newArray(int size) {
            return new VideoYouTube[size];
        }
    };
}

