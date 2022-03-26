package com.kiwiko.jdashboard.services.usercredentials.internal.data;

import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.QueryUserCredentialsInput;
import com.kiwiko.jdashboard.tools.dataaccess.impl.JpaDataAccessObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserCredentialEntityDataAccessObject extends JpaDataAccessObject<UserCredentialEntity> {

    public List<UserCredentialEntity> query(QueryUserCredentialsInput input) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<UserCredentialEntity> query = builder.createQuery(entityType);
        Root<UserCredentialEntity> root = query.from(entityType);
        Predicate allCriteria = builder.and();

        if (input.getUserIds() != null) {
            Expression<Long> userId = root.get("userId");
            Predicate hasUserId = userId.in(input.getUserIds());
            allCriteria = builder.and(allCriteria, hasUserId);
        }

        if (input.getCredentialTypes() != null) {
            Expression<String> credentialType = root.get("credentialType");
            Predicate hasCredentialType = credentialType.in(input.getCredentialTypes());
            allCriteria = builder.and(allCriteria, hasCredentialType);
        }

        if (input.getIsRemoved() != null) {
            Expression<Boolean> isRemoved = root.get("isRemoved");
            Predicate isRemovedPredicate = input.getIsRemoved() ? builder.isTrue(isRemoved) : builder.isFalse(isRemoved);
            allCriteria = builder.and(allCriteria, isRemovedPredicate);
        }

        query.where(allCriteria);
        return createQuery(query).getResultList();
    }
}
