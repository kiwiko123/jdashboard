package com.kiwiko.webapp.messages.web;

import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.data.MessageStatus;
import com.kiwiko.webapp.messages.data.MessageType;
import com.kiwiko.library.json.data.IntermediateJsonBody;
import com.kiwiko.webapp.mvc.json.impl.EasyRequestBodySerializationStrategy;

public class MessageDeserializationStrategy extends EasyRequestBodySerializationStrategy<Message> {

    @Override
    public Message deserialize(IntermediateJsonBody body, Message target) {
        Message message = super.deserialize(body, target);

        body.getValueAs("recipientUserId", Integer.class)
                .map(Integer::longValue)
                .ifPresent(message::setRecipientUserId);

        body.getValueAs("messageType", Integer.class)
                .map(MessageType::getById)
                .ifPresent(message::setMessageType);

        body.getValueAs("messageStatus", String.class)
                .map(MessageStatus::getById)
                .ifPresent(message::setMessageStatus);

        body.getValueAs("id", Integer.class)
                .map(Integer::longValue)
                .ifPresent(message::setId);

        return message;
    }
}
