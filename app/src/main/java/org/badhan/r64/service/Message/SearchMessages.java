package org.badhan.r64.service.Message;

import org.badhan.r64.core.ServiceRequest;
import org.badhan.r64.core.ServiceResponse;
import org.badhan.r64.entity.Message;

import java.util.List;

public final class SearchMessages {
    private SearchMessages(){}

    public static class Request extends ServiceRequest{
        public int fromContactId;
        public boolean includeSentMessages;
        public boolean includeReceivedMessages;

        public Request(int fromContactId, boolean includeSentMessages, boolean includeReceivedMessages) {
            this.fromContactId = fromContactId;
            this.includeSentMessages = includeSentMessages;
            this.includeReceivedMessages = includeReceivedMessages;
        }

        public Request(boolean includeSentMessages, boolean includeReceivedMessages) {
            this.includeSentMessages = includeSentMessages;
            this.includeReceivedMessages = includeReceivedMessages;
        }
    }


    public static class Response extends ServiceResponse{
        public List<Message> messages;
    }
}
