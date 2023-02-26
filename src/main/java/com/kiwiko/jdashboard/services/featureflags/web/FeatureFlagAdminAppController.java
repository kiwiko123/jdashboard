package com.kiwiko.jdashboard.services.featureflags.web;

import com.kiwiko.jdashboard.clients.featureflags.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserPermissionCheck;
import com.kiwiko.jdashboard.services.permissions.api.interfaces.PermissionNames;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/feature-flags/app-api")
@JdashboardConfigured
@UserPermissionCheck(PermissionNames.ADMIN)
public class FeatureFlagAdminAppController {
    @Inject private FeatureFlagAPIHelper featureFlagAPIHelper;

    @PostMapping("/{id}/status/toggle")
    public FeatureFlag toggleStatus(@PathVariable("id") long featureFlagId) {
        return featureFlagAPIHelper.toggleStatus(featureFlagId);
    }
}
