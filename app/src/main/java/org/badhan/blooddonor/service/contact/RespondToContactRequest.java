package org.badhan.blooddonor.service.contact;

import org.badhan.blooddonor.core.ServiceRequest;
import org.badhan.blooddonor.core.ServiceResponse;

public final class RespondToContactRequest {
    private RespondToContactRequest(){}

    public static class Request extends ServiceRequest{
        public int contactRequestId;
        public boolean accept;

        public Request(int contactRequestId, boolean accept){
            this.contactRequestId = contactRequestId;
            this.accept = accept;
        }
    }

    public static class Response extends ServiceResponse{

    }
}
