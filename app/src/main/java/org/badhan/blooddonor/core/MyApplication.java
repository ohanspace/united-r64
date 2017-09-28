package org.badhan.blooddonor.core;

import android.app.Application;

public class MyApplication extends Application {
    private Auth auth;

    @Override
    public void onCreate(){
        super.onCreate();
        auth = new Auth(this);
    }

    public Auth getAuth() {
        return auth;
    }
}
