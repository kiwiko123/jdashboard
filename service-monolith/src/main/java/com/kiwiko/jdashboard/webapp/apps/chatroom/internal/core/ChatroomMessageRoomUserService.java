package com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessageRoomUser;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.data.fetchers.ChatroomMessageRoomUserEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.mappers.ChatroomMessageRoomUserMapper;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatroomMessageRoomUserService {

    @Inject private ChatroomMessageRoomUserEntityDataFetcher chatroomMessageRoomUserEntityDataFetcher;
    @Inject private ChatroomMessageRoomUserMapper chatroomMessageRoomUserMapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;
    @Inject private TransactionProvider transactionProvider;

    public Set<ChatroomMessageRoomUser> getByRoomIds(Collection<Long> chatroomMessageRoomIds) {
        return transactionProvider.readOnly(() -> chatroomMessageRoomUserEntityDataFetcher.getByRooms(chatroomMessageRoomIds).stream()
                .map(chatroomMessageRoomUserMapper::toDto)
                .collect(Collectors.toSet()));
    }

    public ChatroomMessageRoomUser create(ChatroomMessageRoomUser chatroomMessageRoomUser) {
        return crudExecutor.create(chatroomMessageRoomUser, chatroomMessageRoomUserEntityDataFetcher, chatroomMessageRoomUserMapper);
    }
}
