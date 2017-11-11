package org.badhan.r64.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.badhan.r64.R;
import org.badhan.r64.core.BaseActivity;
import org.badhan.r64.entity.Cadre;

import java.util.ArrayList;

public class CadresAdapter extends RecyclerView.Adapter<CadreViewHolder> implements View.OnClickListener {

    private final LayoutInflater inflater;
    private final BaseActivity activity;
    private final OnCadreClickListener listener;
    private final ArrayList<Cadre> cadres;

    public CadresAdapter(BaseActivity activity, OnCadreClickListener listener){
        this.activity = activity;
        this.listener = listener;

        cadres = new ArrayList<>();
        inflater = activity.getLayoutInflater();
    }


    public ArrayList getCadres(){
        return cadres;
    }


    @Override
    public CadreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_cadre, parent, false);
        view.setOnClickListener(this);
        return new CadreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CadreViewHolder holder, int position) {
        holder.populate(activity, cadres.get(position));
    }

    @Override
    public int getItemCount() {
        return cadres.size();
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() instanceof Cadre){
            Cadre cadre = (Cadre) view.getTag();
            listener.onCadreClick(cadre);
        }
    }

    public interface OnCadreClickListener{
        void onCadreClick(Cadre cadre);
    }
}
