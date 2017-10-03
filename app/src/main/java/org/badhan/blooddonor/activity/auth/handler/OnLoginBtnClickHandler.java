package org.badhan.blooddonor.activity.auth.handler;

import android.view.View;
import android.widget.FrameLayout;

import org.badhan.blooddonor.activity.auth.LoginActivity;
import org.badhan.blooddonor.service.auth.LoginWithUsername;

public class OnLoginBtnClickHandler implements View.OnClickListener {
    private LoginActivity loginActivity;

    public OnLoginBtnClickHandler(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    public void onClick(View view) {
        loginActivity.progressBarFrame.setVisibility(FrameLayout.VISIBLE);

        this.loginActivity.bus.post(new LoginWithUsername.Request(
                loginActivity.usernameField.getText().toString(),
                loginActivity.passwordField.getText().toString()));

    }
}
