package com.kiwiko.webapp.chatroom.impl;

import com.kiwiko.webapp.chatroom.internal.ChatroomPushService;
import com.kiwiko.webapp.chatroom.internal.data.ChatroomPushMessageData;
import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.data.MessageType;
import com.kiwiko.webapp.messages.impl.ParameterizedTypeMessageService;
import com.kiwiko.webapp.push.api.parameters.PushToClientParameters;

import javax.inject.Inject;

public class ChatroomMessageService extends ParameterizedTypeMessageService {

    @Inject private ChatroomPushService chatroomPushService;

    @Override
    protected MessageType getMessageType() {
        return MessageType.CHATROOM;
    }

    @Override
    public Message send(Message message) {
        Message result = super.send(message);

        ChatroomPushMessageData pushData = new ChatroomPushMessageData()
                .withMessageId(result.getId());
        PushToClientParameters pushParameters = new PushToClientParameters();
        pushParameters.setUserId(result.getSenderUserId());
        pushParameters.setRecipientUserId(result.getRecipientUserId());
        pushParameters.setData(pushData);
        chatroomPushService.pushToClient(pushParameters);

        return result;
    }
}
