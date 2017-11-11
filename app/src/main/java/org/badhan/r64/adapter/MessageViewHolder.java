package org.badhan.r64.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.badhan.r64.R;
import org.badhan.r64.entity.Message;


public class MessageViewHolder extends RecyclerView.ViewHolder {
    private CardView cardView;
    private ImageView avatar;
    private TextView displayName;
    private TextView createdAt;
    private TextView sentRecievedText;


    public MessageViewHolder(View view) {
        super(view);
        cardView = (CardView) view;
        avatar = view.findViewById(R.id.message_list_item_avatar);
        displayName = view.findViewById(R.id.message_list_item_displayName);
        createdAt = view.findViewById(R.id.message_list_item_createdAt);
        sentRecievedText = view.findViewById(R.id.message_list_item_sentReceivedText);
    }

    public void populate(Context context, Message message){
        itemView.setTag(message);

        Picasso.with(context)
                .load(message.getOtherUser().getAvatarUrl())
                .into(avatar);

        String createdAtText = DateUtils.formatDateTime(
                context,
                message.getCreatedAt().getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME
        );

        createdAt.setText(createdAtText);
        displayName.setText(message.getOtherUser().getDisplayName());
        sentRecievedText.setText(message.isFromMe()? "sent" : "received");

        int colorResId;
        if (message.isSelected()){
            colorResId = R.color.list_item_background_selected;
            cardView.setCardElevation(5);
        }
        else if (message.isRead()){
            colorResId = R.color.list_item_background_read;
            cardView.setCardElevation(3);
        }
        else {
            colorResId = R.color.list_item_background_unread;
            cardView.setCardElevation(3);
        }
    }
}
