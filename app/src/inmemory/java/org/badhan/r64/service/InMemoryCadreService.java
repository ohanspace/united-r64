package org.badhan.r64.service;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.squareup.otto.Subscribe;

import org.badhan.r64.core.MyApplication;
import org.badhan.r64.entity.Cadre;
import org.badhan.r64.service.cadre.GetCadres;

import java.util.ArrayList;

public class InMemoryCadreService extends BaseInMemoryService {
    protected InMemoryCadreService(MyApplication application) {
        super(application);
    }


    @Subscribe
    public void GetCadres(GetCadres.Request request){
        final GetCadres.Response response = new GetCadres.Response();
        response.cadres = new ArrayList<>();

        DatabaseReference cadresRef = application.getFirebaseDatabase().getReference("cadres");
        cadresRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cadre cadre = dataSnapshot.getValue(Cadre.class);
                response.cadres.add(cadre);
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
//            Cadre cadre = new Cadre();
//            cadre.setId(i);
//            cadre.setDisplayName("Cadre name "+ stringId);
//            cadre.setBatch("32");
//            cadre.setCadreType("BCS Administration");
//            cadre.setPostingAddress("Chittagong PWD");
//            cadre.setAvatarUrl("https://lorempixel.com/64/64/people/"+stringId);
//            cadre.setEmail("user"+stringId +"@example.com");
//
//            response.cadres.add(cadre);
//        }
//        postDelayed(response);
    }
}
