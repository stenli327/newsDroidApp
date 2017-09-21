package com.news.client;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsItem implements Parcelable {
    public String title;
    public String date;
    public String link;

    public NewsItem(String t, String d, String l) {
        title = t;
        date = d;
        link = l;
    }

    public NewsItem(Parcel in) {
        title = in.readString();
        date = in.readString();
        link = in.readString();
    }

    public NewsItem() {

    }

    public static final Creator<NewsItem> CREATOR
            = new Creator<NewsItem>() {
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(link);
    }
}