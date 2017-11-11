package org.badhan.r64.core;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.otto.Bus;

import org.badhan.r64.service.Module;

public class MyApplication extends Application {
    private Auth auth;
    private Bus bus;

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    private FirebaseAuth firebaseAuth;

    public MyApplication(){
        bus = new Bus();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        auth = new Auth(this);
        firebaseAuth = FirebaseAuth.getInstance();
        Module.register(this);
    }

    public Auth getAuth() {
        return auth;
    }

    public Bus getBus(){
        return bus;
    }
}
