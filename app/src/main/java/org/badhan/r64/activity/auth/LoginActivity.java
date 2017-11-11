package org.badhan.r64.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.badhan.r64.R;
import org.badhan.r64.activity.ProfileActivity;
import org.badhan.r64.activity.auth.handler.OnLoginBtnClickHandler;
import org.badhan.r64.core.BaseActivity;
import org.badhan.r64.core.Constants;
import org.badhan.r64.service.auth.LoginWithEmail;
import org.badhan.r64.service.auth.LoginWithUsername;


public class LoginActivity extends BaseActivity {
    private static final int REQUEST_REGISTER_ACTIVITY = 1;

    private View loginBtn;
    private View registerBtn;
    public EditText emailField;
    public EditText passwordField;
    public View progressBarFrame;

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.activity_login);

        bindControls();
    }

    private void bindControls() {
        loginBtn = findViewById(R.id.login_activity_loginBtn);
        registerBtn = findViewById(R.id.login_activity_registerBtn);
        emailField = findViewById(R.id.login_activity_emailField);
        passwordField = findViewById(R.id.login_activity_passwordField);
        progressBarFrame = findViewById(R.id.login_activity_progressBarFrame);

        loginBtn.setOnClickListener(new OnLoginBtnClickHandler(this));
        registerBtn.setOnClickListener(new OnRegisterBtnClickHandler());
    }

    private class OnRegisterBtnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, REQUEST_REGISTER_ACTIVITY);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_REGISTER_ACTIVITY)
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
    public void onLoginWithEmail(LoginWithEmail.Response response){
        if (response.succeed())
            onSuccessLogin();

        emailField.setError(response.getPropertyError("email"));
        passwordField.setError(response.getPropertyError("password"));

        progressBarFrame.setVisibility(FrameLayout.GONE);
    }
}
