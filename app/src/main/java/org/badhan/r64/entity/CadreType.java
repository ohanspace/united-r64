package org.badhan.r64.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public final class CadreType implements Parcelable{
    private String key;
    private String displayName;

    public CadreType(String key, String displayName) {
        this.key = key;
        this.displayName = displayName;
    }

    public String getKey() {
        return key;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ArrayList<CadreType> getAllTypes(){
        ArrayList<CadreType> types = new ArrayList<CadreType>();

        types.add(new CadreType("ADMINISTRATION", "BCS (Administration)"));
        types.add(new CadreType("AGRICULTURE", "BCS (Agriculture)"));
        types.add(new CadreType("ECONOMIC", "BCS (Economic)"));
        types.add(new CadreType("FISHERIES", "BCS (Fisheries)"));
        types.add(new CadreType("INFORMATION", "BCS (Information)"));
        types.add(new CadreType("JUDICIAL_SERVICE", "Bangladesh Judicial Service"));
        types.add(new CadreType("LIVESTOCK", "BCS (Livestock)"));
        types.add(new CadreType("POLICE", "BCS (Police)"));
        types.add(new CadreType("PUBLIC_WORKS", "BCS (Public Works)"));
        types.add(new CadreType("TAXATION", "BCS (Taxation)"));

        return types;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Creator<CadreType> CREATOR = new Creator<CadreType>() {
        @Override
        public CadreType createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public CadreType[] newArray(int i) {
            return new CadreType[0];
        }
    };
}

