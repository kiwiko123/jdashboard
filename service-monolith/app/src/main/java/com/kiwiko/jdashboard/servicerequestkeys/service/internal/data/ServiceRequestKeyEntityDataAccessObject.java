package com.kiwiko.jdashboard.servicerequestkeys.service.internal.data;

import com.kiwiko.jdashboard.tools.dataaccess.impl.JpaDataAccessObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ServiceRequestKeyEntityDataAccessObject extends JpaDataAccessObject<ServiceRequestKeyEntity> {

    public Optional<ServiceRequestKeyEntity> getByToken(String token) {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<ServiceRequestKeyEntity> criteriaQuery = criteriaBuilder.createQuery(entityType);
        Root<ServiceRequestKeyEntity> root = criteriaQuery.from(entityType);

        Expression<String> requestTokenField = root.get("requestToken");
        Predicate matchesToken = criteriaBuilder.equal(requestTokenField, token);

        criteriaQuery.where(matchesToken);
        return getSingleResult(criteriaQuery);
    }

    public List<ServiceRequestKeyEntity> getForUsers(Collection<Long> userIds) {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<ServiceRequestKeyEntity> criteriaQuery = criteriaBuilder.createQuery(entityType);
        Root<ServiceRequestKeyEntity> root = criteriaQuery.from(entityType);

        Expression<Long> userId = root.get("createdByUserId");
        Predicate wasCreatedByUser = userId.in(userIds);

        Expression<Instant> expirationDate = root.get("expirationDate");
        Predicate isActive = criteriaBuilder.greaterThan(expirationDate, Instant.now());

        criteriaQuery.where(wasCreatedByUser, isActive);
        return createQuery(criteriaQuery).getResultList();
    }
}
