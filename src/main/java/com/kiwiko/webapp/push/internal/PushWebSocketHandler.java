package com.kiwiko.webapp.push.internal;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.push.api.PushServiceRegistry;
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
        String json = message.getPayload();
        ClientPushRequest requestPayload = pushRequestHelper.deserializeClientPushRequest(json);

        if (pushServiceSessionManager.hasSession(session)) {
            pushRequestHelper.validateClientPushRequest(requestPayload);
            pushServiceSessionManager.sync(session, requestPayload.getRecipientUserId());
            pushServiceRegistry.getPushServicesForId(requestPayload.getServiceId())
                    .forEach(pushService -> pushRequestHelper.routeIncomingPush(pushService, requestPayload, message));
        } else {
            // The connection has just been established.
            pushRequestHelper.validateInitialUserPushRequest(requestPayload);
            pushServiceSessionManager.startSession(session);
            afterUserConnectionEstablished(session, requestPayload.getUserId());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
//        if (!VALID_CLOSE_STATUSES.contains(status)) {
//            String message = String.format("Unexpected close status %s for session ID %s", status.toString(), session.getId());
//            logService.warn(message);
//        }

        pushServiceSessionManager.endSession(session);
    }

    protected void afterUserConnectionEstablished(WebSocketSession session, long userId) { }
}
