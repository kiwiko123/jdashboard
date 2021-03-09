package com.kiwiko.webapp.chatroom.internal;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.push.api.PushReceiver;
import com.kiwiko.webapp.push.api.parameters.OnPushReceivedParameters;
import com.kiwiko.webapp.push.data.PushServiceIdentifier;

import javax.inject.Inject;

public class ChatroomPushReceiver implements PushReceiver {

    @Inject private LogService logService;

    @Override
    public String getServiceId() {
        return PushServiceIdentifier.CHATROOM.getId();
    }

    @Override
    public void onPushReceived(OnPushReceivedParameters parameters) {
        logService.debug(String.format("Chatroom received push \"%s\"", parameters.getMessage()));
    }
}
