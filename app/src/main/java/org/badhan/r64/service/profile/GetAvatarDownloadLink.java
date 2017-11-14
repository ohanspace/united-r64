package org.badhan.r64.service.profile;

import org.badhan.r64.core.ServiceRequest;
import org.badhan.r64.core.ServiceResponse;

public final class GetAvatarDownloadLink {
    private GetAvatarDownloadLink(){}

    public static class Request extends ServiceRequest{

    }

    public static class Response extends ServiceResponse{
        public String downloadLink;

        public String getDownloadLink() {
            return downloadLink;
        }

        public void setDownloadLink(String downloadLink) {
            this.downloadLink = downloadLink;
        }
    }


}
