package com.kiwiko.webapp.apps.chatroom.internal.core.data.fetchers;

import com.kiwiko.webapp.apps.chatroom.internal.core.data.entities.ChatroomMessageRoomUserEntity;
import com.kiwiko.webapp.persistence.data.fetchers.api.interfaces.EntityDataFetcher;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatroomMessageRoomUserEntityDataFetcher extends EntityDataFetcher<ChatroomMessageRoomUserEntity> {

    public Set<ChatroomMessageRoomUserEntity> getByRooms(Collection<Long> chatroomMessageRoomIds) {
        if (chatroomMessageRoomIds.isEmpty()) {
            return Collections.emptySet();
        }

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<ChatroomMessageRoomUserEntity> query = builder.createQuery(entityType);
        Root<ChatroomMessageRoomUserEntity> root = query.from(entityType);

        Expression<Long> chatroomMessageRoomId = root.get("chatroomMessageRoomId");
        Predicate matchesRoom = chatroomMessageRoomId.in(chatroomMessageRoomIds);

        query.where(matchesRoom);
        return createQuery(query).getResultStream().collect(Collectors.toSet());
    }
}
