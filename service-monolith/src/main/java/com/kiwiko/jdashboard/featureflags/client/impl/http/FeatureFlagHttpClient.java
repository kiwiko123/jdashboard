package com.kiwiko.jdashboard.featureflags.client.impl.http;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagUserScope;
import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedFeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.FeatureFlagClient;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.GetFeatureFlagInput;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.GetFeatureFlagOutput;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.TurnOffFeatureFlagInput;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.TurnOnFeatureFlagInput;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClient;
import com.kiwiko.jdashboard.webapp.framework.requests.api.CurrentRequestService;
import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;

import javax.inject.Inject;
import java.util.Optional;

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
    public Optional<Boolean> isOn(String featureFlagName) {
        return resolve(featureFlagName).map(ResolvedFeatureFlag::isEnabled);
    }

    @Override
    public Optional<ResolvedFeatureFlag> resolveForPublic(String featureFlagName) {
        GetFeatureFlagStateRequest request = new GetFeatureFlagStateRequest(featureFlagName, null);
        ClientResponse<ResolvedFeatureFlag> response = jdashboardApiClient.silentSynchronousCall(request);

        return Optional.of(response)
                .filter(ClientResponse::isSuccessful)
                .map(ClientResponse::getPayload);
    }

    @Override
    public Optional<ResolvedFeatureFlag> resolveForUser(String featureFlagName, Long userId) {
        GetFeatureFlagStateRequest request = new GetFeatureFlagStateRequest(featureFlagName, userId);
        ClientResponse<ResolvedFeatureFlag> response = jdashboardApiClient.silentSynchronousCall(request);

        return Optional.of(response)
                .filter(ClientResponse::isSuccessful)
                .map(ClientResponse::getPayload);
    }

    @Override
    public Optional<ResolvedFeatureFlag> resolve(String featureFlagName) {
        Long currentUserId = currentRequestService.getCurrentRequestContext()
                .map(RequestContext::getUserId)
                .orElse(null);

        return currentUserId == null
                ? resolveForPublic(featureFlagName)
                : resolveForUser(featureFlagName, currentUserId);
    }

    @Override
    public ClientResponse<ResolvedFeatureFlag> turnOnForPublic(String featureFlagName) {
        TurnOnFeatureFlagInput turnOnFeatureFlagInput = new TurnOnFeatureFlagInput();
        turnOnFeatureFlagInput.setFeatureFlagName(featureFlagName);
        turnOnFeatureFlagInput.setUserScope(FeatureFlagUserScope.PUBLIC.getId());

        TurnOnFeatureFlagRequest request = new TurnOnFeatureFlagRequest(turnOnFeatureFlagInput);
        return jdashboardApiClient.silentSynchronousCall(request);
    }

    @Override
    public ClientResponse<ResolvedFeatureFlag> turnOnForUser(String featureFlagName, Long userId) {
        TurnOnFeatureFlagInput turnOnFeatureFlagInput = new TurnOnFeatureFlagInput();
        turnOnFeatureFlagInput.setFeatureFlagName(featureFlagName);
        turnOnFeatureFlagInput.setUserScope(FeatureFlagUserScope.INDIVIDUAL.getId());
        turnOnFeatureFlagInput.setUserId(userId);

        TurnOnFeatureFlagRequest request = new TurnOnFeatureFlagRequest(turnOnFeatureFlagInput);
        return jdashboardApiClient.silentSynchronousCall(request);
    }

    @Override
    public ClientResponse<ResolvedFeatureFlag> turnOn(String featureFlagName) {
        Long currentUserId = currentRequestService.getCurrentRequestContext()
                .map(RequestContext::getUserId)
                .orElse(null);

        return currentUserId == null
                ? turnOnForPublic(featureFlagName)
                : turnOnForUser(featureFlagName, currentUserId);
    }

    @Override
    public ClientResponse<ResolvedFeatureFlag> turnOffForPublic(String featureFlagName) {
        TurnOffFeatureFlagInput turnOffFeatureFlagInput = new TurnOffFeatureFlagInput();
        turnOffFeatureFlagInput.setFeatureFlagName(featureFlagName);
        turnOffFeatureFlagInput.setUserScope(FeatureFlagUserScope.PUBLIC.getId());

        TurnOffFeatureFlagRequest request = new TurnOffFeatureFlagRequest(turnOffFeatureFlagInput);
        return jdashboardApiClient.silentSynchronousCall(request);
    }

    @Override
    public ClientResponse<ResolvedFeatureFlag> turnOffForUser(String featureFlagName, Long userId) {
        TurnOffFeatureFlagInput turnOffFeatureFlagInput = new TurnOffFeatureFlagInput();
        turnOffFeatureFlagInput.setFeatureFlagName(featureFlagName);
        turnOffFeatureFlagInput.setUserScope(FeatureFlagUserScope.INDIVIDUAL.getId());
        turnOffFeatureFlagInput.setUserId(userId);

        TurnOffFeatureFlagRequest request = new TurnOffFeatureFlagRequest(turnOffFeatureFlagInput);
        return jdashboardApiClient.silentSynchronousCall(request);
    }

    @Override
    public ClientResponse<ResolvedFeatureFlag> turnOff(String featureFlagName) {
        Long currentUserId = currentRequestService.getCurrentRequestContext()
                .map(RequestContext::getUserId)
                .orElse(null);

        return currentUserId == null
                ? turnOffForPublic(featureFlagName)
                : turnOffForUser(featureFlagName, currentUserId);
    }
}
