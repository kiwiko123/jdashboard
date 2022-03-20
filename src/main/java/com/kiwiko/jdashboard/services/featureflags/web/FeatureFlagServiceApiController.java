package com.kiwiko.jdashboard.services.featureflags.web;

import com.kiwiko.jdashboard.clients.featureflags.api.interfaces.parameters.GetFeatureFlagOutput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.InternalServiceCheck;
import com.kiwiko.jdashboard.services.featureflags.api.interfaces.FeatureFlagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import javax.inject.Inject;

@RestController
@RequestMapping("/feature-flags/service-api")
@JdashboardConfigured
@InternalServiceCheck
public class FeatureFlagServiceApiController {

    @Inject private FeatureFlagService featureFlagService;

    @GetMapping("")
    public GetFeatureFlagOutput getFeatureFlag(
            @RequestParam(value = "id", required = false) @Nullable Long featureFlagId,
            @RequestParam(value = "n", required = false) @Nullable String featureFlagName,
            @RequestParam(value = "u", required = false) @Nullable Long userId) {
        return null;
    }
}
