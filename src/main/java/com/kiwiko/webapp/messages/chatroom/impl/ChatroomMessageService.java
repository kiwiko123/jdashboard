package com.kiwiko.webapp.messages.chatroom.impl;

import com.kiwiko.webapp.messages.data.MessageType;
import com.kiwiko.webapp.messages.impl.ParameterizedTypeMessageService;

public class ChatroomMessageService extends ParameterizedTypeMessageService {

    @Override
    protected MessageType getMessageType() {
        return MessageType.CHATROOM;
    }
}
