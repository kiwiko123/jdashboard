package com.kiwiko.jdashboard.clients.usercredentials.api.interfaces;

import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.CreateUserCredentialInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.CreateUserCredentialOutput;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;

public interface UserCredentialClient {

    ClientResponse<CreateUserCredentialOutput> create(CreateUserCredentialInput input);
}
