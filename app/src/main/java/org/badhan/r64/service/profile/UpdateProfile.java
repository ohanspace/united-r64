package org.badhan.r64.service.profile;

import org.badhan.r64.core.ServiceRequest;
import org.badhan.r64.core.ServiceResponse;

public final class UpdateProfile {

    private UpdateProfile(){}


    public static class Request extends ServiceRequest{
        public String displayName;
        public String email;

        public Request(String displayName, String email) {
            this.displayName = displayName;
            this.email = email;
        }
    }

    public static class Response extends ServiceResponse {

    }
}
