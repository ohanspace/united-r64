package org.badhan.blooddonor.core;

import android.app.DialogFragment;
import android.os.Bundle;

import com.squareup.otto.Bus;



public abstract class BaseDialogFragment extends DialogFragment {
    protected MyApplication application;
    protected Bus bus;

    @Override
    public void onCreate(Bundle savedState){
        super.onCreate(savedState);
        application = (MyApplication) getActivity().getApplication();
        bus = application.getBus();
        bus.register(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        bus.unregister(this);
    }
}
