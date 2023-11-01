package com.kiwiko.jdashboard.featureflags.service.web;

import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedFeatureFlag;
import com.kiwiko.jdashboard.featureflags.service.web.responses.GetFeatureFlagListResponse;
import com.kiwiko.jdashboard.featureflags.service.web.responses.ModifyFeatureFlagData;
import com.kiwiko.jdashboard.users.client.api.dto.User;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagUserScope;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.FeatureFlagClient;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.UpdateFeatureFlagInput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.auth.AuthenticatedUser;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserPermissionCheck;
import com.kiwiko.jdashboard.permissions.service.api.interfaces.PermissionNames;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/list")
    public GetFeatureFlagListResponse getFeatureFlagList(@AuthenticatedUser User currentUser) {
        return featureFlagAPIHelper.getFeatureFlagList(currentUser.getId());
    }

    @Deprecated
    @PostMapping("/{id:\\d+}/status/toggle")
    public FeatureFlag toggleStatus(@PathVariable("id") long featureFlagId) {
        return featureFlagAPIHelper.toggleStatus(featureFlagId);
    }

    @PostMapping("/flags/toggle")
    public ResolvedFeatureFlag toggleStatusByName(
            @RequestBody UpdateFeatureFlagInput input,
            @AuthenticatedUser User currentUser) {
        if (FeatureFlagUserScope.isIndividual(input.getUserScope()) && input.getUserId() == null) {
            input.setUserId(currentUser.getId());
        }

        return featureFlagAPIHelper.toggleStatus(input);
    }

    @GetMapping("/{id:\\d+}/edit-form-data")
    public ModifyFeatureFlagData getModifyFeatureFlagFormData(@PathVariable("id") long featureFlagId) {
        return featureFlagAPIHelper.getModifyFeatureFlagFormData(featureFlagId);
    }

    @GetMapping("/state")
    public ResolvedFeatureFlag getState(@RequestParam("fn") String featureFlagName) {
        return featureFlagClient.resolve(featureFlagName).orElse(null);
    }
}
