package org.badhan.r64.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import org.badhan.r64.R;
import org.badhan.r64.entity.Trainer;


public class TrainerViewHolder extends RecyclerView.ViewHolder {
    private CardView cardView;
    private ImageView avatar;
    private TextView displayName;
    private TextView designation;
    private TextView trainingPost;
    private ImageButton callBtn;
    private ImageButton emailBtn;


    public TrainerViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView;
        avatar = itemView.findViewById(R.id.trainer_list_item_avatar);
        displayName = itemView.findViewById(R.id.trainer_list_item_displayName);
        designation = itemView.findViewById(R.id.trainer_list_item_designation);
        trainingPost = itemView.findViewById(R.id.trainer_list_item_trainingPost);
        callBtn = itemView.findViewById(R.id.trainer_list_item_callBtn);
        emailBtn = itemView.findViewById(R.id.trainer_list_item_emailBtn);
    }


    public void populate(final Context context, final Trainer trainer){
        itemView.setTag(trainer);

        avatar.setImageResource(R.drawable.ic_action_profile);
        displayName.setText(trainer.getDisplayName());
        designation.setText(trainer.getDesignation());
        trainingPost.setText(trainer.getTrainingPost());

        trainer.getAvatarRef().getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context)
                        .load(uri.toString())
                        .into(avatar);
            }
        });


        callBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String telephone = trainer.getTelephone();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+ telephone));
                context.startActivity(callIntent);
            }
        });

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = trainer.getEmail();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts("mailto",email,null));
                context.startActivity(emailIntent);
            }
        });
    }


}
