package com.kiwiko.jdashboard.featureflags.service.api.interfaces;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagRule;
import com.kiwiko.jdashboard.library.persistence.interfaces.api.CreateReadUpdateDeleteAPI;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface FeatureFlagRuleService extends CreateReadUpdateDeleteAPI<FeatureFlagRule> {
    Optional<FeatureFlagRule> findPublic(long featureFlagId);

    Optional<FeatureFlagRule> findForUser(long featureFlagId, long userId);

    Set<FeatureFlagRule> findByFeatureFlagIds(Collection<Long> featureFlagIds);

    Set<String> getEnabledScopesForRules(Collection<FeatureFlagRule> rules);
}
