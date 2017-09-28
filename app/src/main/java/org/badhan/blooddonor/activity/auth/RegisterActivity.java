package org.badhan.blooddonor.activity.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.core.BaseActivity;


public class RegisterActivity extends BaseActivity {
    private EditText nameField;
    private EditText telephoneField;
    private EditText passwordField;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.activity_register);

        bindControls();
    }

    private void bindControls() {
        nameField = (EditText) findViewById(R.id.register_activity_nameField);
        telephoneField = (EditText) findViewById(R.id.register_activity_telephoneField);
        passwordField = (EditText) findViewById(R.id.register_activity_passwordField);
        registerBtn = (Button) findViewById(R.id.register_activity_registerBtn);

        registerBtn.setOnClickListener(new OnRegisterBtnClickHandler());
    }

    private class OnRegisterBtnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            application.getAuth().getUser().setLoggedIn(true);
            setResult(RESULT_OK);
            finish();
        }
    }
}
