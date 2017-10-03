package org.badhan.blooddonor.service.auth;

import org.badhan.blooddonor.core.ServiceRequest;

public final class RegisterWithExternalProvider {
    private RegisterWithExternalProvider(){}

    public static class Request extends ServiceRequest{
    }

    public static class Response extends LoginResponse{

    }
}
