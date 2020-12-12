package com.kiwiko.webapp.messages.web.helpers;

import com.kiwiko.webapp.chatroom.impl.ChatroomMessageService;
import com.kiwiko.webapp.messages.api.queries.data.GetBetweenParameters;
import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.messages.web.helpers.data.MessageDTO;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MessagesResponseHelper {

    @Inject private ChatroomMessageService messageService;

    public List<MessageDTO> getMessagesInThread(GetBetweenParameters parameters) {
        return messageService.getBetween(parameters).stream()
                .sorted(Comparator.comparing(Message::getSentDate))
                .map(message -> makeMessageDTO(message, parameters))
                .collect(Collectors.toList());
    }

    private MessageDTO makeMessageDTO(Message message, GetBetweenParameters parameters) {
        MessageDTO result = new MessageDTO();

        String direction = Objects.equals(message.getSenderUserId(), parameters.getSenderUserId())
                ? "outbound"
                : "inbound";

        result.setId(message.getId());
        result.setMessage(message.getMessage());
        result.setMessageType(message.getMessageType());
        result.setMessageStatus(message.getMessageStatus());
        result.setSenderUserId(message.getSenderUserId());
        result.setRecipientUserId(message.getRecipientUserId());
        result.setSenderName(""); // TODO
        result.setSentDate(message.getSentDate());
        result.setDirection(direction);

        return result;

    }
}
