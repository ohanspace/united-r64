package org.badhan.r64.service.auth;

import org.badhan.r64.core.ServiceRequest;

public final class RegisterWithExternalProvider {
    private RegisterWithExternalProvider(){}

    public static class Request extends ServiceRequest{
    }

    public static class Response extends LoginResponse{

    }
}
