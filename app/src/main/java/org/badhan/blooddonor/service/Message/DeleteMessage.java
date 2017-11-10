package org.badhan.blooddonor.service.Message;

import org.badhan.blooddonor.core.ServiceRequest;
import org.badhan.blooddonor.core.ServiceResponse;

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
