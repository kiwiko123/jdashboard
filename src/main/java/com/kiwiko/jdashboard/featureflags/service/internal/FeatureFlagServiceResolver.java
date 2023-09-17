package com.kiwiko.jdashboard.featureflags.service.internal;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagRule;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagUserScope;
import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedFeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedPublicFeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedUserFeatureFlag;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagResolver;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagRuleService;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagService;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagStatus;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class FeatureFlagServiceResolver implements FeatureFlagResolver {

    @Inject private FeatureFlagService featureFlagService;
    @Inject private FeatureFlagRuleService featureFlagRuleEntityService;
    @Inject private FeatureFlagStateMapper featureFlagStateMapper;

    @Override
    public Optional<? extends ResolvedFeatureFlag> resolve(String featureFlagName) {
        return resolveForPublic(featureFlagName);
    }

    @Override
    public Optional<? extends ResolvedFeatureFlag> resolve(String featureFlagName, @Nullable Long userId) {
        if (userId == null) {
            return resolveForPublic(featureFlagName);
        }

        Optional<ResolvedUserFeatureFlag> resolvedUserFeatureFlag = resolveForUser(featureFlagName, userId);
        if (resolvedUserFeatureFlag.isPresent()) {
            return resolvedUserFeatureFlag;
        }

        return resolveForPublic(featureFlagName);
    }

    @Override
    public Optional<ResolvedPublicFeatureFlag> resolveForPublic(String featureFlagName) {
        FeatureFlag featureFlag = featureFlagService.getByName(featureFlagName).orElse(null);
        if (featureFlag == null) {
            return Optional.empty();
        }

        FeatureFlagRule featureFlagRule = featureFlagRuleEntityService.findPublic(featureFlag.getId()).orElse(null);
        if (featureFlagRule == null) {
            return Optional.empty();
        }

        return Optional.of(featureFlagStateMapper.mapPublicFlag(featureFlag, featureFlagRule));
    }

    @Override
    public Optional<ResolvedUserFeatureFlag> resolveForUser(String featureFlagName, long userId) {
        FeatureFlag featureFlag = featureFlagService.getByName(featureFlagName).orElse(null);
        if (featureFlag == null) {
            return Optional.empty();
        }

        FeatureFlagRule userContext = featureFlagRuleEntityService.findForUser(featureFlag.getId(), userId)
                .orElse(null);
        FeatureFlagRule publicContext = featureFlagRuleEntityService.findPublic(featureFlag.getId())
                .orElse(null);

        return Stream.of(userContext, publicContext)
                .filter(Objects::nonNull)
                .peek(context -> context.setUserId(userId))
                .map(context -> featureFlagStateMapper.mapUserFlag(featureFlag, context))
                .findFirst();
    }

    @Override
    public boolean resolveLegacy(String name, @Nullable Long userId) {
        Optional<FeatureFlag> flag = Optional.empty();

        // If a user is specified, first try searching for an individual flag tied to them.
        if (userId != null) {
            flag = featureFlagService.getForUser(name, userId);
        }

        if (flag.isEmpty()) {
            // Either a user was not provided, or no individual flag was found for them.
            // Search for a public flag.
            flag = featureFlagService.getByName(name)
                    .filter(this::isPublic);
        }

        return flag.map(FeatureFlag::getStatus)
                .map(FeatureFlagStatus.ENABLED.getId()::equalsIgnoreCase)
                .orElse(false);
    }

    @Override
    public boolean resolveLegacy(String name) {
        return this.resolveLegacy(name, null);
    }

    private boolean isPublic(FeatureFlag flag) {
        return FeatureFlagUserScope.PUBLIC.getId().equalsIgnoreCase(flag.getUserScope());
    }
}
