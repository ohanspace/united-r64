package org.badhan.blooddonor.service.profile;

import org.badhan.blooddonor.core.ServiceRequest;
import org.badhan.blooddonor.core.ServiceResponse;

public final class ChangePassword {

    private ChangePassword(){}


    public static class Request extends ServiceRequest{
        public String currentPassword;
        public String newPassword;
        public String confirmNewPassword;

        public Request(String currentPassword, String newPassword, String confirmNewPassword) {
            this.currentPassword = currentPassword;
            this.newPassword = newPassword;
            this.confirmNewPassword = confirmNewPassword;
        }
    }

    public static class Response extends ServiceResponse {

    }
}
