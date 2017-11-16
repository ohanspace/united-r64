package org.badhan.r64.view;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import org.badhan.r64.R;
import org.badhan.r64.activity.cadre.CadresActivity;
import org.badhan.r64.activity.contact.ContactsActivity;
import org.badhan.r64.activity.InboxActivity;
import org.badhan.r64.activity.MainActivity;
import org.badhan.r64.activity.ProfileActivity;
import org.badhan.r64.activity.message.SentMessagesActivity;
import org.badhan.r64.activity.trainer.TrainersActivity;
import org.badhan.r64.core.BaseActivity;
import org.badhan.r64.entity.Cadre;
import org.badhan.r64.entity.User;
import org.badhan.r64.service.profile.CadreDetailsUpdatedEvent;
import org.badhan.r64.service.profile.GotAvatarDownloadLinkEvent;
import org.badhan.r64.service.profile.UserDetailsUpdatedEvent;
import org.badhan.r64.view.navDrawer.ActivityNavDrawerItem;
import org.badhan.r64.view.navDrawer.BasicNavDrawerItem;
import org.badhan.r64.view.navDrawer.NavDrawer;


public class MainNavDrawer extends NavDrawer {
    private final TextView displayNameView;
    private final ImageView avatarImageView;
    private final TextView telephoneView;


    public MainNavDrawer(final BaseActivity activity) {
        super(activity);


        addItem(new ActivityNavDrawerItem(MainActivity.class,
                "Home", R.drawable.ic_action_home,
                null, R.id.include_main_nav_drawer_topItems));

        if (!activity.application.getAuth().getUser().isGuest()){
            addItem(new ActivityNavDrawerItem(ProfileActivity.class,
                    "My Profile", R.drawable.ic_action_profile,
                    null, R.id.include_main_nav_drawer_topItems));
         }
        addItem(new ActivityNavDrawerItem(CadresActivity.class,
                "All Cadres", R.drawable.ic_action_local_library,
                "94", R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(TrainersActivity.class,
                "Management Team", R.drawable.ic_action_contacts,
                "7", R.id.include_main_nav_drawer_topItems));

        addItem(new BasicNavDrawerItem("logout", R.drawable.ic_action_exit, null, R.id.include_main_nav_drawer_bottomItems){
            @Override
            public void onClick(View view){
                activity.getMyApplication().getAuth().logout();
            }
        });

        displayNameView = navDrawerView.findViewById(R.id.include_main_nav_drawer_displayName);
        avatarImageView = navDrawerView.findViewById(R.id.include_main_nav_drawer_avatar);
        telephoneView = navDrawerView.findViewById(R.id.include_main_nav_drawer_telephone);

        User loggedInUser = activity.getMyApplication().getAuth().getUser();
        refreshDrawer(loggedInUser);

    }

    private void refreshDrawer(User user){
        displayNameView.setText(user.getDisplayName());
        telephoneView.setText(user.getTelephone());

        user.getAvatarStorageRef().getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("got download uri",uri.toString());
                Picasso.with(activity)
                        .load(uri.toString())
                        .placeholder(R.drawable.ic_action_profile)
                        .error(R.drawable.ic_action_profile)
                        .into(avatarImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                avatarImageView.setImageResource(R.drawable.ic_action_profile);
            }
        });
    }


    @Subscribe
    public void onUserDetailsUpdated(UserDetailsUpdatedEvent event){
        //todo update avatar img
        User user = event.user;
        refreshDrawer(user);
    }

    @Subscribe
    public void onGotAvatarDownloadLink(GotAvatarDownloadLinkEvent event){

        Picasso.with(activity)
                .load(event.downloadUrl)
                .placeholder(R.drawable.ic_action_profile)
                .error(R.drawable.ic_action_profile)
                .into(avatarImageView);
    }

    @Subscribe
    public void onCadreDetailsUpdated(CadreDetailsUpdatedEvent event){
        Cadre cadre = event.cadre;
        displayNameView.setText(cadre.getDisplayName());
    }
}
