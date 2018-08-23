package com.example.android.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

public class Ingredient implements Parcelable{

    public float quantity;
    public String measure;
    @Json(name = "ingredient")
    public String name;


    protected Ingredient(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        name = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(name);
    }
}
