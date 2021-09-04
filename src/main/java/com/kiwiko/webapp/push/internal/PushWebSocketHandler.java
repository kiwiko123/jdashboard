package com.kiwiko.webapp.push.internal;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.push.api.PushReceiverRegistry;
import com.kiwiko.webapp.push.api.errors.PushException;
import com.kiwiko.webapp.push.data.ClientPushRequest;
import com.kiwiko.webapp.push.internal.impl.PushNotificationDeliveryService;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.inject.Inject;
import java.util.Set;

// TODO only one web socket connection per user/session can be active at once.
public class PushWebSocketHandler extends TextWebSocketHandler {
    private static final Set<CloseStatus> VALID_CLOSE_STATUSES = Set.of(CloseStatus.NORMAL, CloseStatus.NO_CLOSE_FRAME);

    @Inject private Logger logger;
    @Inject private PushRequestHelper pushRequestHelper;
    @Inject private PushReceiverRegistry pushReceiverRegistry;
    @Inject private PushServiceSessionManager pushServiceSessionManager;
    @Inject private PushNotificationDeliveryService pushNotificationDeliveryService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String json = message.getPayload();
        ClientPushRequest requestPayload = pushRequestHelper.deserializeClientPushRequest(json);
        pushRequestHelper.validateInitialUserPushRequest(requestPayload);

        try {
            handlePush(session, requestPayload, message);
        } catch (PushException e) {
            pushServiceSessionManager.endSession(session);
            throw e;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // TODO
        pushServiceSessionManager.endSession(session);
    }

    protected void afterUserConnectionEstablished(WebSocketSession session, ClientPushRequest pushRequest) { }

    private void handlePush(WebSocketSession session, ClientPushRequest pushRequest, TextMessage message) {
        boolean isNewSession = pushServiceSessionManager.getSessionForUser(pushRequest.getUserId()).isEmpty();

        if (isNewSession) {
            pushServiceSessionManager.startSession(pushRequest.getUserId(), session);
            pushNotificationDeliveryService.sendPendingNotifications(pushRequest.getUserId());
            afterUserConnectionEstablished(session, pushRequest);
            return;
        }

        Long recipientUserId = pushRequest.getRecipientUserId();
        if (recipientUserId != null) {
            pushRequestHelper.validateClientPushRequest(pushRequest);
            pushReceiverRegistry.getPushReceiversForService(pushRequest.getServiceId())
                    .forEach(pushService -> pushRequestHelper.routeIncomingPush(pushService, pushRequest, message));
        }
    }
}
