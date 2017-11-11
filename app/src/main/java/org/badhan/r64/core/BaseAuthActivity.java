package org.badhan.r64.core;

import android.content.Intent;
import android.os.Bundle;

import org.badhan.r64.activity.auth.LocalTokenAuthenticationActivity;
import org.badhan.r64.activity.auth.LoginActivity;

public abstract class BaseAuthActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);

        if(!application.getAuth().getUser().isLoggedIn()){
            Intent intent;
            if (application.getAuth().hasAuthToken()){
                intent = new Intent(this, LocalTokenAuthenticationActivity.class);
                intent.putExtra(LocalTokenAuthenticationActivity.EXTRA_REDIRECT_TO_ACTIVITY, getClass().getName());
            }else
                intent = new Intent(this, LoginActivity.class);

            startActivity(intent);
            finish();
            return;
        }

        onAppCreate(savedState);
    }

    protected abstract void onAppCreate(Bundle savedState);
}
