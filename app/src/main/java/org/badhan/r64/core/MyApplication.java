package org.badhan.r64.core;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.otto.Bus;

import org.badhan.r64.service.Module;

public class MyApplication extends Application {
    private Auth auth;
    private Bus bus;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    public MyApplication(){
        bus = new Bus();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        auth = new Auth(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Module.register(this);
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public Auth getAuth() {
        return auth;
    }

    public Bus getBus(){
        return bus;
    }
}
