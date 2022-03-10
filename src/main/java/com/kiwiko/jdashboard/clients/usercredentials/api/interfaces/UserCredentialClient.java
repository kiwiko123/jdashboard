package com.kiwiko.jdashboard.clients.usercredentials.api.interfaces;

import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.CreateUserCredentialInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.CreateUserCredentialOutput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.QueryUserCredentialsInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.QueryUserCredentialsOutput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.ValidateUserCredentialInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.ValidateUserCredentialOutput;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;

public interface UserCredentialClient {

    ClientResponse<QueryUserCredentialsOutput> query(QueryUserCredentialsInput input);

    ClientResponse<CreateUserCredentialOutput> create(CreateUserCredentialInput input);

    ClientResponse<ValidateUserCredentialOutput> validate(ValidateUserCredentialInput input);
}
