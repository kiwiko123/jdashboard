package com.kiwiko.jdashboard.featureflags.service.web;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagRule;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagStatus;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagUserScope;
import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedFeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.FeatureFlagClient;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.UpdateFeatureFlagInput;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagRuleService;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagService;
import com.kiwiko.jdashboard.featureflags.service.web.responses.FeatureFlagListItem;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.featureflags.service.web.responses.FeatureFlagListItemRule;
import com.kiwiko.jdashboard.featureflags.service.web.responses.FeatureFlagListItemV2;
import com.kiwiko.jdashboard.featureflags.service.web.responses.GetFeatureFlagListResponse;
import com.kiwiko.jdashboard.featureflags.service.web.responses.ModifyFeatureFlagData;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;
import com.kiwiko.jdashboard.users.service.api.dto.User;

import javax.inject.Inject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FeatureFlagAPIHelper {

    @Inject private FeatureFlagClient featureFlagClient;
    @Inject private FeatureFlagService featureFlagService;
    @Inject private FeatureFlagRuleService featureFlagRuleService;

    public GetFeatureFlagListResponse getFeatureFlagList(long currentUserId) {
        Set<FeatureFlag> allFeatureFlags = featureFlagService.getAll();
        Set<Long> allFeatureFlagIds = allFeatureFlags.stream()
                .map(FeatureFlag::getId)
                .collect(Collectors.toUnmodifiableSet());

        Map<Long, Set<FeatureFlagRule>> rulesByFeatureFlagId = featureFlagRuleService.findByFeatureFlagIds(allFeatureFlagIds).stream()
                .collect(Collectors.groupingBy(FeatureFlagRule::getFeatureFlagId, Collectors.toUnmodifiableSet()));

        List<FeatureFlagListItemV2> listItems = allFeatureFlags.stream()
                .map(featureFlag -> {
                    Set<FeatureFlagRule> rules = rulesByFeatureFlagId.getOrDefault(featureFlag.getId(), Collections.emptySet());
                    return mapToListItem(featureFlag, rules, currentUserId);
                })
                .toList();

        GetFeatureFlagListResponse response = new GetFeatureFlagListResponse();
        response.setListItems(listItems);
        response.setCurrentUserId(currentUserId);
        return response;
    }

    private FeatureFlagListItemV2 mapToListItem(FeatureFlag featureFlag, Collection<FeatureFlagRule> rules, long currentUserId) {
        List<FeatureFlagListItemRule> listItemRules = rules.stream()
                .map(this::mapToListItemRule)
                .toList();

        Instant earliestDate = listItemRules.stream()
                .map(FeatureFlagListItemRule::getStartDate)
                .min(Comparator.naturalOrder())
                .orElseGet(Instant::now);

        Instant latestDate = listItemRules.stream()
                .map(rule -> Stream.of(rule.getStartDate(), rule.getEndDate()).filter(Objects::nonNull).max(Comparator.naturalOrder()))
                .flatMap(Optional::stream)
                .max(Comparator.naturalOrder())
                .orElse(earliestDate);

        // TODO don't make client call for these
        boolean isOnForPublic = featureFlagClient.resolveForPublic(featureFlag.getName())
                .map(ResolvedFeatureFlag::isEnabled)
                .orElse(false);
        boolean isOnForMe = featureFlagClient.resolveForUser(featureFlag.getName(), currentUserId)
                .map(ResolvedFeatureFlag::isEnabled)
                .orElse(false);

        FeatureFlagListItemV2 result = new FeatureFlagListItemV2();

        result.setFeatureFlagId(featureFlag.getId());
        result.setFeatureFlagName(featureFlag.getName());
        result.setCreatedDate(earliestDate);
        result.setLastUpdatedDate(latestDate);
        result.setOnForMe(isOnForMe);
        result.setOnForPublic(isOnForPublic);
        result.setRules(listItemRules);

        return result;
    }

    private FeatureFlagListItemRule mapToListItemRule(FeatureFlagRule featureFlagRule) {
        FeatureFlagListItemRule result = new FeatureFlagListItemRule();

        result.setFeatureFlagRuleId(featureFlagRule.getId());
        result.setFeatureFlagId(featureFlagRule.getFeatureFlagId());
        result.setFlagStatus(featureFlagRule.getFlagStatus());
        result.setScope(featureFlagRule.getScope());
        result.setUserId(featureFlagRule.getUserId());
        result.setFlagValue(featureFlagRule.getFlagValue());
        result.setStartDate(featureFlagRule.getStartDate());
        result.setEndDate(featureFlagRule.getEndDate());
        result.setOn(FeatureFlagStatus.ENABLED.getId().equals(featureFlagRule.getFlagStatus()));

        return result;
    }

    public ModifyFeatureFlagData getModifyFeatureFlagFormData(long featureFlagId) {
        FeatureFlag featureFlag = featureFlagService.get(featureFlagId)
                .orElseThrow(() -> new IllegalArgumentException("No feature flag exists"));
        List<FeatureFlagListItemRule> rules = featureFlagRuleService.findByFeatureFlagIds(Collections.singleton(featureFlagId)).stream()
                .map(this::mapToListItemRule)
                .toList();

        ModifyFeatureFlagData result = new ModifyFeatureFlagData();
        result.setFeatureFlagId(featureFlagId);
        result.setFeatureFlagName(featureFlag.getName());
        result.setRules(rules);

        return result;
    }

    // -- DEPRECATED

    public List<FeatureFlagListItem> getListItems() {
        Set<FeatureFlag> flags = featureFlagService.getAll();
        List<FeatureFlagListItem> listItems = new ArrayList<>();

        for (FeatureFlag flag : flags) {
            FeatureFlagListItem listItem = makeListItem(flag, null);
            listItems.add(listItem);
        }

        return listItems;
    }

    public ResolvedFeatureFlag toggleStatus(UpdateFeatureFlagInput input) {
        String featureFlagName = input.getFeatureFlagName();
        boolean isOn = featureFlagClient.resolveForUser(featureFlagName, input.getUserId())
                .map(ResolvedFeatureFlag::isEnabled)
                .orElse(false);

        ClientResponse<ResolvedFeatureFlag> updatedFlagResponse;
        if (FeatureFlagUserScope.isIndividual(input.getUserScope())) {
            Long userId = input.getUserId();
            Objects.requireNonNull(userId, "A user ID is required to toggle an individually-scoped feature flag");

            updatedFlagResponse = isOn
                    ? featureFlagClient.turnOffForUser(featureFlagName, userId)
                    : featureFlagClient.turnOnForUser(featureFlagName, userId);
        } else {
            updatedFlagResponse = isOn
                    ? featureFlagClient.turnOffForPublic(featureFlagName)
                    : featureFlagClient.turnOnForPublic(featureFlagName);
        }

        if (!updatedFlagResponse.isSuccessful()) {
            throw new IllegalStateException("Error feature flag");
        }

        return updatedFlagResponse.getPayload();
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
