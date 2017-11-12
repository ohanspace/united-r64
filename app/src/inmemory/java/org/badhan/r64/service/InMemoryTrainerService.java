package org.badhan.r64.service;

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
        GetTrainers.Response response = new GetTrainers.Response();
        response.trainers = new ArrayList<Trainer>();

        for (int i = 0; i < 10; i++){
            String stringId = Integer.toString(i);

            Trainer trainer = new Trainer();
            trainer.setId(i);
            trainer.setAlphabet("T");
            trainer.setDisplayName("Trainer "+ stringId);
            trainer.setDesignation("Director General");
            trainer.setTrainingPost("Chief advisor");
            trainer.setTelephone("01922503521");
            trainer.setEmail("borhan.chittagong@gmal.com");

            response.trainers.add(trainer);
        }

        postDelayed(response);

    }
}
