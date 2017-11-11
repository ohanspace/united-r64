package org.badhan.r64.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class Message implements Parcelable{
    private int id;
    private Calendar createdAt;
    private String shortMessage;
    private String longMessage;
    private String imageUrl;
    private UserDetails otherUser;
    private boolean isFromMe;
    private boolean isRead;
    private boolean isSelected;

    public Message(int id,
                   Calendar createdAt,
                   String shortMessage,
                   String longMessage,
                   String imageUrl,
                   UserDetails otherUser,
                   boolean isFromMe,
                   boolean isRead) {
        this.id = id;
        this.createdAt = createdAt;
        this.shortMessage = shortMessage;
        this.longMessage = longMessage;
        this.imageUrl = imageUrl;
        this.otherUser = otherUser;
        this.isFromMe = isFromMe;
        this.isRead = isRead;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public String getLongMessage() {
        return longMessage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserDetails getOtherUser() {
        return otherUser;
    }

    public boolean isFromMe() {
        return isFromMe;
    }

    public boolean isRead() {
        return isRead;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public Message[] newArray(int i) {
            return new Message[0];
        }
    };
}
