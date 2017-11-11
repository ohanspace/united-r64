package org.badhan.r64.view.navDrawer;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.badhan.r64.R;
import org.badhan.r64.core.BaseActivity;

import java.util.ArrayList;

public class NavDrawer {
    private ArrayList<NavDrawerItem> items;
    private NavDrawerItem selectedItem;

    protected BaseActivity activity;
    protected DrawerLayout drawerLayout;
    protected ViewGroup navDrawerView;

    public NavDrawer(BaseActivity activity){
        this.activity = activity;
        items = new ArrayList<>();
        drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        navDrawerView = (ViewGroup) activity.findViewById(R.id.nav_drawer);

        if (drawerLayout == null || navDrawerView == null)
            throw new RuntimeException("you must have views with ids drawer_layout and nav_drawer to use the NavDrawer class");

        Toolbar toolbar = activity.getToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_action_nav_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOpen(!isOpen());
            }
        });

        activity.getMyApplication().getBus().register(this); //register to bus
    }

    public void addItem(NavDrawerItem item){
        items.add(item);
        item.navDrawer = this;
    }

    public boolean isOpen(){
        return drawerLayout.isDrawerOpen(Gravity.START);
    }

    public void setOpen(boolean open){
        if (open)
            drawerLayout.openDrawer(Gravity.START);
        else
            drawerLayout.closeDrawer(Gravity.START);
    }

    public void setSelectedItem(NavDrawerItem item){
        if (selectedItem != null)
            selectedItem.setSelected(false);
        else{
            selectedItem = item;
            selectedItem.setSelected(true);
        }
    }

    public void create(){
        LayoutInflater inflater = activity.getLayoutInflater();
        for (NavDrawerItem item : items){
            item.inflate(inflater, navDrawerView);
        }
    }


    public void destroy() {
        activity.getMyApplication().getBus().unregister(this);
    }
}
