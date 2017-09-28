package org.badhan.blooddonor.activity.auth.handler;

import android.view.View;

import org.badhan.blooddonor.activity.auth.LoginActivity;

public class OnLoginBtnClickHandler implements View.OnClickListener {
    private LoginActivity loginActivity;

    public OnLoginBtnClickHandler(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    public void onClick(View view) {
        loginActivity.application.getAuth().getUser().setLoggedIn(true);
        loginActivity.onSuccessLogin();
    }
}
