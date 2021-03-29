package com.kiwiko.webapp.flags.features.internal.database;

import com.kiwiko.webapp.flags.features.dto.FeatureFlagUserScope;
import com.kiwiko.webapp.mvc.persistence.impl.VersionedEntityManagerDAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class FeatureFlagEntityDAO extends VersionedEntityManagerDAO<FeatureFlagEntity> {

    @Override
    protected Class<FeatureFlagEntity> getEntityType() {
        return FeatureFlagEntity.class;
    }

    public Optional<FeatureFlagEntity> getByName(String name) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<FeatureFlagEntity> query = builder.createQuery(entityType);
        Root<FeatureFlagEntity> root = query.from(entityType);

        Expression<String> nameField = root.get("name");
        Expression<String> lowerCaseNameField = builder.lower(nameField);
        Expression<Boolean> isRemovedField = root.get("isRemoved");
        Predicate matchesName = builder.equal(lowerCaseNameField, name.toLowerCase());
        Predicate isActive = builder.isFalse(isRemovedField);

        query.where(matchesName, isActive);
        return getSingleResult(query);
    }

    public Optional<FeatureFlagEntity> getForUser(String name, long userId) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<FeatureFlagEntity> query = builder.createQuery(entityType);
        Root<FeatureFlagEntity> root = query.from(entityType);

        Expression<String> nameField = root.get("name");
        Expression<String> lowerCaseNameField = builder.lower(nameField);
        Expression<String> userScopeField = root.get("userScope");
        Expression<Long> userIdField = root.get("userId");
        Expression<Boolean> isRemovedField = root.get("isRemoved");
        Predicate matchesName = builder.equal(lowerCaseNameField, name.toLowerCase());
        Predicate hasIndividualScope = builder.equal(userScopeField, FeatureFlagUserScope.INDIVIDUAL.name());
        Predicate forUserId = builder.equal(userIdField, userIdField);
        Predicate isActive = builder.isFalse(isRemovedField);

        query.where(matchesName, hasIndividualScope, forUserId, isActive);
        return getSingleResult(query);
    }

    public List<FeatureFlagEntity> getAll() {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<FeatureFlagEntity> query = builder.createQuery(entityType);
        Root<FeatureFlagEntity> root = query.from(entityType);

        Expression<Boolean> isRemovedField = root.get("isRemoved");
        Predicate isActive = builder.isFalse(isRemovedField);

        query.where(isActive);
        return getResultList(query);
    }
}
