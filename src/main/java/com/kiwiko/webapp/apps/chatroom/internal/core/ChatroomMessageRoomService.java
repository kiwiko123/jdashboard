package com.kiwiko.webapp.apps.chatroom.internal.core;

import com.kiwiko.webapp.apps.chatroom.api.dto.ChatroomMessageRoom;
import com.kiwiko.webapp.apps.chatroom.internal.core.data.fetchers.ChatroomMessageRoomEntityDataFetcher;
import com.kiwiko.webapp.apps.chatroom.internal.core.mappers.ChatroomMessageRoomMapper;
import com.kiwiko.webapp.mvc.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatroomMessageRoomService {

    @Inject private ChatroomMessageRoomEntityDataFetcher chatroomMessageRoomEntityDataFetcher;
    @Inject private ChatroomMessageRoomMapper chatroomMessageRoomMapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;
    @Inject private TransactionProvider transactionProvider;

    public ChatroomMessageRoom create(ChatroomMessageRoom room) {
        return crudExecutor.create(room, chatroomMessageRoomEntityDataFetcher, chatroomMessageRoomMapper);
    }

    public Set<ChatroomMessageRoom> getRoomsForUser(long userId) {
        return transactionProvider.readOnly(() -> chatroomMessageRoomEntityDataFetcher.getRoomsForUser(userId).stream()
                .map(chatroomMessageRoomMapper::toDto)
                .collect(Collectors.toSet()));
    }
}
