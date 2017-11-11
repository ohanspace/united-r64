package org.badhan.r64.service.contact;

import org.badhan.r64.core.ServiceRequest;
import org.badhan.r64.core.ServiceResponse;
import org.badhan.r64.entity.UserDetails;

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
