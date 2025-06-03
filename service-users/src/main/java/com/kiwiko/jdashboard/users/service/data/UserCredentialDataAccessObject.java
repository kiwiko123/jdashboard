package com.kiwiko.jdashboard.users.service.data;

import com.kiwiko.jdashboard.framework.dataaccess.AbstractJpaDataAccessObject;
import com.kiwiko.jdashboard.users.service.data.entity.UserCredentialEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class UserCredentialDataAccessObject extends AbstractJpaDataAccessObject<Long, UserCredentialEntity> {

    public List<UserCredentialEntity> getForUser(long userId) {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<UserCredentialEntity> query = criteriaBuilder.createQuery(entityType);
        Root<UserCredentialEntity> root = query.from(entityType);

        Predicate hasUserId = criteriaBuilder.equal(root.get("userId"), userId);

        query.select(root).where(hasUserId);

        return createQuery(query).getResultList();
    }
}
