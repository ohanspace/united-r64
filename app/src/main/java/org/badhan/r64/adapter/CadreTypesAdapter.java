package org.badhan.r64.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.badhan.r64.R;
import org.badhan.r64.core.BaseActivity;
import org.badhan.r64.entity.CadreType;

import java.util.ArrayList;


public class CadreTypesAdapter extends RecyclerView.Adapter<CadreTypeViewHolder> implements View.OnClickListener {
    private LayoutInflater inflater;
    private BaseActivity activity;
    private ArrayList<CadreType> cadreTypes;
    private OnCadreTypeClickListener listener;

    public CadreTypesAdapter(BaseActivity activity, OnCadreTypeClickListener listener) {
        this.activity = activity;
        this.listener = listener;

        cadreTypes = new ArrayList<>();
        inflater = activity.getLayoutInflater();
    }

    public ArrayList<CadreType> getCadreTypes(){
        return cadreTypes;
    }



    @Override
    public CadreTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_cadre_type, parent, false);
        view.setOnClickListener(this);
        return new CadreTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CadreTypeViewHolder holder, int position) {
        holder.populate(activity, cadreTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return cadreTypes.size();
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() instanceof CadreType){
            CadreType cadreType =(CadreType) view.getTag();
            listener.onCadreTypeClick(cadreType);
        }
    }


    public interface OnCadreTypeClickListener{
        void onCadreTypeClick(CadreType cadreType);
    }
}
