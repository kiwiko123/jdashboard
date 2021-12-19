package com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.constants.ChatroomMessageStatus;
import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessage;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.data.fetchers.ChatroomMessageEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.mappers.ChatroomMessageMapper;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.parameters.GetMessagesForRoomParameters;
import com.kiwiko.jdashboard.webapp.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class ChatroomMessageService {

    @Inject private ChatroomMessageEntityDataFetcher chatroomMessageEntityDataFetcher;
    @Inject private ChatroomMessageMapper chatroomMessageMapper;
    @Inject private TransactionProvider transactionProvider;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;

    public List<ChatroomMessage> getMessagesByRoomId(GetMessagesForRoomParameters parameters) {
        return transactionProvider.readOnly(() -> chatroomMessageEntityDataFetcher.getMessagesForRoom(parameters).stream()
                .map(chatroomMessageMapper::toDto)
                .collect(Collectors.toList()));
    }

    public ChatroomMessage create(ChatroomMessage message) {
        message.setSentDate(Instant.now());
        message.setMessageStatus(ChatroomMessageStatus.SENT);
        return crudExecutor.create(message, chatroomMessageEntityDataFetcher, chatroomMessageMapper);
    }
}
