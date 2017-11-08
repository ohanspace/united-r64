package org.badhan.blooddonor.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.core.BaseFragment;


public class PendingContactRequestsFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_contact_requests, container, false);
        return view;
    }
}
