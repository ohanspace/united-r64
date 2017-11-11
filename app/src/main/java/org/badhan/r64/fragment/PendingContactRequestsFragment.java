package org.badhan.r64.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import org.badhan.r64.R;
import org.badhan.r64.adapter.ContactRequestsAdapter;
import org.badhan.r64.core.BaseActivity;
import org.badhan.r64.core.BaseFragment;
import org.badhan.r64.service.contact.GetContactRequests;


public class PendingContactRequestsFragment extends BaseFragment {
    private View progressBarFrame;
    private ContactRequestsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_contact_requests, container, false);
        progressBarFrame = view.findViewById(R.id.fragment_pending_contact_requests_list_progressFrame);
        adapter = new ContactRequestsAdapter((BaseActivity) getActivity());

        ListView listView = view.findViewById(R.id.fragment_pending_contact_requests_list);
        listView.setAdapter(adapter);

        bus.post(new GetContactRequests.Request(true));
        return view;
    }

    @Subscribe
    public void onGetContactRequests(GetContactRequests.Response contactRequests){
        contactRequests.showErrorToast(getActivity());

        progressBarFrame.animate()
                .alpha(0)
                .setDuration(250)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        progressBarFrame.setVisibility(View.GONE);
                    }
                })
                .start();

        adapter.clear();
        adapter.addAll(contactRequests.requests);
    }
}
