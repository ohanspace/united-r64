package org.badhan.r64.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.badhan.r64.R;
import org.badhan.r64.activity.cadre.CadresActivity;
import org.badhan.r64.activity.contact.ContactsActivity;
import org.badhan.r64.activity.InboxActivity;
import org.badhan.r64.activity.MainActivity;
import org.badhan.r64.activity.ProfileActivity;
import org.badhan.r64.activity.message.SentMessagesActivity;
import org.badhan.r64.activity.trainer.TrainersActivity;
import org.badhan.r64.core.BaseActivity;
import org.badhan.r64.entity.User;
import org.badhan.r64.service.profile.UserDetailsUpdatedEvent;
import org.badhan.r64.view.navDrawer.ActivityNavDrawerItem;
import org.badhan.r64.view.navDrawer.BasicNavDrawerItem;
import org.badhan.r64.view.navDrawer.NavDrawer;


public class MainNavDrawer extends NavDrawer {
    private final TextView displayNameView;
    private final ImageView avatarImageView;


    public MainNavDrawer(final BaseActivity activity) {
        super(activity);


        addItem(new ActivityNavDrawerItem(MainActivity.class, "Home", R.drawable.ic_action_home, null, R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(ProfileActivity.class, "Profile", R.drawable.ic_action_profile, null, R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(InboxActivity.class, "Inbox", R.drawable.ic_action_email, null, R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(SentMessagesActivity.class, "Sent Messages", R.drawable.ic_action_sent_message, null, R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(ContactsActivity.class, "Contacts", R.drawable.ic_action_contacts, null, R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(CadresActivity.class, "Cadres", R.drawable.ic_action_contacts, null, R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(TrainersActivity.class,
                "CMT", R.drawable.ic_action_contacts,
                null, R.id.include_main_nav_drawer_topItems));

        addItem(new BasicNavDrawerItem("logout", R.drawable.ic_action_exit, null, R.id.include_main_nav_drawer_bottomItems){
            @Override
            public void onClick(View view){
                activity.getMyApplication().getAuth().logout();
            }
        });

        displayNameView = navDrawerView.findViewById(R.id.include_main_nav_drawer_displayName);
        avatarImageView = navDrawerView.findViewById(R.id.include_main_nav_drawer_avatar);

        User loggedInUser = activity.getMyApplication().getAuth().getUser();
        displayNameView.setText(loggedInUser.getDisplayName());
        //TODO set avatarImageView src to user
    }


    @Subscribe
    public void onUserDetailsUpdated(UserDetailsUpdatedEvent event){
        //todo update avatar img
        User user = event.user;
        displayNameView.setText(user.getDisplayName());
    }
}
