package org.badhan.blooddonor.service.contact;

import org.badhan.blooddonor.core.ServiceRequest;
import org.badhan.blooddonor.core.ServiceResponse;

public final class SendContactRequest {
    private SendContactRequest(){}

    public static class Request extends ServiceRequest{
        public int userId;

        public Request(int userId) {
            this.userId = userId;
        }
    }

    public static class Response extends ServiceResponse{

    }

}
