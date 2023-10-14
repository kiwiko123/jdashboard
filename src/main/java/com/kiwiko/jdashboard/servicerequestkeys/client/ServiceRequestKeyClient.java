package com.kiwiko.jdashboard.servicerequestkeys.client;

import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;

public interface ServiceRequestKeyClient {
    ClientResponse<ProvisionServiceRequestKeyOutput> provisionServiceRequestKey(ProvisionServiceRequestKeyInput input)
            throws ClientException, ServerException, InterruptedException;
}
