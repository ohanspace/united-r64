package org.badhan.r64.service.auth;

import org.badhan.r64.core.ServiceRequest;

public final class Register {
    private Register(){}

    public static class Request extends ServiceRequest{
        public String displayName;
        public String email;
        public String password;

        public Request(String displayName, String email, String password) {
            this.displayName = displayName;
            this.email = email;
            this.password = password;
        }
    }

    public static class Response extends LoginResponse{

    }
}
