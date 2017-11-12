package org.badhan.r64.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.badhan.r64.R;
import org.badhan.r64.core.BaseActivity;
import org.badhan.r64.entity.Trainer;

import java.util.ArrayList;

public class TrainersAdapter extends RecyclerView.Adapter<TrainerViewHolder> {
    private final LayoutInflater inflater;
    private final BaseActivity activity;
    private final ArrayList<Trainer> trainers;

    public TrainersAdapter(BaseActivity activity) {
        this.activity = activity;
        inflater = activity.getLayoutInflater();
        trainers = new ArrayList<>();
    }

    public ArrayList getTrainers(){
        return trainers;
    }



    @Override
    public TrainerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_trainer, parent, false);
        return new TrainerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrainerViewHolder holder, int position) {
        holder.populate(activity, trainers.get(position));
    }

    @Override
    public int getItemCount() {
        return trainers.size();
    }
}
