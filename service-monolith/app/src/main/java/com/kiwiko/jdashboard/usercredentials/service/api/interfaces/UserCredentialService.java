package com.kiwiko.jdashboard.usercredentials.service.api.interfaces;

import com.kiwiko.jdashboard.usercredentials.client.api.dto.UserCredential;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.CreateUserCredentialInput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.QueryUserCredentialsInput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.QueryUserCredentialsOutput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.ValidateUserCredentialInput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.ValidateUserCredentialOutput;

import java.util.Optional;

public interface UserCredentialService {

    Optional<UserCredential> get(long id);

    UserCredential create(UserCredential userCredential);
    UserCredential create(CreateUserCredentialInput input);

    UserCredential update(UserCredential userCredential);

    UserCredential merge(UserCredential userCredential);

    void delete(long id);

    QueryUserCredentialsOutput query(QueryUserCredentialsInput input);

    ValidateUserCredentialOutput validateUserCredential(ValidateUserCredentialInput input);
}
