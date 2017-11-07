package org.badhan.blooddonor.core;

import android.app.Fragment;
import android.os.Bundle;

import com.squareup.otto.Bus;


public abstract class BaseFragment extends Fragment {
    protected MyApplication application;
    protected Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (MyApplication) getActivity().getApplication();
        bus = application.getBus();
        bus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
