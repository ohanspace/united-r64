package org.badhan.blooddonor.core;

import android.app.Application;

import com.squareup.otto.Bus;

import org.badhan.blooddonor.service.Module;

public class MyApplication extends Application {
    private Auth auth;
    private Bus bus;

    public MyApplication(){
        bus = new Bus();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        auth = new Auth(this);
        Module.register(this);
    }

    public Auth getAuth() {
        return auth;
    }

    public Bus getBus(){
        return bus;
    }
}
