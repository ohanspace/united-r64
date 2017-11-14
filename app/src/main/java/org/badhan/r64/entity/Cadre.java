package org.badhan.r64.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Cadre implements Parcelable{
    private int id;
    private String group;
    private String rollNo;
    private String cadreId;
    private String telephone;
    private String email;
    private String name;
    private String batch;
    private String cadreType;
    private String homeDistrict;
    private String postingDistrict;
    private String postingAddress;
    private String university;
    private String session;
    private String bloodGroup;
    private String avatarUrl;


    public Cadre(){
    }


    public Cadre(Parcel parcel) {
        id = parcel.readInt();
        group = parcel.readString();
        rollNo = parcel.readString();
        cadreId = parcel.readString();
        telephone = parcel.readString();
        email = parcel.readString();
        name = parcel.readString();
        batch = parcel.readString();
        cadreType = parcel.readString();
        homeDistrict = parcel.readString();
        postingAddress = parcel.readString();
        university = parcel.readString();
        session = parcel.readString();
        bloodGroup = parcel.readString();
        avatarUrl = parcel.readString();
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //ORDER is important
        parcel.writeInt(id);
        parcel.writeString(group);
        parcel.writeString(rollNo);
        parcel.writeString(cadreId);
        parcel.writeString(telephone);
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(batch);
        parcel.writeString(cadreType);
        parcel.writeString(homeDistrict);
        parcel.writeString(postingAddress);
        parcel.writeString(university);
        parcel.writeString(session);
        parcel.writeString(bloodGroup);
        parcel.writeString(avatarUrl);
    }

    public String getSearchableText(){
        return getName()+ "," +
                getBatch() +"th batch" +
                getBloodGroup() + "," +
                getCadreType() + "," +
                getHomeDistrict() + "," +
                getPostingAddress();
    }

    public StorageReference getAvatarStorageRef(){
        if (getAvatarUri() == null){
            setAvatarUrl("fakeUri"); // for avoiding illegal reference error
        }
        StorageReference ref = FirebaseStorage.getInstance()
                .getReference(getAvatarUri());
        return ref;
    }


    public String getCadreBatchType(){
        return batch + " th BCS" + " ("+cadreType.toLowerCase()+")";
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

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getDisplayName() {
        return name;
    }

    public void setDisplayName(String displayName) {
        this.name = displayName;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getAvatarUri(){
        return avatarUrl;
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

    public static final Creator<Cadre> CREATOR = new Creator<Cadre>() {
        @Override
        public Cadre createFromParcel(Parcel parcel) {
            return new Cadre(parcel);
        }

        @Override
        public Cadre[] newArray(int size) {
            return new Cadre[size];
        }
    };
}
