package com.kiwiko.webapp.messages.web;

import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.data.MessageStatus;
import com.kiwiko.webapp.messages.data.MessageType;
import com.kiwiko.webapp.mvc.json.data.IntermediateJsonBody;
import com.kiwiko.webapp.mvc.json.impl.EasyRequestBodySerializationStrategy;

public class MessageDeserializationStrategy extends EasyRequestBodySerializationStrategy<Message> {

    @Override
    public Message deserialize(IntermediateJsonBody body, Message target) {
        Message message = super.deserialize(body, target);

        body.getValue("recipientUserId")
                .map(id -> (Integer) id)
                .map(Integer::longValue)
                .ifPresent(message::setRecipientUserId);

        body.getValue("messageType")
                .map(type -> (Integer) type)
                .map(MessageType::getById)
                .ifPresent(message::setMessageType);

        body.getValue("messageStatus")
                .map(status -> (String) status)
                .map(MessageStatus::getById)
                .ifPresent(message::setMessageStatus);

        return message;
    }
}
