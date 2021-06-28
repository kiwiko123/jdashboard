package com.kiwiko.webapp.streaming.pushservice.internal.impl.websockets.spring;

import com.kiwiko.webapp.streaming.pushservice.api.dto.ClientPushRequest;
import com.kiwiko.webapp.streaming.pushservice.api.dto.PushRequest;
import com.kiwiko.webapp.streaming.pushservice.api.interfaces.parameters.PushToClientParameters;

import java.util.Objects;

public class PushServiceValidator {

    public void validateInitialPushRequest(PushRequest request) throws NullPointerException {
        Objects.requireNonNull(request, "Invalid request");
        Objects.requireNonNull(request.getServiceId(), "Service ID is required for push service");
        Objects.requireNonNull(request.getUserId(), "User ID is required for push service");
    }

    public void validateClientPushRequest(ClientPushRequest request) throws NullPointerException {
        validateInitialPushRequest(request);
        Objects.requireNonNull(request.getRecipientUserId(), "Recipient user ID is required to push messages");
    }

    public void validatePushToClientParameters(PushToClientParameters parameters) throws NullPointerException {
        validateInitialPushRequest(parameters);
    }
}
