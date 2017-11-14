package org.badhan.r64.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.badhan.r64.R;
import org.badhan.r64.entity.Cadre;

public class CadreViewHolder extends RecyclerView.ViewHolder {
    private CardView cardView;
    private ImageView avatar;
    private TextView displayName;
    private TextView cadreBatchType;
    private TextView postingAddress;

    public CadreViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView;
        avatar = itemView.findViewById(R.id.cadre_list_item_avatar);
        displayName = itemView.findViewById(R.id.cadre_list_item_displayName);
        cadreBatchType = itemView.findViewById(R.id.cadre_list_item_cadreBatchType);
        postingAddress = itemView.findViewById(R.id.cadre_list_item_postingAddress);
    }


    public void populate(Context context, Cadre cadre){
        itemView.setTag(cadre);

        Picasso.with(context)
                .load(cadre.getAvatarUrl())
                .placeholder(R.drawable.ic_action_profile)
                .error(R.drawable.ic_action_profile)
                .into(avatar);

        displayName.setText(cadre.getDisplayName());
        cadreBatchType.setText(cadre.getCadreBatchType());
        postingAddress.setText(cadre.getPostingAddress());

    }
}
