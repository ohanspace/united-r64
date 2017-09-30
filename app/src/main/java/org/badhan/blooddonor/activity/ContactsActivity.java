package org.badhan.blooddonor.activity;

import android.os.Bundle;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.core.BaseAuthActivity;
import org.badhan.blooddonor.view.MainNavDrawer;


public class ContactsActivity extends BaseAuthActivity {
    @Override
    protected void onAppCreate(Bundle savedState) {
        setContentView(R.layout.activity_contacts);
        getSupportActionBar().setTitle("Contacts");

        setNavDrawer(new MainNavDrawer(this));
    }
}
