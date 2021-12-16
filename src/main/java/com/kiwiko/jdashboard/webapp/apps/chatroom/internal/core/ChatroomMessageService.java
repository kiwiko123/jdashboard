package com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessage;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.data.fetchers.ChatroomMessageEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.mappers.ChatroomMessageMapper;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.parameters.GetMessagesForRoomParameters;
import com.kiwiko.jdashboard.webapp.framework.persistence.transactions.api.interfaces.TransactionProvider;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class ChatroomMessageService {

    @Inject private ChatroomMessageEntityDataFetcher chatroomMessageEntityDataFetcher;
    @Inject private ChatroomMessageMapper chatroomMessageMapper;
    @Inject private TransactionProvider transactionProvider;

    public List<ChatroomMessage> getMessagesByRoomId(GetMessagesForRoomParameters parameters) {
        return transactionProvider.readOnly(() -> chatroomMessageEntityDataFetcher.getMessagesForRoom(parameters).stream()
                .map(chatroomMessageMapper::toDto)
                .collect(Collectors.toList()));
    }
}
