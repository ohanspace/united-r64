package org.badhan.blooddonor.activity.auth;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.core.BaseActivity;

public class ExternalLoginActivity extends BaseActivity {
    public static final String EXTRA_PROVIDER_NAME = "EXTRA_PROVIDER_NAME";
    private WebView webView;
    private Button testLoginBtn;
    private String providerName;

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.activity_external_login);

        webView = (WebView) findViewById(R.id.external_login_activity_webView);
        testLoginBtn = (Button) findViewById(R.id.external_login_activity_testLoginBtn);

        providerName = getIntent().getStringExtra(EXTRA_PROVIDER_NAME);

        testLoginBtn.setText("Login with " + providerName);
        testLoginBtn.setOnClickListener(new OnTestLoginBtnClickHandler());
    }

    private class OnTestLoginBtnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            application.getAuth().getUser().setLoggedIn(true);
            setResult(RESULT_OK);
            finish();
        }
    }
}
