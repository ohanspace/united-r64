package org.badhan.blooddonor.service;

import android.net.Uri;

import org.badhan.blooddonor.core.ServiceResponse;

public final class AccountService {
    private AccountService(){}

    public static class AvatarChangeRequest{
        private Uri newAvatarUri;

        public AvatarChangeRequest(Uri newAvatarUri){
            this.newAvatarUri = newAvatarUri;
        }
    }


    public static class AvatarChangeResponse extends ServiceResponse{

    }


    public static class ProfileUpdateRequest{
        public String displayName;
        public String email;

        public ProfileUpdateRequest(String displayName, String email) {
            this.displayName = displayName;
            this.email = email;
        }
    }


    public static class ProfileUpdateResponse extends ServiceResponse{

    }


    public static class PasswordChangeRequest {
        public String currentPassword;
        public String newPassword;
        public String confirmNewPassword;

        public PasswordChangeRequest(String currentPassword, String newPassword, String confirmNewPassword) {
            this.currentPassword = currentPassword;
            this.newPassword = newPassword;
            this.confirmNewPassword = confirmNewPassword;
        }
    }


    public static class PasswordChangeResponse extends ServiceResponse{

    }

}
