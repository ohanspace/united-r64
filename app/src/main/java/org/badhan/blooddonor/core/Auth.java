package org.badhan.blooddonor.core;

import android.content.Context;

import org.badhan.blooddonor.entity.User;

public class Auth {
    private final Context context;
    private User user;

    public Auth(Context context) {
        this.context = context;
        user = new User();
    }


    public User getUser() {
        return user;
    }
}
