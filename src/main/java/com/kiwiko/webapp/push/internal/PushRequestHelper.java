package com.kiwiko.webapp.push.internal;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.mvc.json.api.JsonMapper;
import com.kiwiko.webapp.mvc.json.api.errors.JsonException;
import com.kiwiko.webapp.push.api.PushService;
import com.kiwiko.webapp.push.api.errors.ClientUnreachablePushException;
import com.kiwiko.webapp.push.api.errors.PushException;
import com.kiwiko.webapp.push.api.parameters.OnPushReceivedParameters;
import com.kiwiko.webapp.push.api.parameters.PushToClientParameters;
import com.kiwiko.webapp.push.data.ClientPushRequest;
import com.kiwiko.webapp.push.data.PushRequest;
import org.springframework.web.socket.TextMessage;

import javax.inject.Inject;

public class PushRequestHelper {

    @Inject private JsonMapper jsonMapper;
    @Inject private LogService logService;

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
    }

    public void validateClientPushRequest(ClientPushRequest request) throws PushException {
        if (request.getServiceId() == null) {
            throw new PushException("Service ID is required");
        }

        if (request.getUserId() == null) {
            throw new PushException("(Sender) user ID is required");
        }

        if (request.getRecipientUserId() == null) {
            throw new PushException("Recipient user ID is required");
        }
    }

    public void pushToClient(PushService pushService, ClientPushRequest clientPushRequest, TextMessage message) {
        PushToClientParameters parameters = new PushToClientParameters();
        parameters.setUserId(clientPushRequest.getUserId());
        parameters.setRecipientUserId(clientPushRequest.getRecipientUserId());
        parameters.setServiceId(clientPushRequest.getServiceId());
        parameters.setMessage(message.getPayload());

        try {
            pushService.pushToClient(parameters);
        } catch (ClientUnreachablePushException e) {
            logService.error("Failed to push", e);
        }
    }

    public void routeIncomingPush(PushService pushService, ClientPushRequest clientPushRequest, TextMessage message) {
        OnPushReceivedParameters parameters = new OnPushReceivedParameters();
        parameters.setServiceId(clientPushRequest.getServiceId());
        parameters.setUserId(clientPushRequest.getUserId());
        parameters.setRecipientUserId(clientPushRequest.getRecipientUserId());
        parameters.setMessage(message.getPayload());

        pushService.onPushReceived(parameters);
    }
}
