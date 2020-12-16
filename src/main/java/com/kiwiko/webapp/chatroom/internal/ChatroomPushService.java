package com.kiwiko.webapp.chatroom.internal;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.push.api.parameters.OnPushReceivedParameters;
import com.kiwiko.webapp.push.api.parameters.PushToClientParameters;
import com.kiwiko.webapp.push.data.PushServiceIdentifier;
import com.kiwiko.webapp.push.impl.TextWebSocketPushService;

import javax.inject.Inject;

public class ChatroomPushService extends TextWebSocketPushService {

    @Inject private LogService logService;

    @Override
    public String getServiceId() {
        return PushServiceIdentifier.CHATROOM.getId();
    }

    @Override
    public boolean shouldPushToClient(PushToClientParameters parameters) {
        // TODO?
        return true;
    }

    @Override
    public void onPushReceived(OnPushReceivedParameters parameters) {
        logService.debug(String.format("Chatroom received push \"%s\"", parameters.getMessage()));
    }
}
