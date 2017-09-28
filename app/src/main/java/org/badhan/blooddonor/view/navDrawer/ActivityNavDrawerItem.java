package org.badhan.blooddonor.view.navDrawer;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ActivityNavDrawerItem extends BasicNavDrawerItem {
    private final Class targetActivity;

    public ActivityNavDrawerItem(Class targetActivity, String text, int iconDrawable, String badge, int containerId) {
        super(text, iconDrawable, badge, containerId);
        this.targetActivity = targetActivity;
    }

    @Override
    public void inflate(LayoutInflater inflater, ViewGroup navDrawerView){
        super.inflate(inflater, navDrawerView);

        if (navDrawer.activity.getClass() == targetActivity){
            navDrawer.setSelectedItem(this);
        }
    }

    @Override
    public void onClick(View view){
        navDrawer.setOpen(false);

        if (navDrawer.activity.getClass() == targetActivity)
            return;

        super.onClick(view);

        //TODO:animation

        Intent intent = new Intent(navDrawer.activity, targetActivity);
        navDrawer.activity.startActivity(intent);
        navDrawer.activity.finish();
    }
}
