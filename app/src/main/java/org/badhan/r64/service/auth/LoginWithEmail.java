package org.badhan.r64.service.auth;

import org.badhan.r64.core.ServiceRequest;
import org.badhan.r64.core.ServiceResponse;

public final class LoginWithEmail {
    private LoginWithEmail(){}

    public static class Request extends ServiceRequest{
        public String email;
        public String password;

        public Request(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }


    public static class Response extends LoginResponse{

    }
}
