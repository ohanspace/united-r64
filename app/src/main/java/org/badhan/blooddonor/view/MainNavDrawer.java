package org.badhan.blooddonor.view;

import android.view.View;
import android.widget.Toast;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.activity.MainActivity;
import org.badhan.blooddonor.core.BaseActivity;
import org.badhan.blooddonor.view.navDrawer.ActivityNavDrawerItem;
import org.badhan.blooddonor.view.navDrawer.BasicNavDrawerItem;
import org.badhan.blooddonor.view.navDrawer.NavDrawer;


public class MainNavDrawer extends NavDrawer {
    public MainNavDrawer(final BaseActivity activity) {
        super(activity);


        addItem(new ActivityNavDrawerItem(MainActivity.class, "Item 1", R.drawable.ic_action_nav_menu, null, R.id.include_main_nav_drawer_topItems));

        addItem(new BasicNavDrawerItem("logout", R.drawable.ic_action_nav_menu, null, R.id.include_main_nav_drawer_bottomItems){
            @Override
            public void onClick(View view){
                Toast.makeText(activity,"Logged out!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
