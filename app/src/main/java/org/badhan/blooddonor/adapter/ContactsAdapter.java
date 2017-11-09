package org.badhan.blooddonor.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.core.BaseActivity;
import org.badhan.blooddonor.entity.UserDetails;


public class ContactsAdapter extends ArrayAdapter<UserDetails> {
    private LayoutInflater inflater;

    public ContactsAdapter(BaseActivity activity){
        super(activity,0);
        inflater = activity.getLayoutInflater();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserDetails userDetails =  getItem(position);
        ViewHolder viewHolder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.contacts_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.displayName.setText(userDetails.getDisplayName());
        Picasso.with(getContext())
                .load(userDetails.getAvatarUrl())
                .error(R.drawable.ic_action_contacts)
                .into(viewHolder.avatar);

        return convertView;
    }

    private class ViewHolder{
        public TextView displayName;
        public ImageView avatar;

        public ViewHolder(View view){
            displayName = view.findViewById(R.id.contacts_list_item_displayName);
            avatar = view.findViewById(R.id.contacts_list_item_avatar);

        }
    }
}
