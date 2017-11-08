package org.badhan.blooddonor.core;

import android.app.Fragment;
import android.os.Bundle;

import com.squareup.otto.Bus;


public abstract class BaseFragment extends Fragment {
    protected MyApplication application;
    protected Bus bus;
    protected ActionScheduler scheduler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (MyApplication) getActivity().getApplication();
        bus = application.getBus();
        scheduler = new ActionScheduler(application);
        bus.register(this);
    }

    public ActionScheduler getScheduler(){
        return scheduler;
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
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
