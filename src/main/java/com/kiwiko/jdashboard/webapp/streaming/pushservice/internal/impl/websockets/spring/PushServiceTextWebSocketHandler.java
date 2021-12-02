package com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.mvc.json.gson.GsonProvider;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.dto.ClientPushRequest;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.PushService;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.exceptions.PushException;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.parameters.PushToClientParameters;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.dto.ConfirmConnectionParameters;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.sessions.PushServiceWebSocketSessionManager;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.setup.PushServiceSubscriberRouter;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.inject.Inject;

public class PushServiceTextWebSocketHandler extends TextWebSocketHandler {

    @Inject private GsonProvider gsonProvider;
    @Inject private PushService pushService;
    @Inject private PushServiceWebSocketSessionManager sessionManager;
    @Inject private PushServiceDeserializationHelper pushServiceDeserializationHelper;
    @Inject private PushServiceValidator pushServiceValidator;
    @Inject private PushServiceSubscriberRouter pushServiceSubscriberRouter;
    @Inject private Logger logger;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String json = message.getPayload();
        logger.debug(String.format("Received push from client with payload: %s", json));

        ClientPushRequest clientPushRequest = pushServiceDeserializationHelper.deserializeClientPushRequest(json);
        pushServiceValidator.validateInitialPushRequest(clientPushRequest);

        handleIncomingPush(session, clientPushRequest, json);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.debug(String.format("Closing web socket session %s with status %s", session.getId(), status.toString()));
        sessionManager.endSession(session.getId());
    }

    private void handleIncomingPush(WebSocketSession session, ClientPushRequest request, String message) {
        boolean isNewSession = sessionManager.getSessionById(session.getId()).isEmpty();
        if (isNewSession) {
            startSession(session, request);
        } else {
            pushServiceSubscriberRouter.routeMessageToSubscribers(request, message);
        }
    }

    private void startSession(WebSocketSession session, ClientPushRequest request) {
        sessionManager.storeSession(session, request);

        ConfirmConnectionParameters confirmConnectionParameters = new ConfirmConnectionParameters();
        confirmConnectionParameters.setSessionId(session.getId());
        String jsonParameters = gsonProvider.getDefault().toJson(confirmConnectionParameters);

        PushToClientParameters pushToClientParameters = new PushToClientParameters();
        pushToClientParameters.setUserId(request.getUserId());
        pushToClientParameters.setRecipientUserId(request.getUserId());
        pushToClientParameters.setServiceId(request.getServiceId());
        pushToClientParameters.setData(jsonParameters);

        try {
            pushService.pushToClient(pushToClientParameters);
        } catch (PushException e) {
            logger.error(String.format("Unable to confirm push service connection for recipient user ID %d", request.getUserId()));
        }
    }
}
