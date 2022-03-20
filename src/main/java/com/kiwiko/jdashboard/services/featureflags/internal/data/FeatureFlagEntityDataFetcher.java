package com.kiwiko.jdashboard.services.featureflags.internal.data;

import com.kiwiko.jdashboard.services.featureflags.api.dto.FeatureFlagUserScope;
import com.kiwiko.jdashboard.webapp.persistence.data.access.api.interfaces.DataAccessObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class FeatureFlagEntityDataFetcher extends DataAccessObject<FeatureFlagEntity> {

    public Optional<FeatureFlagEntity> getByName(String name) {
        CriteriaBuilder builder = getCriteriaBuilder();
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
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<FeatureFlagEntity> query = builder.createQuery(entityType);
        Root<FeatureFlagEntity> root = query.from(entityType);

        Expression<String> nameField = builder.lower(root.get("name"));
        Expression<String> userScopeField = root.get("userScope");
        Expression<Long> userIdField = root.get("userId");
        Expression<Boolean> isRemovedField = root.get("isRemoved");
        Predicate matchesName = builder.equal(nameField, name.toLowerCase());
        Predicate hasIndividualScope = builder.equal(userScopeField, FeatureFlagUserScope.INDIVIDUAL.getId());
        Predicate forUserId = builder.equal(userIdField, userId);
        Predicate isActive = builder.isFalse(isRemovedField);

        query.where(matchesName, hasIndividualScope, forUserId, isActive);
        return getSingleResult(query);
    }

    public List<FeatureFlagEntity> getAll() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<FeatureFlagEntity> query = builder.createQuery(entityType);
        Root<FeatureFlagEntity> root = query.from(entityType);

        Expression<Boolean> isRemovedField = root.get("isRemoved");
        Predicate isActive = builder.isFalse(isRemovedField);

        query.where(isActive);
        return createQuery(query).getResultList();
    }
}
