package org.badhan.r64.entity;

import java.util.Calendar;

public class ContactRequest {
    private int id;
    private boolean isFromMe;
    private UserDetails userDetails;
    private Calendar createdAt;

    public ContactRequest(int id, boolean isFromMe, UserDetails userDetails, Calendar createdAt) {
        this.id = id;
        this.isFromMe = isFromMe;
        this.userDetails = userDetails;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public boolean isFromMe() {
        return isFromMe;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }
}
