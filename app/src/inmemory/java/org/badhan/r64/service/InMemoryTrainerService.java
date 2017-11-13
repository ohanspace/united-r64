package org.badhan.r64.service;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.squareup.otto.Subscribe;

import org.badhan.r64.core.MyApplication;
import org.badhan.r64.entity.Trainer;
import org.badhan.r64.service.trainer.GetTrainers;

import java.util.ArrayList;

public class InMemoryTrainerService extends BaseInMemoryService {
    protected InMemoryTrainerService(MyApplication application) {
        super(application);
    }

    @Subscribe
    public void GetTrainers(GetTrainers.Request request){
        final GetTrainers.Response response = new GetTrainers.Response();
        response.trainers = new ArrayList<Trainer>();

        DatabaseReference trainersRef = application.getFirebaseDatabase().getReference("trainers");

        trainersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("trainers data", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Trainer trainer = dataSnapshot.getValue(Trainer.class);
                response.trainers.add(trainer);
                bus.post(response);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        for (int i = 0; i < 10; i++){
//            String stringId = Integer.toString(i);
//
//            Trainer trainer = new Trainer();
//            trainer.setId(i);
//            trainer.setAlphabet("T");
//            trainer.setDisplayName("Trainer "+ stringId);
//            trainer.setDesignation("Director General");
//            trainer.setTrainingPost("Chief advisor");
//            trainer.setTelephone("01922503521");
//            trainer.setEmail("borhan.chittagong@gmal.com");
//
//            response.trainers.add(trainer);
//        }


    }
}
