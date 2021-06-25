package com.kiwiko.webapp.apps.chatroom.web.helpers;

import com.kiwiko.webapp.apps.chatroom.impl.ChatroomMessageService;
import com.kiwiko.webapp.messages.api.queries.data.GetBetweenParameters;
import com.kiwiko.webapp.messages.data.Message;
import com.kiwiko.webapp.apps.chatroom.web.helpers.data.MessageDTO;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChatroomResponseHelper {

    @Inject private ChatroomMessageService messageService;

    public List<MessageDTO> getMessagesInThread(GetBetweenParameters parameters) {
        return messageService.getBetween(parameters).stream()
                .map(message -> makeMessageDTO(message, parameters.getSenderUserId()))
                .collect(Collectors.toList());
    }

    public Optional<MessageDTO> get(long messageId) {
        return messageService.get(messageId)
                .map(message -> makeMessageDTO(message, null));
    }

    private MessageDTO makeMessageDTO(Message message, Long currentUserId) {
        MessageDTO result = new MessageDTO();

        String direction = Objects.equals(message.getSenderUserId(), currentUserId)
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
