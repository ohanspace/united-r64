package org.badhan.r64.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


    public void populate(final Context context, Cadre cadre){
        itemView.setTag(cadre);

        displayName.setText(cadre.getDisplayName());
        cadreBatchType.setText(cadre.getCadreBatchType());
        postingAddress.setText(cadre.getPostingAddress());

        avatar.setImageResource(R.drawable.ic_action_profile);

        cadre.getAvatarStorageRef().getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(context)
                                .load(uri.toString())
                                .placeholder(R.drawable.ic_action_profile)
                                .error(R.drawable.ic_action_profile)
                                .into(avatar);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        avatar.setImageResource(R.drawable.ic_action_profile);
                    }
                });



    }
}
