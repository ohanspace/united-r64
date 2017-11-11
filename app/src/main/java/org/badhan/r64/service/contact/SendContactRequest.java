package org.badhan.r64.service.contact;

import org.badhan.r64.core.ServiceRequest;
import org.badhan.r64.core.ServiceResponse;

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
