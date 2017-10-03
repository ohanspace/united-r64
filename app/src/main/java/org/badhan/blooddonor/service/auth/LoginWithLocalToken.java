package org.badhan.blooddonor.service.auth;

import org.badhan.blooddonor.core.ServiceRequest;

public final class LoginWithLocalToken {

    private LoginWithLocalToken(){}

    public static class Request extends ServiceRequest{
        public String authToken;

        public Request(String authToken) {
            this.authToken = authToken;
        }
    }

    public static class Response extends LoginResponse{

    }
}
