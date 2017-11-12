package org.badhan.r64.service.cadre;

import org.badhan.r64.core.ServiceRequest;
import org.badhan.r64.core.ServiceResponse;
import org.badhan.r64.entity.Cadre;

import java.util.ArrayList;

public final class GetCadres {
    private GetCadres(){}

    public static class Request extends ServiceRequest{
        public String group;

        public Request(){}

        public Request(String group) {
            this.group = group;
        }
    }


    public static class Response extends ServiceResponse{
        public ArrayList<Cadre> cadres;
    }
}
