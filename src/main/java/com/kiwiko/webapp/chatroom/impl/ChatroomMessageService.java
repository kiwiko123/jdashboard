package com.kiwiko.webapp.chatroom.impl;

import com.kiwiko.webapp.chatroom.internal.ChatroomPushService;
import com.kiwiko.webapp.messages.data.MessageType;
import com.kiwiko.webapp.messages.impl.ParameterizedTypeMessageService;

import javax.inject.Inject;

public class ChatroomMessageService extends ParameterizedTypeMessageService {

    @Inject private ChatroomPushService chatroomPushService;

    @Override
    protected MessageType getMessageType() {
        return MessageType.CHATROOM;
    }
}
