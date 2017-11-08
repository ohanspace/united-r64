package org.badhan.blooddonor.activity.contact;

import android.os.Bundle;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.core.BaseAuthActivity;


public class ContactDetailsActivity extends BaseAuthActivity {
    public static final String  EXTRA_CONTACT_DETAILS = "EXTRA_CONTACT_DETAILS";
    @Override
    protected void onAppCreate(Bundle savedState) {
        setContentView(R.layout.activity_contact_details);
    }
}
