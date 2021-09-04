package com.kiwiko.webapp.push.internal;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.mvc.json.api.JsonMapper;
import com.kiwiko.webapp.mvc.json.api.errors.JsonException;
import com.kiwiko.webapp.push.api.PushReceiver;
import com.kiwiko.webapp.push.api.errors.PushException;
import com.kiwiko.webapp.push.api.parameters.OnPushReceivedParameters;
import com.kiwiko.webapp.push.data.ClientPushRequest;
import com.kiwiko.webapp.push.data.PushRequest;
import org.springframework.web.socket.TextMessage;

import javax.inject.Inject;

public class PushRequestHelper {

    @Inject private JsonMapper jsonMapper;
    @Inject private Logger logger;

    public ClientPushRequest deserializeClientPushRequest(String json) throws PushException {
        try {
            return jsonMapper.deserialize(json, ClientPushRequest.class);
        } catch (JsonException e) {
            String message = String.format("Failed to deserialize push request: \"%s\"", json);
            throw new PushException(message, e);
        }
    }

    public <Request extends PushRequest> void validateInitialUserPushRequest(Request request) throws PushException {
        if (request.getUserId() == null) {
            throw new PushException("Current user ID is required");
        }

        if (request.getServiceId() == null) {
            throw new PushException("Service ID is required");
        }
    }

    public void validateClientPushRequest(ClientPushRequest request) throws PushException {
        validateInitialUserPushRequest(request);

        if (request.getRecipientUserId() == null) {
            throw new PushException("Recipient user ID is required");
        }
    }

    public void routeIncomingPush(PushReceiver pushReceiver, ClientPushRequest clientPushRequest, TextMessage message) {
        OnPushReceivedParameters parameters = new OnPushReceivedParameters();
        parameters.setServiceId(clientPushRequest.getServiceId());
        parameters.setUserId(clientPushRequest.getUserId());
        parameters.setRecipientUserId(clientPushRequest.getRecipientUserId());
        parameters.setMessage(message.getPayload());

        pushReceiver.onPushReceived(parameters);
    }
}
