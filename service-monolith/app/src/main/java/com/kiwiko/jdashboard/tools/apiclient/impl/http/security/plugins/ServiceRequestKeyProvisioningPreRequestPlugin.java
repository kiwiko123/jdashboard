package com.kiwiko.jdashboard.tools.apiclient.impl.http.security.plugins;

import com.kiwiko.jdashboard.library.http.client.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.PreRequestPlugin;
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
        getInternalServiceRequestKeyRequestContext.addPreRequestPlugin(requestLoggerPreRequestPlugin);

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
        request.addRequestHeader(new RequestHeader(serviceRequestKey.getRequestKeyHeaderName(), serviceRequestKey.getRequestKey()));
    }
}
