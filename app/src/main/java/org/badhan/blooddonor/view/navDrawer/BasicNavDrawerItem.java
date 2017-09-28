package org.badhan.blooddonor.view.navDrawer;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.badhan.blooddonor.R;


public class BasicNavDrawerItem extends NavDrawerItem implements View.OnClickListener {
    private String text;
    private String badge;
    private int iconDrawable;
    private int containerId;

    private ImageView iconView;
    private TextView textView;
    private TextView badgeTextView;
    private View view;
    private int defaultTextColor;


    public BasicNavDrawerItem(String text, int iconDrawable, String badge, int containerId){
        this.text = text;
        this.iconDrawable = iconDrawable;
        this.badge = badge;
        this.containerId = containerId;
    }

    @Override
    public void inflate(LayoutInflater inflater, ViewGroup navDrawerView) {
        ViewGroup container = navDrawerView.findViewById(this.containerId);
        if (container == null)
            throw new RuntimeException("Nav drawer item : "+ text + " could not be attached to ViewGroup. View not found");

        view = inflater.inflate(R.layout.nav_drawer_item, container);
        view.setOnClickListener(this);

        iconView =  view.findViewById(R.id.nav_drawer_item_icon);
        textView =  view.findViewById(R.id.nav_drawer_item_text);
        badgeTextView = view.findViewById(R.id.nav_drawer_item_badge);

        defaultTextColor = textView.getCurrentTextColor();

        iconView.setImageResource(iconDrawable);
        textView.setText(text);
        if (badge != null)
            badgeTextView.setText(badge);
        else
            badgeTextView.setVisibility(View.GONE);
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected){
            view.setBackgroundResource(R.drawable.nav_drawer_item_selected_background);
            int textColor = ContextCompat.getColor(navDrawer.activity, R.color.nav_drawer_item_selected_text_color);
            textView.setTextColor(textColor);
        }else {
            view.setBackground(null);
            textView.setTextColor(defaultTextColor);
        }
    }

    public void setText(String text){
        this.text = text;
        if (view != null)
            textView.setText(text);
    }

    public void setBadge(String badge){
        this.badge = badge;
        if (view != null){
            if (badge != null){
                badgeTextView.setText(badge);
                badgeTextView.setVisibility(View.VISIBLE);
            }
            else
                badgeTextView.setVisibility(View.GONE);
        }
    }

    public void setIcon(int iconDrawable){
        this.iconDrawable = iconDrawable;
        if (view != null)
            iconView.setImageResource(iconDrawable);
    }

    @Override
    public void onClick(View view) {
        navDrawer.setSelectedItem(this);
    }
}
