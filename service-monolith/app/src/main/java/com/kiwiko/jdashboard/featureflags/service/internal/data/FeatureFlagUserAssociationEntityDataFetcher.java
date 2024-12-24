package com.kiwiko.jdashboard.featureflags.service.internal.data;

import com.kiwiko.jdashboard.tools.dataaccess.impl.JpaDataAccessObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class FeatureFlagUserAssociationEntityDataFetcher extends JpaDataAccessObject<FeatureFlagUserAssociationEntity> {

    public Optional<FeatureFlagUserAssociationEntity> getByFeatureFlagIdForUser(long featureFlagId, long userId) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<FeatureFlagUserAssociationEntity> query = builder.createQuery(entityType);
        Root<FeatureFlagUserAssociationEntity> root = query.from(entityType);

        Predicate matchesFlag = builder.equal(root.get("featureFlagId"), featureFlagId);
        Predicate forUser = builder.equal(root.get("userId"), userId);

        query.where(matchesFlag, forUser);
        return getSingleResult(query);
    }
}
