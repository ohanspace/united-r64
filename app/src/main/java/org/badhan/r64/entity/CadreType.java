package org.badhan.r64.entity;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

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
        ArrayList<CadreType> types = new ArrayList<CadreType>();

        types.add(new CadreType("ADMINISTRATION", "BCS (Administration)","#9575CD"));
        types.add(new CadreType("AGRICULTURE", "BCS (Agriculture)","#42A5F5"));
        types.add(new CadreType("ECONOMIC", "BCS (Economic)","#0097A7"));
        types.add(new CadreType("FISHERIES", "BCS (Fisheries)","#009688"));
        types.add(new CadreType("INFORMATION", "BCS (Information)","#FF8A65"));
        types.add(new CadreType("JUDICIAL_SERVICE", "Bangladesh Judicial Service","#8BC34A"));
        types.add(new CadreType("LIVESTOCK", "BCS (Livestock)","#1DE9B6"));
        types.add(new CadreType("POLICE", "BCS (Police)","#F06292"));
        types.add(new CadreType("PUBLIC_WORKS", "BCS (Public Works)","#FF5252"));
        types.add(new CadreType("TAXATION", "BCS (Taxation)","#9C27B0"));

        return types;
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

