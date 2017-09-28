package org.badhan.blooddonor.core;

import android.content.Intent;
import android.os.Bundle;

import org.badhan.blooddonor.activity.auth.LoginActivity;

public abstract class BaseAuthActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);

        if(!application.getAuth().getUser().isLoggedIn()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        onAppCreate(savedState);
    }

    protected abstract void onAppCreate(Bundle savedState);
}
