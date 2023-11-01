package com.kiwiko.jdashboard.usercredentials.client.api.interfaces;

import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.CreateUserCredentialInput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.CreateUserCredentialOutput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.QueryUserCredentialsInput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.QueryUserCredentialsOutput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.ValidateUserCredentialInput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.ValidateUserCredentialOutput;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;

public interface UserCredentialClient {

    ClientResponse<QueryUserCredentialsOutput> query(QueryUserCredentialsInput input);

    ClientResponse<CreateUserCredentialOutput> create(CreateUserCredentialInput input);

    ClientResponse<ValidateUserCredentialOutput> validate(ValidateUserCredentialInput input);
}
