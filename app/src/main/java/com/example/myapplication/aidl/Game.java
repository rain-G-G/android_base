package com.example.myapplication.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangxin on 2021/7/16.
 */

public class Game implements Parcelable {
    public String name;
    public String description;
    public Game(String gameName, String des) {
        name = gameName;
        description = des;
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public Game(Parcel in) {
        name = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
    }
}
