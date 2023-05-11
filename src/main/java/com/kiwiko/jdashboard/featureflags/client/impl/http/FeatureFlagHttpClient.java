package com.kiwiko.jdashboard.featureflags.client.impl.http;

import com.kiwiko.jdashboard.featureflags.client.api.interfaces.FeatureFlagClient;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.GetFeatureFlagInput;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.GetFeatureFlagOutput;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.ResolvedFeatureFlag;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClient;
import com.kiwiko.jdashboard.webapp.framework.requests.api.CurrentRequestService;
import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;

import javax.inject.Inject;

public class FeatureFlagHttpClient implements FeatureFlagClient {

    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private CurrentRequestService currentRequestService;

    @Override
    public GetFeatureFlagOutput getFlag(GetFeatureFlagInput input) {
        JdashboardApiRequest request = input.getFeatureFlagId() == null
                ? new GetFeatureFlagByNameRequest(input.getFeatureFlagName(), input.getUserId())
                : new GetFeatureFlagByIdRequest(input.getFeatureFlagId());

        ClientResponse<GetFeatureFlagOutput> response = jdashboardApiClient.silentSynchronousCall(request);
        return response.getStatus().isSuccessful() ? response.getPayload() : new GetFeatureFlagOutput(null);
    }

    @Override
    public ResolvedFeatureFlag resolveForPublic(String featureFlagName) {
        GetFeatureFlagStateRequest request = new GetFeatureFlagStateRequest(featureFlagName, null);
        ClientResponse<ResolvedFeatureFlag> response = jdashboardApiClient.silentSynchronousCall(request);

        return response.isSuccessful() ? response.getPayload() : new ResolvedFeatureFlag(null);
    }

    @Override
    public ResolvedFeatureFlag resolveForUser(String featureFlagName, Long userId) {
        GetFeatureFlagStateRequest request = new GetFeatureFlagStateRequest(featureFlagName, userId);
        ClientResponse<ResolvedFeatureFlag> response = jdashboardApiClient.silentSynchronousCall(request);

        return response.isSuccessful() ? response.getPayload() : new ResolvedFeatureFlag(null);
    }

    @Override
    public ResolvedFeatureFlag resolve(String featureFlagName) {
        Long currentUserId = currentRequestService.getCurrentRequestContext()
                .map(RequestContext::getUserId)
                .orElse(null);

        return currentUserId == null
                ? resolveForPublic(featureFlagName)
                : resolveForUser(featureFlagName, currentUserId);
    }

    @Override
    public ResolvedFeatureFlag turnOnForPublic(String featureFlagName) {
        TurnOnFeatureFlagRequest request = new TurnOnFeatureFlagRequest(featureFlagName, null);
        ClientResponse<ResolvedFeatureFlag> response = jdashboardApiClient.silentSynchronousCall(request);

        return response.isSuccessful() ? response.getPayload() : new ResolvedFeatureFlag(null);
    }

    @Override
    public ResolvedFeatureFlag turnOnForUser(String featureFlagName, Long userId) {
        TurnOnFeatureFlagRequest request = new TurnOnFeatureFlagRequest(featureFlagName, userId);
        ClientResponse<ResolvedFeatureFlag> response = jdashboardApiClient.silentSynchronousCall(request);

        return response.isSuccessful() ? response.getPayload() : new ResolvedFeatureFlag(null);
    }

    @Override
    public ResolvedFeatureFlag turnOn(String featureFlagName) {
        Long currentUserId = currentRequestService.getCurrentRequestContext()
                .map(RequestContext::getUserId)
                .orElse(null);

        return currentUserId == null
                ? turnOnForPublic(featureFlagName)
                : turnOnForUser(featureFlagName, currentUserId);
    }

    @Override
    public ResolvedFeatureFlag turnOffForPublic(String featureFlagName) {
        TurnOffFeatureFlagRequest request = new TurnOffFeatureFlagRequest(featureFlagName, null);
        ClientResponse<ResolvedFeatureFlag> response = jdashboardApiClient.silentSynchronousCall(request);

        return response.isSuccessful() ? response.getPayload() : new ResolvedFeatureFlag(null);
    }

    @Override
    public ResolvedFeatureFlag turnOffForUser(String featureFlagName, Long userId) {
        TurnOffFeatureFlagRequest request = new TurnOffFeatureFlagRequest(featureFlagName, userId);
        ClientResponse<ResolvedFeatureFlag> response = jdashboardApiClient.silentSynchronousCall(request);

        return response.isSuccessful() ? response.getPayload() : new ResolvedFeatureFlag(null);
    }

    @Override
    public ResolvedFeatureFlag turnOff(String featureFlagName) {
        Long currentUserId = currentRequestService.getCurrentRequestContext()
                .map(RequestContext::getUserId)
                .orElse(null);

        return currentUserId == null
                ? turnOffForPublic(featureFlagName)
                : turnOffForUser(featureFlagName, currentUserId);
    }
}
