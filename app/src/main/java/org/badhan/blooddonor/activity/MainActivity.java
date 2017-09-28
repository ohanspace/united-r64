package org.badhan.blooddonor.activity;

import android.os.Bundle;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.core.BaseAuthActivity;
import org.badhan.blooddonor.view.MainNavDrawer;

public class MainActivity extends BaseAuthActivity {

    @Override
    protected void onAppCreate(Bundle savedState) {
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("inbox");

        setNavDrawer(new MainNavDrawer(this));
    }

}
