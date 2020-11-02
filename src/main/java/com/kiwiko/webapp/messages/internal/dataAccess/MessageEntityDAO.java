package com.kiwiko.webapp.messages.internal.dataAccess;

import com.kiwiko.webapp.mvc.persistence.impl.VersionedEntityManagerDAO;
import com.kiwiko.webapp.messages.data.MessageType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public class MessageEntityDAO extends VersionedEntityManagerDAO<MessageEntity> {

    @Override
    protected Class<MessageEntity> getEntityType() {
        return MessageEntity.class;
    }

    public List<MessageEntity> getBetween(
            long senderUserId,
            Collection<Long> recipientUserIds,
            MessageType messageType) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<MessageEntity> query = builder.createQuery(entityType);
        Root<MessageEntity> root = query.from(entityType);

        Expression<Long> senderUserIdField = root.get("senderUserId");
        Predicate fromSenderUserId = builder.equal(senderUserIdField, senderUserIdField);

        Expression<Long> recipientUserIdField = root.get("recipientUserId");
        Predicate toRecipientUserIds = recipientUserIdField.in(recipientUserIds);

        Expression<Integer> messageTypeField = root.get("messageType");
        Predicate isMessageType = builder.equal(messageTypeField, messageType);

        Predicate allCriteria = builder.and(fromSenderUserId, toRecipientUserIds, isMessageType);
        query.where(allCriteria);
        return getResultList(query);
    }
}
