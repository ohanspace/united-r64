package org.badhan.blooddonor.core;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity{
    public MyApplication application;

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        application = (MyApplication) getApplication();
    }
}
