package org.badhan.blooddonor.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.activity.ProfileActivity;
import org.badhan.blooddonor.activity.auth.handler.OnLoginBtnClickHandler;
import org.badhan.blooddonor.core.BaseActivity;
import org.badhan.blooddonor.core.Constants;
import org.badhan.blooddonor.service.auth.LoginWithUsername;


public class LoginActivity extends BaseActivity {
    private static final int REQUEST_REGISTER_ACTIVITY = 1;
    private static final int REQUEST_EXTERNAL_LOGIN_ACTIVITY = 2;

    private View loginBtn;
    private View registerBtn;
    private View facebookLoginBtn;
    private View googleLoginBtn;
    public EditText usernameField;
    public EditText passwordField;
    public FrameLayout progressBarFrame;

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.activity_login);

        bindControls();
    }

    private void bindControls() {
        loginBtn = findViewById(R.id.login_activity_loginBtn);
        registerBtn = findViewById(R.id.login_activity_registerBtn);
        facebookLoginBtn = findViewById(R.id.login_activity_facebookLoginBtn);
        googleLoginBtn = findViewById(R.id.login_activity_googleLoginBtn);
        usernameField = findViewById(R.id.login_activity_usernameField);
        passwordField = findViewById(R.id.login_activity_passwordField);
        progressBarFrame = findViewById(R.id.login_activity_progressBarFrame);

        loginBtn.setOnClickListener(new OnLoginBtnClickHandler(this));
        registerBtn.setOnClickListener(new OnRegisterBtnClickHandler());
        facebookLoginBtn.setOnClickListener(new OnExternalLoginBtnClickHandler());
        googleLoginBtn.setOnClickListener(new OnExternalLoginBtnClickHandler());
    }

    private class OnRegisterBtnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, REQUEST_REGISTER_ACTIVITY);
        }
    }

    private class OnExternalLoginBtnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view == facebookLoginBtn)
                startLoginActivityFor(Constants.FACEBOOK_PROVIDER_NAME);
            else if(view == googleLoginBtn){
                startLoginActivityFor(Constants.GOOGLE_PROVIDER_NAME);
            }
        }

        private void startLoginActivityFor(String providerName){
            Intent intent = new Intent(LoginActivity.this, ExternalLoginActivity.class);
            intent.putExtra(ExternalLoginActivity.EXTRA_PROVIDER_NAME, providerName);

            startActivityForResult(intent, REQUEST_EXTERNAL_LOGIN_ACTIVITY);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_REGISTER_ACTIVITY)
            onSuccessLogin();
        else if (requestCode == REQUEST_EXTERNAL_LOGIN_ACTIVITY)
            onSuccessLogin();

    }


    public void onSuccessLogin() {
        application.getAuth().getUser().setLoggedIn(true);
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Login succeed",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Subscribe
    public void onLoginWithUsername(LoginWithUsername.Response response){
        if (response.succeed())
            onSuccessLogin();

        usernameField.setError(response.getPropertyError("username"));
        passwordField.setError(response.getPropertyError("password"));

        progressBarFrame.setVisibility(FrameLayout.GONE);
    }
}
