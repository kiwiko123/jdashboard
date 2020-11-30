package com.kiwiko.webapp.push.impl;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.push.api.PushService;
import com.kiwiko.webapp.push.api.errors.ClientUnreachablePushException;
import com.kiwiko.webapp.push.api.errors.PushException;
import com.kiwiko.webapp.push.api.parameters.PushToClientParameters;
import com.kiwiko.webapp.push.internal.PushServiceSessionManager;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.inject.Inject;
import java.io.IOException;

public abstract class TextWebSocketPushService implements PushService {

    @Inject private LogService logService;
    @Inject private PushServiceSessionManager pushServiceSessionManager;

    @Override
    public void pushToClient(PushToClientParameters parameters) throws ClientUnreachablePushException, PushException {
        if (!shouldPushToClient(parameters)) {
            logService.debug("Skipping push to client");
            return;
        }

        Long recipientUserId = parameters.getRecipientUserId();
        WebSocketSession session = pushServiceSessionManager.getSessionByUserId(recipientUserId)
                .orElseThrow(() -> new ClientUnreachablePushException(
                        String.format("Unable to reach user ID %d", recipientUserId)));

        String message = parameters.getMessage();
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            throw new PushException(
                    String.format(
                            "Failed to send message \"%s\" from user ID %d to user ID %d",
                            message,
                            parameters.getUserId(),
                            recipientUserId),
                    e);
        }
    }
}
