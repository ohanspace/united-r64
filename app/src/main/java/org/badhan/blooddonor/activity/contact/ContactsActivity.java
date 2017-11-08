package org.badhan.blooddonor.activity.contact;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.core.BaseAuthActivity;
import org.badhan.blooddonor.fragment.ContactsFragment;
import org.badhan.blooddonor.fragment.PendingContactRequestsFragment;
import org.badhan.blooddonor.view.MainNavDrawer;


public class ContactsActivity extends BaseAuthActivity {
    private ObjectAnimator animation;
    private ArrayAdapter<ContactsSpinnerItem> adapter;


    @Override
    protected void onAppCreate(Bundle savedState) {
        setContentView(R.layout.activity_contacts);
        setNavDrawer(new MainNavDrawer(this));

        adapter = new ArrayAdapter<ContactsSpinnerItem>(this, R.layout.toolbar_spinner_list_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        adapter.add(new ContactsSpinnerItem("Contacts",
                Color.parseColor("#00BCD4"), ContactsFragment.class));
        adapter.add(new ContactsSpinnerItem("Pending Contact Requests",
                getResources().getColor(R.color.pending_contact_requests), PendingContactRequestsFragment.class));


        Spinner spinner = findViewById(R.id.contacts_activity_toolbar_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerItemSelectedHandler());

        getSupportActionBar().setTitle(null);

    }


    private class SpinnerItemSelectedHandler implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            ContactsSpinnerItem item = adapter.getItem(position);
            if (item == null)
                return;

            if (animation != null)
                animation.end();

            int currentColor = ((ColorDrawable) toolbar.getBackground()).getColor();
            animation = ObjectAnimator.ofObject(toolbar, "backgroundColor",
                    new ArgbEvaluator(), currentColor, item.getColor())
                    .setDuration(250);
            animation.start();

            Fragment fragment;
            try{
                fragment = (Fragment) item.getFragment().newInstance();
            }catch (Exception e){
                Log.e("ContactsActivity","Could not instantiate fragment " + item.getFragment().getName(), e );
                return;
            }

            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.contacts_activity_fragment_container, fragment)
                    .commit();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }


    private class ContactsSpinnerItem{
        private final String title;
        private final int color;

        private Class fragment;

        public ContactsSpinnerItem(String title, int color, Class fragment) {
            this.title = title;
            this.color = color;
            this.fragment = fragment;
        }

        public String getTitle() {
            return title;
        }

        public int getColor() {
            return color;
        }

        public Class getFragment() {
            return fragment;
        }
        @Override
        public String toString() {
            return title;
        }


    }
}
