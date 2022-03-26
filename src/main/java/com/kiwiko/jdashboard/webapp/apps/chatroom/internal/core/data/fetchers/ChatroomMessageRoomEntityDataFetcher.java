package com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.data.fetchers;

import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.data.entities.ChatroomMessageRoomEntity;
import com.kiwiko.jdashboard.tools.dataaccess.impl.JpaDataAccessObject;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatroomMessageRoomEntityDataFetcher extends JpaDataAccessObject<ChatroomMessageRoomEntity> {

    public Set<ChatroomMessageRoomEntity> getRoomsForUsers(Collection<Long> userIds) {
        if (userIds.isEmpty()) {
            return Collections.emptySet();
        }

        String queryString = new StringBuilder()
                .append("SELECT cmr.* FROM chatroom_message_rooms cmr ")
                .append("JOIN chatroom_message_room_users cmru ON cmr.id = cmru.chatroom_message_room_id ")
                .append("WHERE cmru.user_id IN (:userIds) AND cmr.is_removed = false AND cmru.is_removed = false")
                .append(";")
                .toString();

        @SuppressWarnings("unchecked")
        List<ChatroomMessageRoomEntity> result = (List<ChatroomMessageRoomEntity>) createNativeQuery(queryString)
                .setParameter("userIds", userIds)
                .getResultList();
        return new HashSet<>(result);
    }
}
