package org.badhan.r64.core;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;

import com.squareup.otto.Bus;

import org.badhan.r64.R;
import org.badhan.r64.view.navDrawer.NavDrawer;

public abstract class BaseActivity extends AppCompatActivity{
    protected boolean isRegisteredToBus;
    public MyApplication application;
    protected Toolbar toolbar;
    protected NavDrawer navDrawer;
    protected boolean isTablet;
    public Bus bus;
    protected ActionScheduler scheduler;

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        application = (MyApplication) getApplication();
        bus = application.getBus();
        scheduler = new ActionScheduler(application);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        isTablet = (metrics.widthPixels / metrics.density) >= 600;

        registerToBus();
    }

    private void registerToBus() {
        if (!isRegisteredToBus){
            bus.register(this);
            isRegisteredToBus = true;
        }
    }


    private void unregisterFromBus() {
        if (isRegisteredToBus){
            bus.unregister(this);
            isRegisteredToBus = false;
        }
    }

    public ActionScheduler getScheduler(){
        return scheduler;
    }

    @Override
    protected void onResume() {
        super.onResume();
        scheduler.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scheduler.onPause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterFromBus();

        if (navDrawer != null)
            this.navDrawer.destroy();
    }

    @Override
    public void finish() {
        super.finish();
        unregisterFromBus();
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
