package com.kiwiko.jdashboard.userauth.service.internal;

import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.UserCredentialClient;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.CreateUserCredentialInput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.CreateUserCredentialOutput;
import com.kiwiko.jdashboard.clients.users.api.dto.User;
import com.kiwiko.jdashboard.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.clients.users.api.interfaces.responses.CreateUserOutput;
import com.kiwiko.jdashboard.userauth.service.api.interfaces.exceptions.CreateUserException;
import com.kiwiko.jdashboard.userauth.service.web.dto.CreateUserInput;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;

import javax.inject.Inject;
import java.util.Objects;

public class UserCreator {

    @Inject private UserClient userClient;
    @Inject private UserCredentialClient userCredentialClient;

    public CreateUserOutput createUser(CreateUserInput input) throws CreateUserException {
        Objects.requireNonNull(input.getUsername(), "Username is required to create user");
        Objects.requireNonNull(input.getPassword(), "Password is required to create user");

        com.kiwiko.jdashboard.clients.users.api.interfaces.responses.CreateUserInput clientCreateUserInput = new com.kiwiko.jdashboard.clients.users.api.interfaces.responses.CreateUserInput();
        clientCreateUserInput.setUsername(input.getUsername());
        clientCreateUserInput.setEmailAddress(input.getEmailAddress());

        ClientResponse<CreateUserOutput> createUserResponse = userClient.create(clientCreateUserInput);
        if (!createUserResponse.getStatus().isSuccessful()) {
            throw new CreateUserException(String.format("Failed to create user %s with error %s", input.getUsername(), createUserResponse.getStatus().getErrorMessage()));
        }

        User createdUser = createUserResponse.getPayload().getUser();
        CreateUserCredentialInput createUserCredentialInput = new CreateUserCredentialInput(
                createdUser.getId(),
                "user_password",
                input.getPassword());
        ClientResponse<CreateUserCredentialOutput> createUserCredentialResponse = userCredentialClient.create(createUserCredentialInput);
        if (!createUserCredentialResponse.getStatus().isSuccessful()) {
            throw new CreateUserException(
                    String.format(
                            "Failed to create user credentials for user %d with error %s",
                            createdUser.getId(),
                            createUserCredentialResponse.getStatus().getErrorMessage()));
        }

        return createUserResponse.getPayload();
    }
}
