package com.kiwiko.jdashboard.featureflags.service.internal.data;

import com.kiwiko.jdashboard.tools.dataaccess.impl.JpaDataAccessObject;

import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeatureFlagContextEntityDataAccessObject extends JpaDataAccessObject<FeatureFlagRuleEntity> {

    public Optional<FeatureFlagRuleEntity> findMostRecentActive(
            long featureFlagId, String scope, @Nullable Long userId) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<FeatureFlagRuleEntity> query = builder.createQuery(entityType);
        Root<FeatureFlagRuleEntity> root = query.from(entityType);

        List<Predicate> predicates = new ArrayList<>();

        Predicate hasFeatureFlagId = builder.equal(root.get("featureFlagId"), featureFlagId);
        predicates.add(hasFeatureFlagId);

        Predicate hasScope = builder.equal(root.get("scope"), scope);
        predicates.add(hasScope);

        if (userId != null) {
            Predicate hasUserId = builder.equal(root.get("userId"), userId);
            predicates.add(hasUserId);
        }

        Predicate isActive = builder.isNull(root.get("endDate"));
        predicates.add(isActive);

        query.where(predicates.toArray(new Predicate[0]))
                .orderBy(builder.desc(root.get("startDate")));

        return getSingleResult(query);
    }
}
