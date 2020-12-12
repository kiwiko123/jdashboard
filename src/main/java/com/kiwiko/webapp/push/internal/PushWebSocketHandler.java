package com.kiwiko.webapp.push.internal;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.push.api.PushServiceRegistry;
import com.kiwiko.webapp.push.api.errors.PushException;
import com.kiwiko.webapp.push.data.ClientPushRequest;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.inject.Inject;
import java.util.Set;

public class PushWebSocketHandler extends TextWebSocketHandler {

    private static final Set<CloseStatus> VALID_CLOSE_STATUSES = Set.of(CloseStatus.NORMAL, CloseStatus.NO_CLOSE_FRAME);

    @Inject private LogService logService;
    @Inject private PushRequestHelper pushRequestHelper;
    @Inject private PushServiceRegistry pushServiceRegistry;
    @Inject private PushServiceSessionManager pushServiceSessionManager;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            handlePush(session, message);
        } catch (PushException e) {
            pushServiceSessionManager.endSession(session);
            throw e;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        pushServiceSessionManager.endSession(session);
    }

    protected void afterUserConnectionEstablished(WebSocketSession session, long userId) { }

    private void handlePush(WebSocketSession session, TextMessage message) {
        String json = message.getPayload();
        ClientPushRequest requestPayload = pushRequestHelper.deserializeClientPushRequest(json);
        pushRequestHelper.validateInitialUserPushRequest(requestPayload);
        boolean isNewSession = !pushServiceSessionManager.hasSession(requestPayload.getUserId());

        if (isNewSession) {
            pushServiceSessionManager.startSession(session, requestPayload.getUserId());
            afterUserConnectionEstablished(session, requestPayload.getUserId());
            return;
        }

        Long recipientUserId = requestPayload.getRecipientUserId();
        if (recipientUserId != null && pushServiceSessionManager.hasSession(recipientUserId)) {
            pushRequestHelper.validateClientPushRequest(requestPayload);
            pushServiceRegistry.getPushServicesForId(requestPayload.getServiceId())
                    .forEach(pushService -> pushRequestHelper.routeIncomingPush(pushService, requestPayload, message));
        }
    }
}
