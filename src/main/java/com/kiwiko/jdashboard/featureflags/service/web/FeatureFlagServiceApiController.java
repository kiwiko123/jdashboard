package com.kiwiko.jdashboard.featureflags.service.web;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagState;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.TurnOnFeatureFlagInput;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.GetFeatureFlagOutput;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.ResolvedFeatureFlag;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagStateService;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.LockedApi;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/feature-flags/service-api")
@JdashboardConfigured
@LockedApi(clients = "feature-flag-service-client")
public class FeatureFlagServiceApiController {

    @Inject private FeatureFlagService featureFlagService;
    @Inject private FeatureFlagStateService featureFlagStateService;

    @GetMapping("/{id}")
    public GetFeatureFlagOutput getFeatureFlagById(@PathVariable("id") long featureFlagId) {
        FeatureFlag flag = featureFlagService.get(featureFlagId).orElse(null);
        return new GetFeatureFlagOutput(flag);
    }

    @GetMapping("")
    public GetFeatureFlagOutput getFeatureFlagByName(
            @RequestParam("fn") String encodedFeatureFlagName,
            @RequestParam(value = "u", required = false) @Nullable Long userId) {
        String featureFlagName = URLDecoder.decode(encodedFeatureFlagName, StandardCharsets.UTF_8);
        FeatureFlag flag = userId == null
                ? featureFlagService.getByName(featureFlagName).orElse(null)
                : featureFlagService.getForUser(featureFlagName, userId).orElse(null);
        return new GetFeatureFlagOutput(flag);
    }

    @GetMapping("/state")
    public ResolvedFeatureFlag getFlagState(
            @RequestParam("fn") String featureFlagName,
            @RequestParam(value = "u", required = false) @Nullable Long userId) {
        FeatureFlagState state = userId == null
                ? featureFlagStateService.getPublicFlagByName(featureFlagName).orElse(null)
                : featureFlagStateService.getUserFlagByName(featureFlagName, userId).orElse(null);
        return new ResolvedFeatureFlag(state);
    }

    @PostMapping("/state/on")
    public ResolvedFeatureFlag turnOn(@RequestBody TurnOnFeatureFlagInput input) {
        FeatureFlagState state = input.getUserId() == null
                ? featureFlagStateService.turnOn(input.getFeatureFlagName())
                : featureFlagStateService.turnOn(input.getFeatureFlagName(), input.getUserId());
        return new ResolvedFeatureFlag(state);
    }

    @PostMapping("/state/off")
    public ResolvedFeatureFlag turnOff(@RequestBody TurnOnFeatureFlagInput input) {
        FeatureFlagState state = input.getUserId() == null
                ? featureFlagStateService.turnOff(input.getFeatureFlagName())
                : featureFlagStateService.turnOff(input.getFeatureFlagName(), input.getUserId());
        return new ResolvedFeatureFlag(state);
    }
}
