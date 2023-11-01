package com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core;

import com.kiwiko.jdashboard.webapp.apps.chatroom.api.dto.ChatroomMessageRoom;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.data.fetchers.ChatroomMessageRoomEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.exceptions.ChatroomRuntimeException;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.mappers.ChatroomMessageRoomMapper;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.dto.UniversalUniqueIdentifier;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.UniqueIdentifierService;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.parameters.CreateUuidParameters;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ChatroomMessageRoomService {

    @Inject private ChatroomMessageRoomEntityDataFetcher chatroomMessageRoomEntityDataFetcher;
    @Inject private ChatroomMessageRoomMapper chatroomMessageRoomMapper;
    @Inject private UniqueIdentifierService uniqueIdentifierService;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;
    @Inject private TransactionProvider transactionProvider;

    public ChatroomMessageRoom create(ChatroomMessageRoom room) {
        ChatroomMessageRoom chatroomMessageRoom = crudExecutor.create(room, chatroomMessageRoomEntityDataFetcher, chatroomMessageRoomMapper);
        CreateUuidParameters createUuidParameters = new CreateUuidParameters(String.format("%s:%d", ChatroomMessageRoom.UUID_REFERENCE_KEY_PREFIX, chatroomMessageRoom.getId()));
        uniqueIdentifierService.create(createUuidParameters);

        return chatroomMessageRoom;
    }

    public String getRoomUuid(long chatroomMessageRoomId) throws ChatroomRuntimeException {
        return uniqueIdentifierService.getByReferenceKey(String.format("%s:%d", ChatroomMessageRoom.UUID_REFERENCE_KEY_PREFIX, chatroomMessageRoomId))
                .map(UniversalUniqueIdentifier::getUuid)
                .orElseThrow(() -> new ChatroomRuntimeException(String.format("Unable to find UUID for chatroom message room %d", chatroomMessageRoomId)));
    }

    public Optional<ChatroomMessageRoom> getByRoomUuid(String roomUuid) {
        return uniqueIdentifierService.getByUuid(roomUuid)
                .map(UniversalUniqueIdentifier::getReferenceKey)
                .map(this::parseRoomIdFromReferenceKey)
                .flatMap(id -> crudExecutor.get(id, chatroomMessageRoomEntityDataFetcher, chatroomMessageRoomMapper));
    }

    public Set<ChatroomMessageRoom> getRoomsForUsers(Collection<Long> userIds) {
        return transactionProvider.readOnly(() -> chatroomMessageRoomEntityDataFetcher.getRoomsForUsers(userIds).stream()
                .map(chatroomMessageRoomMapper::toDto)
                .collect(Collectors.toSet()));
    }

    @Nullable
    private Long parseRoomIdFromReferenceKey(String referenceKey) {
        Pattern pattern = Pattern.compile("^chatroom_message_rooms:(?<roomId>\\d+)$");
        Matcher matcher = pattern.matcher(referenceKey);
        if (!matcher.find()) {
            return null;
        }

        String id = matcher.group("roomId");
        Objects.requireNonNull(id, String.format("No room ID found in reference key %s", referenceKey));

        return Long.parseLong(id);
    }
}
