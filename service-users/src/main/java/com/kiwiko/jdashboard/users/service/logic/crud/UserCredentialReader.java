package com.kiwiko.jdashboard.users.service.logic.crud;

import com.kiwiko.jdashboard.users.service.data.UserCredentialDataAccessObject;
import com.kiwiko.jdashboard.users.service.dto.UserCredential;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

public class UserCredentialReader {
    @Inject private UserCredentialEntityMapper userCredentialEntityMapper;
    @Inject private UserCredentialDataAccessObject userCredentialDataAccessObject;

    @Transactional
    public List<UserCredential> getForUser(long userId) {
        return userCredentialDataAccessObject.getForUser(userId).stream()
                .map(userCredentialEntityMapper::toDto)
                .toList();
    }
}
