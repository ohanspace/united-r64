package org.badhan.blooddonor.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.activity.MainActivity;
import org.badhan.blooddonor.core.Auth;
import org.badhan.blooddonor.core.BaseActivity;
import org.badhan.blooddonor.service.auth.LoginWithLocalToken;

public class LocalTokenAuthenticationActivity extends BaseActivity {

    public static final String EXTRA_REDIRECT_TO_ACTIVITY = "EXTRA_REDIRECT_TO_ACTIVITY";
    private Auth auth;

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.activity_local_token_authentication);

        auth = application.getAuth();

        if (!auth.hasAuthToken()){
            redirectToLoginActivity();
            return;
        }

        bus.post(new LoginWithLocalToken.Request(auth.getAuthToken()));
    }

    private void redirectToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


    @Subscribe
    public void onLoginWithLocalToken(LoginWithLocalToken.Response response){
        if (!response.succeed()){
            Toast.makeText(this, "Invalid local auth token. Please login again", Toast.LENGTH_LONG).show();
            auth.setAuthToken(null);
            redirectToLoginActivity();
            return;
        }

        //if valid local token
        Intent intent;
        String redirectTo = getIntent().getStringExtra(EXTRA_REDIRECT_TO_ACTIVITY);
        Class homeActivity = MainActivity.class;

        if (redirectTo != null){
            try{
                intent = new Intent(this, Class.forName(redirectTo));
            }catch (Exception ignored){
                intent = new Intent(this, homeActivity);
            }
        }else {
            intent = new Intent(this, homeActivity);
        }

        startActivity(intent);
        finish();

    }
}
