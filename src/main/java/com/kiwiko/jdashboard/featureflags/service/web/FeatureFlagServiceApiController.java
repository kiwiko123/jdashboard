package com.kiwiko.jdashboard.featureflags.service.web;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.GetFeatureFlagOutput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.LockedApi;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@LockedApi
public class FeatureFlagServiceApiController {

    @Inject private FeatureFlagService featureFlagService;

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
}
