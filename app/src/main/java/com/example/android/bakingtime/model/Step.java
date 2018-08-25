package com.example.android.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable{

    public static final String SAVED_INTENT = "saved_step_intent_key";
    public static final String CURRENT = "current_step_key";
    public int id;
    public String shortDescription = "";
    public String description = "";
    public String videoURL = "";
    public String thumbnailURL = "";

    public Step() {}

    protected Step(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }
}
