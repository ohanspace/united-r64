package org.badhan.blooddonor.dialog;

import android.app.DialogFragment;
import android.os.Bundle;

import org.badhan.blooddonor.core.MyApplication;


public abstract class BaseDialogFragment extends DialogFragment {
    protected MyApplication application;

    @Override
    public void onCreate(Bundle savedState){
        super.onCreate(savedState);
        application = (MyApplication) getActivity().getApplication();
    }
}
