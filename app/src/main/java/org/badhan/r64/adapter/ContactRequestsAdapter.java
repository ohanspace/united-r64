package org.badhan.r64.adapter;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.badhan.r64.R;
import org.badhan.r64.core.BaseActivity;
import org.badhan.r64.entity.ContactRequest;
import org.badhan.r64.entity.UserDetails;


public class ContactRequestsAdapter extends ArrayAdapter<ContactRequest> {
    private LayoutInflater inflater;
    public ContactRequestsAdapter(BaseActivity activity){
        super(activity, 0); //0 for avoid default layout
        inflater = activity.getLayoutInflater();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactRequest contactRequest = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.contact_requests_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        UserDetails userDetails = contactRequest.getUserDetails();
        viewHolder.displayName.setText(userDetails.getDisplayName());

        Picasso.with(getContext())
                .load(userDetails.getAvatarUrl())
                .placeholder(R.drawable.ic_action_contacts)
                .error(R.drawable.ic_action_home)
                .into(viewHolder.avatar);

        String createdAt = DateUtils.formatDateTime(getContext(),
                contactRequest.getCreatedAt().getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);

        if (contactRequest.isFromMe()){
            viewHolder.createdAt.setText("sent at " + createdAt);
        }else {
            viewHolder.createdAt.setText("received at " + createdAt);
        }

        return convertView;

    }

    public class ViewHolder{
        public TextView displayName;
        public TextView createdAt;
        public ImageView avatar;

        public ViewHolder(View view){
            displayName = view.findViewById(R.id.contact_requests_list_item_displayName);
            createdAt =   view.findViewById(R.id.contact_requests_list_item_createdAt);
            avatar =   view.findViewById(R.id.contact_requests_list_item_avatar);
        }
    }
}
