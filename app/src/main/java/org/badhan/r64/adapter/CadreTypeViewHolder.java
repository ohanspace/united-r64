package org.badhan.r64.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.badhan.r64.R;
import org.badhan.r64.entity.CadreType;


public class CadreTypeViewHolder extends RecyclerView.ViewHolder {
    private CardView cardView;
    private TextView displayName;

    public CadreTypeViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView;
        displayName = itemView.findViewById(R.id.cadre_type_item_displayName);
    }

    public void populate(Context context, CadreType cadreType){
        itemView.setTag(cadreType);

        displayName.setText(cadreType.getDisplayName());
    }
}
