package com.kiwiko.webapp.push.impl;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.mvc.json.api.JsonMapper;
import com.kiwiko.webapp.push.api.PushService;
import com.kiwiko.webapp.push.api.errors.ClientUnreachablePushException;
import com.kiwiko.webapp.push.api.errors.PushException;
import com.kiwiko.webapp.push.api.parameters.PushToClientParameters;
import com.kiwiko.webapp.push.internal.PushServiceSessionManager;
import com.kiwiko.webapp.push.internal.impl.PushNotificationDeliveryService;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.inject.Inject;
import java.io.IOException;

abstract class TextWebSocketPushService implements PushService {

    @Inject private JsonMapper jsonMapper;
    @Inject private LogService logService;
    @Inject private PushServiceSessionManager pushServiceSessionManager;
    @Inject private PushNotificationDeliveryService pushNotificationDeliveryService;

    @Override
    public void pushToClient(PushToClientParameters parameters) throws PushException {
        normalizePushParameters(parameters);

        Long recipientUserId = parameters.getRecipientUserId();
        WebSocketSession session = pushServiceSessionManager.getSessionForUser(recipientUserId)
                .orElseThrow(() -> new ClientUnreachablePushException(
                        String.format("Unable to reach user ID %d", recipientUserId)));
        Object data = parameters.getData();
        String jsonData = jsonMapper.writeValueAsString(data);
        WebSocketMessage<String> message = new TextMessage(jsonData);

        try {
            session.sendMessage(message);
        } catch (IOException e) {
            pushNotificationDeliveryService.enqueueMissedNotification(parameters);
            // TODO throwing the exception causes the transaction to be rolled back
            throw new PushException(
                    String.format(
                            "Failed to send message \"%s\" from user ID %d to user ID %d",
                            jsonData,
                            parameters.getUserId(),
                            recipientUserId),
                    e);
        }
    }

    private void normalizePushParameters(PushToClientParameters parameters) {
        parameters.setServiceId(getServiceId());
    }
}
