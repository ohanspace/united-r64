package org.badhan.r64.service.profile;

import android.net.Uri;

import org.badhan.r64.core.ServiceRequest;
import org.badhan.r64.core.ServiceResponse;

public final class ChangeAvatar {

    private ChangeAvatar(){}


    public static class Request extends ServiceRequest{
        public String newAvatarUri;

        public Request(String newAvatarUri){
            this.newAvatarUri = newAvatarUri;
        }
    }

    public static class Response extends ServiceResponse {

    }
}
