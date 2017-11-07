package org.badhan.blooddonor.service.contact;

import org.badhan.blooddonor.core.ServiceRequest;
import org.badhan.blooddonor.core.ServiceResponse;
import org.badhan.blooddonor.entity.ContactRequest;

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
