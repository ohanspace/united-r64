package org.badhan.r64.view.navDrawer;

import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class NavDrawerItem {
    protected NavDrawer navDrawer;

    public abstract void inflate(LayoutInflater inflater, ViewGroup container);
    public abstract void setSelected(boolean selected);
}
