package com.kiwiko.jdashboard.services.usercredentials.api.interfaces;

import com.kiwiko.jdashboard.services.usercredentials.api.dto.UserCredential;

import java.util.Optional;

public interface UserCredentialService {

    Optional<UserCredential> get(long id);

    UserCredential create(UserCredential userCredential);

    UserCredential update(UserCredential userCredential);

    UserCredential merge(UserCredential userCredential);

    void delete(long id);
}
