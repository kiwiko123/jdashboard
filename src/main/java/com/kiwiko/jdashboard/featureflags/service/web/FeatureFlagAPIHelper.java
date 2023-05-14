package com.kiwiko.jdashboard.featureflags.service.web;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagState;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagStatus;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagUserScope;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.FeatureFlagClient;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.ResolvedFeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.UpdateFeatureFlagInput;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagService;
import com.kiwiko.jdashboard.featureflags.service.web.responses.FeatureFlagListItem;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.services.users.api.dto.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

class FeatureFlagAPIHelper {

    @Inject private FeatureFlagClient featureFlagClient;
    @Inject private FeatureFlagService featureFlagService;

    public List<FeatureFlagListItem> getListItems() {
        Set<FeatureFlag> flags = featureFlagService.getAll();
        List<FeatureFlagListItem> listItems = new ArrayList<>();

        for (FeatureFlag flag : flags) {
            FeatureFlagListItem listItem = makeListItem(flag, null);
            listItems.add(listItem);
        }

        return listItems;
    }

    public Optional<ResolvedFeatureFlag> toggleStatus(UpdateFeatureFlagInput input) {
        String featureFlagName = input.getFeatureFlagName();
        FeatureFlagState currentState = featureFlagClient.resolve(featureFlagName).get().orElse(null);
        if (currentState == null) {
            return Optional.empty();
        }

        ResolvedFeatureFlag resolvedFeatureFlag;

        if (FeatureFlagUserScope.isIndividual(input.getUserScope())) {
            Long userId = input.getUserId();
            Objects.requireNonNull(userId, "A user ID is required to toggle an individually-scoped feature flag");

            resolvedFeatureFlag = currentState.isEnabled()
                    ? featureFlagClient.turnOffForUser(featureFlagName, userId)
                    : featureFlagClient.turnOnForUser(featureFlagName, userId);
        } else {
            resolvedFeatureFlag = currentState.isEnabled()
                    ? featureFlagClient.turnOffForPublic(featureFlagName)
                    : featureFlagClient.turnOnForPublic(featureFlagName);
        }

        return Optional.of(resolvedFeatureFlag);
    }

    @Deprecated
    public FeatureFlag toggleStatus(long featureFlagId) {
        FeatureFlag flag = featureFlagService.get(featureFlagId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No flag found with ID %d", featureFlagId)));
        flag.setStatus(FeatureFlagStatus.opposite(flag.getStatus()));
        return featureFlagService.update(flag);
    }

    private FeatureFlagListItem makeListItem(FeatureFlag flag, User user) {
        FeatureFlagListItem listItem = new FeatureFlagListItem();
        listItem.setFeatureFlag(flag);
        listItem.setUser(user);

        return listItem;
    }
}
