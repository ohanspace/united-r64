package org.badhan.r64.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Cadre implements Parcelable{
    private int id;
    private String rollNo;
    private String cadreId;
    private String telephone;
    private String email;
    private String displayName;
    private String batch;
    private String cadreType;
    private String homeDistrict;
    private String postingDistrict;
    private String postingAddress;
    private String university;
    private String session;
    private String bloodGroup;
    private String avatarUrl;


    public String getCadreBatchType(){
        return batch + "th" + cadreType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getCadreId() {
        return cadreId;
    }

    public void setCadreId(String cadreId) {
        this.cadreId = cadreId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCadreType() {
        return cadreType;
    }

    public void setCadreType(String cadreType) {
        this.cadreType = cadreType;
    }

    public String getHomeDistrict() {
        return homeDistrict;
    }

    public void setHomeDistrict(String homeDistrict) {
        this.homeDistrict = homeDistrict;
    }

    public String getPostingDistrict() {
        return postingDistrict;
    }

    public void setPostingDistrict(String postingDistrict) {
        this.postingDistrict = postingDistrict;
    }

    public String getPostingAddress() {
        return postingAddress;
    }

    public void setPostingAddress(String postingAddress) {
        this.postingAddress = postingAddress;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Creator<Cadre> CREATOR = new Creator<Cadre>() {
        @Override
        public Cadre createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public Cadre[] newArray(int i) {
            return new Cadre[0];
        }
    };
}
