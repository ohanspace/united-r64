package org.badhan.r64.core;

import android.app.DialogFragment;
import android.os.Bundle;

import com.squareup.otto.Bus;



public abstract class BaseDialogFragment extends DialogFragment {
    protected MyApplication application;
    protected Bus bus;
    protected ActionScheduler scheduler;

    @Override
    public void onCreate(Bundle savedState){
        super.onCreate(savedState);
        application = (MyApplication) getActivity().getApplication();
        bus = application.getBus();
        scheduler = new ActionScheduler(application);

        bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        scheduler.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        scheduler.onResume();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        bus.unregister(this);
    }
}
