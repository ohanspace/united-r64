package org.badhan.r64.activity.trainer;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.otto.Subscribe;

import org.badhan.r64.R;
import org.badhan.r64.adapter.TrainersAdapter;
import org.badhan.r64.core.BaseAuthActivity;
import org.badhan.r64.entity.Cadre;
import org.badhan.r64.entity.Trainer;
import org.badhan.r64.service.cadre.GetCadres;
import org.badhan.r64.service.trainer.GetTrainers;
import org.badhan.r64.view.MainNavDrawer;

import java.util.ArrayList;

public class TrainersActivity extends BaseAuthActivity {
    private TrainersAdapter adapter;
    private ArrayList<Trainer> trainers;
    private View progressBarFrame;

    @Override
    protected void onAppCreate(Bundle savedState) {
        setContentView(R.layout.activity_trainers);
        setNavDrawer(new MainNavDrawer(this));
        getSupportActionBar().setTitle("Course Management Team");

        adapter = new TrainersAdapter(this);
        trainers = adapter.getTrainers();

        progressBarFrame = findViewById(R.id.trainers_activity_progressBarFrame);
        RecyclerView recyclerView = findViewById(R.id.trainers_activity_trainer_list);
        recyclerView.setAdapter(adapter);

        if (isTablet)
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        else
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showProgressBar();
        bus.post(new GetTrainers.Request());
    }

    private void showProgressBar(){
        progressBarFrame.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        progressBarFrame.setVisibility(View.GONE);
    }

    @Subscribe
    public void onGetTrainers(GetTrainers.Response response){
        hideProgressBar();
        response.showErrorToast(this);

        int oldTrainersSize = trainers.size();
        trainers.clear();
        adapter.notifyItemRangeRemoved(0, oldTrainersSize);

        trainers.addAll(response.trainers);
        adapter.notifyItemRangeInserted(0, trainers.size());

        hideProgressBar();
    }


}
