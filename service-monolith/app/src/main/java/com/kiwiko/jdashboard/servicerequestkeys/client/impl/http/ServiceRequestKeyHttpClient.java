package com.kiwiko.jdashboard.servicerequestkeys.client.impl.http;

import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyInput;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyOutput;
import com.kiwiko.jdashboard.servicerequestkeys.client.ServiceRequestKeyClient;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClient;

import javax.inject.Inject;

public class ServiceRequestKeyHttpClient implements ServiceRequestKeyClient {
    @Inject private JdashboardApiClient jdashboardApiClient;

    @Override
    public ClientResponse<ProvisionServiceRequestKeyOutput> provisionServiceRequestKey(ProvisionServiceRequestKeyInput input)
            throws ClientException, ServerException, InterruptedException {
        ProvisionServiceRequestKeyRequest request = new ProvisionServiceRequestKeyRequest(input);
        return jdashboardApiClient.synchronousCall(request);
    }
}
