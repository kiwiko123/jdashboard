package com.kiwiko.webapp.apps.chatroom.internal;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.push.api.PushReceiver;
import com.kiwiko.webapp.push.api.parameters.OnPushReceivedParameters;
import com.kiwiko.webapp.push.data.PushServiceIdentifier;

import javax.inject.Inject;

public class ChatroomPushReceiver implements PushReceiver {

    @Inject private Logger logger;

    @Override
    public String getServiceId() {
        return PushServiceIdentifier.CHATROOM.getId();
    }

    @Override
    public void onPushReceived(OnPushReceivedParameters parameters) {
        logger.debug(String.format("Chatroom received push \"%s\"", parameters.getMessage()));
    }
}
