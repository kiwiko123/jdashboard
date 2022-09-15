package com.kiwiko.jdashboard.clients.featureflags.impl.http;

import com.kiwiko.jdashboard.clients.featureflags.api.interfaces.FeatureFlagClient;
import com.kiwiko.jdashboard.clients.featureflags.api.interfaces.parameters.GetFeatureFlagInput;
import com.kiwiko.jdashboard.clients.featureflags.api.interfaces.parameters.GetFeatureFlagOutput;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.JdashboardApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardApiClient;

import javax.inject.Inject;

public class FeatureFlagHttpClient implements FeatureFlagClient {

    @Inject private JdashboardApiClient jdashboardApiClient;

    @Override
    public GetFeatureFlagOutput getFlag(GetFeatureFlagInput input) {
        JdashboardApiRequest request = input.getFeatureFlagId() == null
                ? new GetFeatureFlagByNameRequest(input.getFeatureFlagName(), input.getUserId())
                : new GetFeatureFlagByIdRequest(input.getFeatureFlagId());

        ClientResponse<GetFeatureFlagOutput> response = jdashboardApiClient.silentSynchronousCall(request);
        return response.getStatus().isSuccessful() ? response.getPayload() : new GetFeatureFlagOutput(null);
    }
}
