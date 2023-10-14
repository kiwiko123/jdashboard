package com.kiwiko.jdashboard.tools.apiclient.impl.http.security.plugins;

import com.google.common.collect.ImmutableSet;
import com.kiwiko.jdashboard.library.http.client.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.PreRequestPlugin;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.RequestPlugins;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyInput;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyOutput;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequestContext;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClient;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.v2.RequestLoggerPreRequestPlugin;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.security.plugins.srk.GetInternalServiceRequestKeyRequest;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.security.plugins.srk.GetInternalServiceRequestKeyRequestContext;

import javax.inject.Inject;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Stream;

public class ServiceRequestKeyProvisioningPreRequestPlugin implements PreRequestPlugin {
    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private RequestLoggerPreRequestPlugin requestLoggerPreRequestPlugin;

    @Override
    public <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>> void preRequest(RequestType request, RequestContextType requestContext) throws ClientException {
        String clientIdentifier = requestContext.getClientIdentifier();
        if (clientIdentifier == null) {
            return;
        }

        ProvisionServiceRequestKeyInput provisionServiceRequestKeyInput = ProvisionServiceRequestKeyInput.builder()
                .serviceClientIdentifier(clientIdentifier)
                .expirationTime(Instant.now().plus(1, ChronoUnit.MINUTES).toString())
                .description("internal service key")
                .build();

        GetInternalServiceRequestKeyRequest getInternalServiceRequestKeyRequest = new GetInternalServiceRequestKeyRequest(provisionServiceRequestKeyInput);
        GetInternalServiceRequestKeyRequestContext getInternalServiceRequestKeyRequestContext = new GetInternalServiceRequestKeyRequestContext(getInternalServiceRequestKeyRequest);
        getInternalServiceRequestKeyRequestContext.setPreRequestPlugins(RequestPlugins.of(requestLoggerPreRequestPlugin));

        ClientResponse<ProvisionServiceRequestKeyOutput> response;
        try {
            response = jdashboardApiClient.synchronousCall(getInternalServiceRequestKeyRequest, getInternalServiceRequestKeyRequestContext);
        } catch (ServerException | InterruptedException e) {
            String message = "An unexpected error occurred attempting to get an internal service request key from Jdashboard";
            throw new ClientException(message, e);
        }

        if (!response.isSuccessful()) {
            String message = "Failed to obtain an internal service request key from Jdashboard";
            throw new ClientException(message);
        }

        ProvisionServiceRequestKeyOutput serviceRequestKey = response.getPayload();
        RequestHeader requestHeader = new RequestHeader(serviceRequestKey.getRequestKeyHeaderName(), serviceRequestKey.getRequestKey());

        Set<RequestHeader> requestHeaders = new ImmutableSet.Builder<RequestHeader>()
                .addAll(request.getRequestHeaders())
                .add(requestHeader)
                .build();
        request.setRequestHeaders(requestHeaders);
    }
}
