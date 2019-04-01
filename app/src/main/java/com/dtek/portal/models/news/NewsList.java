
package com.dtek.portal.models.news;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class NewsList implements Parcelable {

    @SerializedName("nextPage")
    @Expose
    private Boolean nextPage;

    @SerializedName("news")
    @Expose
    private List<News> newses = new ArrayList<News>();


    public Boolean getNextPage() {
        return nextPage;
    }

    public void setNextPage(Boolean nextPage) {
        this.nextPage = nextPage;
    }

    public List<News> getNewses() {
        return newses;
    }

    public void setNewses(List<News> newses) {
        this.newses = newses;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.nextPage);
        dest.writeTypedList(this.newses);
    }

    public NewsList() {
    }

    protected NewsList(Parcel in) {
        this.nextPage = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.newses = in.createTypedArrayList(News.CREATOR);
    }

    public static final Creator<NewsList> CREATOR = new Creator<NewsList>() {
        @Override
        public NewsList createFromParcel(Parcel source) {
            return new NewsList(source);
        }

        @Override
        public NewsList[] newArray(int size) {
            return new NewsList[size];
        }
    };
}
