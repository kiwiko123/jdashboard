package com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.PushService;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.exceptions.ClientUnreachablePushException;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.exceptions.PushException;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.parameters.PushToClientParameters;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.sessions.PushServiceWebSocketSessionManager;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Optional;

public class TextWebSocketPushService implements PushService {

    @Inject private PushServiceWebSocketSessionManager pushServiceSessionManager;
    @Inject private PushServiceValidator pushServiceValidator;
    @Inject private Logger logger;

    @Override
    public void pushToClient(PushToClientParameters parameters) throws PushException {
        pushServiceValidator.validatePushToClientParameters(parameters);
        WebSocketSession recipientSession = pushServiceSessionManager.getSessionForUser(parameters.getRecipientUserId())
                .orElseThrow(() -> new ClientUnreachablePushException(String.format("Unable to reach user ID %d", parameters.getRecipientUserId())));

        String messageData = Optional.ofNullable(parameters.getData()).orElse("");
        WebSocketMessage<String> message = new TextMessage(messageData);

        try {
            recipientSession.sendMessage(message);
        } catch (IOException e) {
            throw new PushException(String.format("Error sending push service message %s", parameters.toString()));
        }
    }
}
