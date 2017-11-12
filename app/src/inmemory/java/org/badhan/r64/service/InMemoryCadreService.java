package org.badhan.r64.service;

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
        GetCadres.Response response = new GetCadres.Response();
        response.cadres = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            String stringId = Integer.toString(i);
            Cadre cadre = new Cadre();
            cadre.setId(i);
            cadre.setDisplayName("Cadre name "+ stringId);
            cadre.setBatch("32");
            cadre.setCadreType("BCS Administration");
            cadre.setPostingAddress("Chittagong PWD");
            cadre.setAvatarUrl("https://lorempixel.com/64/64/people/"+stringId);
            cadre.setEmail("user"+stringId +"@example.com");

            response.cadres.add(cadre);
        }

        postDelayed(response);
    }
}
