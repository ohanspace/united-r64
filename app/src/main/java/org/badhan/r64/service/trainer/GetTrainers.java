package org.badhan.r64.service.trainer;


import org.badhan.r64.core.ServiceRequest;
import org.badhan.r64.core.ServiceResponse;
import org.badhan.r64.entity.Trainer;

import java.util.ArrayList;

public final class GetTrainers {
    private GetTrainers(){}

    public static class Request extends ServiceRequest{

    }

    public static class Response extends ServiceResponse{
        public ArrayList<Trainer> trainers;
    }

}
