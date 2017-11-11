package org.badhan.r64.service.contact;

import org.badhan.r64.core.ServiceRequest;
import org.badhan.r64.core.ServiceResponse;
import org.badhan.r64.entity.ContactRequest;

import java.util.List;


public final class GetContactRequests  {
    private GetContactRequests(){}

    public static class Request extends ServiceRequest{
        public boolean fromMe;

        public Request(boolean fromMe) {
            this.fromMe = fromMe;
        }
    }


    public static class Response extends ServiceResponse{
        public List<ContactRequest> requests;
    }
}
