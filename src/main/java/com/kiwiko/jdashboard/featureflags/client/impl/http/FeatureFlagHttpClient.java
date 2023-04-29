package com.kiwiko.jdashboard.featureflags.client.impl.http;

import com.kiwiko.jdashboard.featureflags.client.api.interfaces.FeatureFlagClient;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.GetFeatureFlagInput;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.GetFeatureFlagOutput;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClient;

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
