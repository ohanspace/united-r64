package org.badhan.r64.entity;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import org.badhan.r64.factory.CadreTypeFactory;

import java.util.ArrayList;

public final class CadreType implements Parcelable{
    private String key;
    private String displayName;
    private String color;

    public CadreType(Parcel parcel){
        key = parcel.readString();
        displayName = parcel.readString();
        color = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(displayName);
        parcel.writeString(color);

    }

    public CadreType(){}

    public CadreType(String key, String displayName) {
        this.key = key;
        this.displayName = displayName;
    }

    public CadreType(String key, String displayName, String color) {
        this.key = key;
        this.displayName = displayName;
        this.color = color;
    }

    public String getKey() {
        return key;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getColor() {
        return Color.parseColor(color);
    }

    public static ArrayList<CadreType> getAllTypes(){
        return CadreTypeFactory.getInstance().getAllTypes();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CadreType> CREATOR = new Creator<CadreType>() {
        @Override
        public CadreType createFromParcel(Parcel parcel) {
            return new CadreType(parcel);
        }

        @Override
        public CadreType[] newArray(int size) {
            return new CadreType[size];
        }
    };
}

