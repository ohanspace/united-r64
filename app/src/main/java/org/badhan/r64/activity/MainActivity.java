package org.badhan.r64.activity;

import android.os.Bundle;

import org.badhan.r64.R;
import org.badhan.r64.core.BaseAuthActivity;
import org.badhan.r64.view.MainNavDrawer;

public class MainActivity extends BaseAuthActivity {

    @Override
    protected void onAppCreate(Bundle savedState) {
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("inbox");

        setNavDrawer(new MainNavDrawer(this));
    }

}
