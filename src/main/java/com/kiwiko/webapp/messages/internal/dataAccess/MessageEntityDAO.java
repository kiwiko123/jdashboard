package com.kiwiko.webapp.messages.internal.dataAccess;

import com.kiwiko.library.persistence.dataAccess.api.AuditableEntityManagerDAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public class MessageEntityDAO extends AuditableEntityManagerDAO<MessageEntity> {

    @Override
    protected Class<MessageEntity> getEntityType() {
        return MessageEntity.class;
    }

    public List<MessageEntity> getBetween(long senderUserId, Collection<Long> recipientUserIds) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<MessageEntity> query = builder.createQuery(entityType);
        Root<MessageEntity> root = query.from(entityType);

        Expression<Long> senderUserIdField = root.get("senderUserId");
        Predicate fromSenderUserId = builder.equal(senderUserIdField, senderUserIdField);

        Expression<Long> recipientUserIdField = root.get("recipientUserId");
        Predicate toRecipientUserIds = recipientUserIdField.in(recipientUserIds);

        Predicate allCriteria = builder.and(fromSenderUserId, toRecipientUserIds);
        query.where(allCriteria);
        return getResultList(query);
    }
}
