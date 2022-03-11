package com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.data.fetchers;

import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.data.entities.ChatroomMessageEntity;
import com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.parameters.GetMessagesForRoomParameters;
import com.kiwiko.jdashboard.webapp.persistence.data.access.api.interfaces.DataAccessObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.List;

public class ChatroomMessageEntityDataFetcher extends DataAccessObject<ChatroomMessageEntity> {

    public List<ChatroomMessageEntity> getMessagesForRoom(GetMessagesForRoomParameters parameters) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<ChatroomMessageEntity> query = builder.createQuery(entityType);
        Root<ChatroomMessageEntity> root = query.from(entityType);

        Expression<Long> roomId = root.get("chatroomMessageRoomId");
        Predicate allCriteria = builder.equal(roomId, parameters.getChatroomMessageRoomId());

        if (!parameters.getIncludeRemovedMessages()) {
            Expression<Boolean> isRemoved = root.get("isRemoved");
            Predicate isActive = builder.isFalse(isRemoved);
            allCriteria = builder.and(allCriteria, isActive);
        }

        Expression<Instant> sentDate = root.get("sentDate");
        Order descendingSentDate = builder.asc(sentDate);

        query.where(allCriteria);
        query.orderBy(descendingSentDate);

        return createQuery(query)
                .setMaxResults(parameters.getMaxMessagesToFetch())
                .getResultList();
    }
}
