package org.badhan.blooddonor.service.contact;

import org.badhan.blooddonor.core.ServiceRequest;
import org.badhan.blooddonor.core.ServiceResponse;
import org.badhan.blooddonor.entity.UserDetails;

import java.util.ArrayList;
import java.util.List;

public final class GetContacts {
    private GetContacts(){}

    public static class Request extends ServiceRequest{
        public boolean includePendingRequests;

        public Request(boolean includePendingRequests) {
            this.includePendingRequests = includePendingRequests;
        }
    }

    public static class Response extends ServiceResponse{
        public List<UserDetails> contacts = new ArrayList<>();
    }
}
