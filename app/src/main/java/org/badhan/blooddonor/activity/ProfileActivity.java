package org.badhan.blooddonor.activity;

import android.os.Bundle;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.core.BaseAuthActivity;
import org.badhan.blooddonor.view.MainNavDrawer;


public class ProfileActivity extends BaseAuthActivity {
    @Override
    protected void onAppCreate(Bundle savedState) {
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");

        setNavDrawer(new MainNavDrawer(this));
    }
}
