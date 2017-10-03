package org.badhan.blooddonor.service.auth;

import org.badhan.blooddonor.core.ServiceRequest;

public final class Register {
    private Register(){}

    public static class Request extends ServiceRequest{
        public String displayName;
        public String email;
        public String password;
    }

    public static class Response extends LoginResponse{

    }
}
