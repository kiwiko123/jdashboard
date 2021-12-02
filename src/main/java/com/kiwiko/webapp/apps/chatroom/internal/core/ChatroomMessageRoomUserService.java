package com.kiwiko.webapp.apps.chatroom.internal.core;

import com.kiwiko.webapp.apps.chatroom.api.dto.ChatroomMessageRoomUser;
import com.kiwiko.webapp.apps.chatroom.internal.core.data.fetchers.ChatroomMessageRoomUserEntityDataFetcher;
import com.kiwiko.webapp.apps.chatroom.internal.core.mappers.ChatroomMessageRoomUserMapper;
import com.kiwiko.webapp.mvc.persistence.transactions.api.interfaces.TransactionProvider;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatroomMessageRoomUserService {

    @Inject private ChatroomMessageRoomUserEntityDataFetcher chatroomMessageRoomUserEntityDataFetcher;
    @Inject private ChatroomMessageRoomUserMapper chatroomMessageRoomUserMapper;
    @Inject private TransactionProvider transactionProvider;

    public Set<ChatroomMessageRoomUser> getByRoomIds(Collection<Long> chatroomMessageRoomIds) {
        return transactionProvider.readOnly(() -> chatroomMessageRoomUserEntityDataFetcher.getByRooms(chatroomMessageRoomIds).stream()
                .map(chatroomMessageRoomUserMapper::toDto)
                .collect(Collectors.toSet()));
    }
}
