package org.badhan.blooddonor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.activity.contact.AddContactActivity;
import org.badhan.blooddonor.activity.contact.ContactDetailsActivity;
import org.badhan.blooddonor.adapter.ContactsAdapter;
import org.badhan.blooddonor.core.BaseActivity;
import org.badhan.blooddonor.core.BaseFragment;
import org.badhan.blooddonor.entity.UserDetails;
import org.badhan.blooddonor.service.contact.GetContacts;


public class ContactsFragment extends BaseFragment {
    private ContactsAdapter adapter;
    private View progressBarFrame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        adapter = new ContactsAdapter((BaseActivity) getActivity());
        progressBarFrame = view.findViewById(R.id.fragment_contacts_list_progressBarFrame);

        ListView listView = view.findViewById(R.id.fragment_contacts_list);
        View emptyView = view.findViewById(R.id.fragment_contacts_emptyList);
        listView.setEmptyView(emptyView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnContactsItemClickHandler());

        bus.post(new GetContacts.Request(false));

        return view;
    }

    @Subscribe
    public void onGetContactsResponse(final GetContacts.Response response){
        scheduler.invokeOnResume(GetContacts.Response.class ,new Runnable() {
            @Override
            public void run() {
                response.showErrorToast(getActivity());

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
                adapter.addAll(response.contacts);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contacts_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.contacts_fragment_menu_addContact){
            Intent intent = new Intent(getActivity(), AddContactActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class OnContactsItemClickHandler implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            UserDetails userDetails = adapter.getItem(position);

            Intent intent = new Intent(getActivity(), ContactDetailsActivity.class);
            intent.putExtra(ContactDetailsActivity.EXTRA_CONTACT_DETAILS, userDetails);
            startActivity(intent);
        }
    }
}
