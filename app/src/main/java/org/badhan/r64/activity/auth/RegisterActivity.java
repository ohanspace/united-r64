package org.badhan.r64.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.otto.Subscribe;

import org.badhan.r64.R;
import org.badhan.r64.core.BaseActivity;
import org.badhan.r64.service.auth.LoginResponse;
import org.badhan.r64.service.auth.Register;
import org.badhan.r64.service.auth.RegisterWithExternalProvider;


public class RegisterActivity extends BaseActivity {
    private EditText telephoneField;
    private EditText emailField;
    private EditText passwordField;
    private Button registerBtn;
    private Button loginBtn;
    private String defaultRegisterBtnText;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.activity_register);

        bindControls();

    }

    private void bindControls() {
        telephoneField = findViewById(R.id.register_activity_telephoneField);
        emailField = findViewById(R.id.register_activity_emailField);
        passwordField = findViewById(R.id.register_activity_passwordField);
        registerBtn = findViewById(R.id.register_activity_registerBtn);
        loginBtn = findViewById(R.id.register_activity_loginBtn);
        progressBar = findViewById(R.id.register_activity_progressBar);

        registerBtn.setOnClickListener(new OnRegisterBtnClickHandler());
        loginBtn.setOnClickListener(new OnLoginBtnClickHandler());
        defaultRegisterBtnText = registerBtn.getText().toString();
    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        registerBtn.setEnabled(false);
        registerBtn.setText("");
    }

    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
        registerBtn.setEnabled(true);
        registerBtn.setText(defaultRegisterBtnText);
    }

    private class OnRegisterBtnClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            showProgressBar();

            bus.post(new Register.Request(
                    telephoneField.getText().toString(),
                    emailField.getText().toString(),
                    passwordField.getText().toString()
            ));
        }
    }


    private class OnLoginBtnClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Subscribe
    public void onRegisterResponse(Register.Response response){
        registerResponse(response);
    }


    @Subscribe
    public void onExternalRegisterResponse(RegisterWithExternalProvider.Response response){
        registerResponse(response);
    }


    private void registerResponse(LoginResponse response){

        if (response.succeed()){
            setResult(RESULT_OK);
            finish();
            return;
        }

        hideProgressBar();
        response.showErrorToast(this);
        telephoneField.setError(response.getPropertyError("telephone"));
        emailField.setError(response.getPropertyError("email"));
        passwordField.setError(response.getPropertyError("password"));
    }
}
