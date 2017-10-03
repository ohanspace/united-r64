package org.badhan.blooddonor.service.profile;

import android.net.Uri;

import org.badhan.blooddonor.core.ServiceRequest;
import org.badhan.blooddonor.core.ServiceResponse;

public final class ChangeAvatar {

    private ChangeAvatar(){}


    public static class Request extends ServiceRequest{
        public Uri newAvatarUri;

        public Request(Uri newAvatarUri){
            this.newAvatarUri = newAvatarUri;
        }
    }

    public static class Response extends ServiceResponse {

    }
}
