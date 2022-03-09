package com.kiwiko.jdashboard.services.usercredentials.api.interfaces;

import com.kiwiko.jdashboard.clients.usercredentials.api.dto.UserCredential;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.CreateUserCredentialInput;

import java.util.Optional;
import java.util.Set;

public interface UserCredentialService {

    Optional<UserCredential> get(long id);

    UserCredential create(UserCredential userCredential);
    UserCredential create(CreateUserCredentialInput input);

    UserCredential update(UserCredential userCredential);

    UserCredential merge(UserCredential userCredential);

    void delete(long id);

    Set<UserCredential> query(QueryUserCredentialsInput input);
}
