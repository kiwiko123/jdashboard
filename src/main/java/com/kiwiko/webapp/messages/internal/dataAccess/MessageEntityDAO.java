package com.kiwiko.webapp.messages.internal.dataAccess;

import com.kiwiko.webapp.messages.api.queries.data.GetBetweenParameters;
import com.kiwiko.webapp.mvc.persistence.impl.VersionedEntityManagerDAO;
import com.kiwiko.webapp.messages.data.MessageType;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.time.Instant;
import java.util.List;

public class MessageEntityDAO extends VersionedEntityManagerDAO<MessageEntity> {

    @Override
    protected Class<MessageEntity> getEntityType() {
        return MessageEntity.class;
    }

    @SuppressWarnings("unchecked")
    public List<MessageEntity> getBetween(
            GetBetweenParameters parameters,
            MessageType messageType) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<MessageEntity> query = builder.createQuery(entityType);
        Root<MessageEntity> root = query.from(entityType);

        Expression<Long> senderUserIdField = root.get("senderUserId");
        Expression<Long> recipientUserIdField = root.get("recipientUserId");

        Predicate fromSenderUserId = builder.equal(senderUserIdField, parameters.getSenderUserId());
        Predicate toRecipientUserIds = recipientUserIdField.in(parameters.getRecipientUserIds());
        Predicate isOutbound = builder.and(fromSenderUserId, toRecipientUserIds);

        Predicate toSenderUserId = builder.equal(recipientUserIdField, parameters.getSenderUserId());
        Predicate fromRecipientsUserIds = senderUserIdField.in(parameters.getRecipientUserIds());
        Predicate isInbound = builder.and(toSenderUserId, fromRecipientsUserIds);

        Predicate isRelatedToUser = builder.or(isOutbound, isInbound);

        Expression<Integer> messageTypeField = root.get("messageType");
        Predicate isMessageType = builder.equal(messageTypeField, messageType);

        Predicate allCriteria = builder.and(isRelatedToUser, isMessageType);

        Expression<Instant> sentDateField = root.get("sentDate");
        if (parameters.getMinimumSentDate().isPresent()) {
            Predicate isSentSince = builder.greaterThanOrEqualTo(sentDateField, parameters.getMinimumSentDate().get());
            allCriteria = builder.and(allCriteria, isSentSince);
        }

        Order descendingSentDate = builder.desc(sentDateField);

        query.where(allCriteria);
        query.orderBy(descendingSentDate);

        Query primedQuery = createQuery(query);
        parameters.getMaxResults().ifPresent(primedQuery::setMaxResults);
        return (List<MessageEntity>) primedQuery.getResultList();
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
