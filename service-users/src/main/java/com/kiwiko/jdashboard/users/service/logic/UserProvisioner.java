package com.kiwiko.jdashboard.users.service.logic;

import com.kiwiko.jdashboard.users.service.dto.User;
import com.kiwiko.jdashboard.users.service.dto.UserCredential;
import com.kiwiko.jdashboard.users.service.dto.UserCredentialType;
import com.kiwiko.jdashboard.users.service.logic.crud.UserCredentialReader;
import com.kiwiko.jdashboard.users.service.logic.crud.UserCredentialWriter;
import com.kiwiko.jdashboard.users.service.logic.crud.UserReader;
import com.kiwiko.jdashboard.users.service.logic.crud.UserWriter;
import com.kiwiko.jdashboard.users.service.logic.dto.CreateUserInput;
import com.kiwiko.jdashboard.users.service.logic.dto.CreateUserOutput;
import com.kiwiko.jdashboard.users.service.logic.encryption.UserCredentialEncryptor;
import jakarta.inject.Inject;

import java.time.Instant;

public class UserProvisioner {
    @Inject private UserReader userReader;
    @Inject private UserWriter userWriter;
    @Inject private UserCredentialReader userCredentialReader;
    @Inject private UserCredentialWriter userCredentialWriter;
    @Inject private UserCredentialEncryptor userCredentialEncryptor;

    public CreateUserOutput createNewUser(CreateUserInput input) {
        User user = createUser(input);
        UserCredential passwordCredential = createPasswordCredential(input, user.getId());

        return CreateUserOutput.builder()
                .userId(user.getId())
                .build();
    }

    private User createUser(CreateUserInput input) {
        User userToCreate = User.builder()
                .username(input.getUsername())
                .createdDate(Instant.now())
                .isRemoved(false)
                .build();

        return userWriter.create(userToCreate);
    }

    private UserCredential createPasswordCredential(CreateUserInput input, long userId) {
        String encryptedPassword = userCredentialEncryptor.encrypt(input.getPassword());

        UserCredential passwordCredentialToCreate = UserCredential.builder()
                .userId(userId)
                .credentialType(UserCredentialType.PASSWORD.getId())
                .credentialValue(encryptedPassword)
                .createdDate(Instant.now())
                .isRemoved(false)
                .build();

        return userCredentialWriter.create(passwordCredentialToCreate);
    }
}
