package org.badhan.blooddonor.core;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.view.navDrawer.NavDrawer;

public abstract class BaseActivity extends AppCompatActivity{
    public MyApplication application;
    protected Toolbar toolbar;
    protected NavDrawer navDrawer;
    protected boolean isTablet;

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        application = (MyApplication) getApplication();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        isTablet = (metrics.widthPixels / metrics.density) >= 600;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResId){
        super.setContentView(layoutResId);

        toolbar = (Toolbar) findViewById(R.id.include_toolbar_main);
        if (toolbar != null)
            setSupportActionBar(toolbar);
    }

    protected void setNavDrawer(NavDrawer navDrawer){
        this.navDrawer = navDrawer;
        this.navDrawer.create();
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    public MyApplication getMyApplication(){
        return application;
    }
}
