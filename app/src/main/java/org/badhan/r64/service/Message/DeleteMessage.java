package org.badhan.r64.service.Message;

import org.badhan.r64.core.ServiceRequest;
import org.badhan.r64.core.ServiceResponse;

public final class DeleteMessage {
    private DeleteMessage(){}

    public static class Request extends ServiceRequest{
        public int messageId;

        public Request(int messageId) {
            this.messageId = messageId;
        }
    }

    public static class Response extends ServiceResponse{
        public int messageId;
    }
}
