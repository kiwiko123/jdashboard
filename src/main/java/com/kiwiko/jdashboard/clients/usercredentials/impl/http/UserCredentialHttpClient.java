package com.kiwiko.jdashboard.clients.usercredentials.impl.http;

import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.UserCredentialClient;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.CreateUserCredentialInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.CreateUserCredentialOutput;
import com.kiwiko.jdashboard.clients.usercredentials.impl.http.requests.CreateUserCredentialRequest;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardApiClient;

import javax.inject.Inject;

public class UserCredentialHttpClient implements UserCredentialClient {

    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private Logger logger;

    @Override
    public ClientResponse<CreateUserCredentialOutput> create(CreateUserCredentialInput input) {
        CreateUserCredentialRequest request = new CreateUserCredentialRequest(input);
        return jdashboardApiClient.silencedSynchronousCall(request);
    }
}
