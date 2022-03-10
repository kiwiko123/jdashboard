package com.kiwiko.jdashboard.services.userauth.internal;

import com.kiwiko.jdashboard.clients.usercredentials.api.dto.UserCredential;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.UserCredentialClient;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.QueryUserCredentialsInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.QueryUserCredentialsOutput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.ValidateUserCredentialInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.ValidateUserCredentialOutput;
import com.kiwiko.jdashboard.clients.users.api.dto.User;
import com.kiwiko.jdashboard.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.clients.users.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.clients.users.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.services.sessions.api.interfaces.SessionService;
import com.kiwiko.jdashboard.services.userauth.api.interfaces.exceptions.IncorrectPasswordException;
import com.kiwiko.jdashboard.services.userauth.api.interfaces.exceptions.InvalidUsernameException;
import com.kiwiko.jdashboard.services.userauth.api.interfaces.exceptions.UserAuthenticationException;
import com.kiwiko.jdashboard.services.userauth.web.dto.LogUserInInput;
import com.kiwiko.jdashboard.services.userauth.web.dto.LogUserInOutput;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Set;

public class UserAuthenticator {

    @Inject private UserClient userClient;
    @Inject private UserCredentialClient userCredentialClient;
    @Inject private SessionService sessionService;

    public LogUserInOutput logUserIn(LogUserInInput input) throws UserAuthenticationException {
        User user = getUserByUsername(input.getUserLoginData().getUsername());
        validatePassword(input.getUserLoginData().getPassword(), user.getId());
        logUserIn(user.getId(), input.getHttpServletResponse());

        LogUserInOutput output = new LogUserInOutput();
        output.setUserId(user.getId());
        return output;
    }

    private User getUserByUsername(String inputUsername) throws InvalidUsernameException {
        GetUsersQuery getUsersQuery = GetUsersQuery.newBuilder()
                .setUsernames(Collections.singleton(inputUsername))
                .build();
        GetUsersByQueryResponse getUsersByQueryResponse = userClient.getByQuery(getUsersQuery);;
        return getUsersByQueryResponse.getUsers().stream()
                .findFirst()
                .orElseThrow(() -> new InvalidUsernameException(String.format("No user found with username \"%s\"", inputUsername)));
    }

    private void validatePassword(String inputPassword, long userId) throws UserAuthenticationException {
        QueryUserCredentialsInput input = QueryUserCredentialsInput.newBuilder()
                .setCredentialTypes(Collections.singleton("user_password"))
                .setUserIds(Collections.singleton(userId))
                .setIsRemoved(false)
                .build();
        ClientResponse<QueryUserCredentialsOutput> response = userCredentialClient.query(input);

        if (!response.getStatus().isSuccessful()) {
            throw new UserAuthenticationException(String.format("Unable to retrieve user credentials for user ID %d", userId));
        }

        Set<UserCredential> userCredentials = response.getPayload().getUserCredentials();
        if (userCredentials.isEmpty()) {
            throw new UserAuthenticationException(String.format("No user credentials found for user %d", userId));
        }
        if (userCredentials.size() > 1) {
            throw new UserAuthenticationException(String.format("Multiple active passwords found for user %d", userId));
        }

        UserCredential userCredential = userCredentials.stream().findFirst().orElse(null);

        ValidateUserCredentialInput validateUserCredentialInput = new ValidateUserCredentialInput();
        validateUserCredentialInput.setUserCredentialId(userCredential.getId());
        validateUserCredentialInput.setPlaintextCredentialValue(inputPassword);

        ClientResponse<ValidateUserCredentialOutput> validateCredentialResponse = userCredentialClient.validate(validateUserCredentialInput);
        if (!validateCredentialResponse.getStatus().isSuccessful()) {
            throw new UserAuthenticationException(String.format("Unable to validate user credentials for user ID %d", userId));
        }

        if (!validateCredentialResponse.getPayload().getIsValid()) {
            throw new IncorrectPasswordException("Incorrect password");
        }
    }

    private void logUserIn(long userId, HttpServletResponse httpServletResponse) {
        // TODO use session client
        sessionService.createSessionCookieForUser(userId, httpServletResponse);
    }
}
