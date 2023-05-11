package com.kiwiko.jdashboard.featureflags.service.web;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagState;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.FeatureFlagClient;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.ResolvedFeatureFlag;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserPermissionCheck;
import com.kiwiko.jdashboard.services.permissions.api.interfaces.PermissionNames;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/feature-flags/app-api")
@JdashboardConfigured
@UserPermissionCheck(PermissionNames.ADMIN)
public class FeatureFlagAdminAppController {
    @Inject private FeatureFlagAPIHelper featureFlagAPIHelper;
    @Inject private FeatureFlagClient featureFlagClient;

    @PostMapping("/{id}/status/toggle")
    public FeatureFlag toggleStatus(@PathVariable("id") long featureFlagId) {
        return featureFlagAPIHelper.toggleStatus(featureFlagId);
    }

    @PostMapping("/state/toggle")
    public FeatureFlagState toggleStatusByName(@RequestParam("fn") String featureFlagName) {
        FeatureFlagState currentState = featureFlagClient.resolve(featureFlagName).get().orElse(null);
        if (currentState == null) {
            return null;
        }

        ResolvedFeatureFlag resolvedFeatureFlag = currentState.isEnabled()
                ? featureFlagClient.turnOff(featureFlagName)
                : featureFlagClient.turnOn(featureFlagName);
        return resolvedFeatureFlag.get().orElse(null);
    }

    @GetMapping("/state")
    public FeatureFlagState getState(@RequestParam("fn") String featureFlagName) {
        return featureFlagClient.resolve(featureFlagName).get().orElse(null);
    }
}
