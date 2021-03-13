package com.kiwiko.webapp.chatroom.internal;

import com.kiwiko.webapp.push.api.PushReceiver;
import com.kiwiko.webapp.push.data.PushServiceIdentifier;
import com.kiwiko.webapp.push.impl.PushServiceImpl;

import javax.inject.Inject;
import java.util.Optional;

public class ChatroomPushService extends PushServiceImpl {

    @Inject private ChatroomPushReceiver chatroomPushReceiver;

    @Override
    public String getServiceId() {
        return PushServiceIdentifier.CHATROOM.getId();
    }

    @Override
    protected Optional<PushReceiver> getPushReceiver() {
        return Optional.of(chatroomPushReceiver);
    }
}
