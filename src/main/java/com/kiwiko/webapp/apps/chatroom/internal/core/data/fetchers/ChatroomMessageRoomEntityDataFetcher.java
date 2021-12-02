package com.kiwiko.webapp.apps.chatroom.internal.core.data.fetchers;

import com.kiwiko.webapp.apps.chatroom.internal.core.data.entities.ChatroomMessageRoomEntity;
import com.kiwiko.webapp.persistence.data.fetchers.api.interfaces.EntityDataFetcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatroomMessageRoomEntityDataFetcher extends EntityDataFetcher<ChatroomMessageRoomEntity> {

    public Set<ChatroomMessageRoomEntity> getRoomsForUser(long userId) {
        String queryString = new StringBuilder()
                .append("SELECT cmr.* FROM chatroom_message_rooms cmr ")
                .append("JOIN chatroom_message_room_users cmru ON cmr.id = cmru.chatroom_message_room_id ")
                .append("WHERE cmru.user_id = :userId AND cmr.is_removed = false AND cmru.is_removed = false")
                .append(";")
                .toString();

        @SuppressWarnings("unchecked")
        List<ChatroomMessageRoomEntity> result = (List<ChatroomMessageRoomEntity>) createNativeQuery(queryString)
                .setParameter("userId", userId)
                .getResultList();
        return new HashSet<>(result);
    }
}
