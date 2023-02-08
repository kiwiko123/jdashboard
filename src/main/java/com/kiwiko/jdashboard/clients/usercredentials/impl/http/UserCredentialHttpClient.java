package com.kiwiko.jdashboard.clients.usercredentials.impl.http;

import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.UserCredentialClient;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.CreateUserCredentialInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.CreateUserCredentialOutput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.QueryUserCredentialsInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.QueryUserCredentialsOutput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.ValidateUserCredentialInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.ValidateUserCredentialOutput;
import com.kiwiko.jdashboard.clients.usercredentials.impl.http.requests.CreateUserCredentialRequest;
import com.kiwiko.jdashboard.clients.usercredentials.impl.http.requests.QueryUserCredentialsRequest;
import com.kiwiko.jdashboard.clients.usercredentials.impl.http.requests.ValidateUserCredentialRequest;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClient;

import javax.inject.Inject;

public class UserCredentialHttpClient implements UserCredentialClient {

    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private Logger logger;

    @Override
    public ClientResponse<QueryUserCredentialsOutput> query(QueryUserCredentialsInput input) {
        QueryUserCredentialsRequest request = new QueryUserCredentialsRequest(input);
        return jdashboardApiClient.silentSynchronousCall(request);
    }

    @Override
    public ClientResponse<CreateUserCredentialOutput> create(CreateUserCredentialInput input) {
        CreateUserCredentialRequest request = new CreateUserCredentialRequest(input);
        return jdashboardApiClient.silentSynchronousCall(request);
    }

    @Override
    public ClientResponse<ValidateUserCredentialOutput> validate(ValidateUserCredentialInput input) {
        ValidateUserCredentialRequest request = new ValidateUserCredentialRequest(input);
        return jdashboardApiClient.silentSynchronousCall(request);
    }
}
