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
        Predicate fromSenderUserId = builder.equal(senderUserIdField, senderUserId);

        Expression<Long> recipientUserIdField = root.get("recipientUserId");
        Predicate toRecipientUserIds = recipientUserIdField.in(recipientUserIds);

        Predicate toSenderUserId = builder.equal(recipientUserIdField, senderUserId);
        Predicate fromRecipientsUserIds = senderUserIdField.in(recipientUserIds);

        Expression<Integer> messageTypeField = root.get("messageType");
        Predicate isMessageType = builder.equal(messageTypeField, messageType);

        Predicate isOutbound = builder.and(fromSenderUserId, toRecipientUserIds);
        Predicate isInbound = builder.and(toSenderUserId, fromRecipientsUserIds);
        Predicate isRelatedToUser = builder.or(isOutbound, isInbound);

        Predicate allCriteria = builder.and(isRelatedToUser, isMessageType);
        query.where(allCriteria);
        return getResultList(query);
    }

    public List<MessageEntity> getRelatedToUser(long userId) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<MessageEntity> query = builder.createQuery(entityType);
        Root<MessageEntity> root = query.from(entityType);

        Expression<Long> senderUserIdField = root.get("senderUserId");
        Predicate fromUser = builder.equal(senderUserIdField, userId);

        Expression<Long> recipientUserIdField = root.get("recipientUserId");
        Predicate toUser = builder.equal(recipientUserIdField, userId);

        Predicate isRelatedToUser = builder.or(fromUser, toUser);
        query.where(isRelatedToUser);
        return getResultList(query);
    }
}
