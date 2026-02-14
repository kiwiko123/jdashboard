package com.kiwiko.jdashboard.users.service.logic.crud;

import com.kiwiko.jdashboard.users.service.data.UserCredentialDataAccessObject;
import com.kiwiko.jdashboard.users.service.data.entity.UserCredentialEntity;
import com.kiwiko.jdashboard.users.service.dto.UserCredential;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

public class UserCredentialWriter {
    @Inject private UserCredentialEntityMapper userCredentialEntityMapper;
    @Inject private UserCredentialDataAccessObject userCredentialDataAccessObject;

    @Transactional
    public UserCredential create(UserCredential userCredential) {
        UserCredentialEntity entityToCreate = userCredentialEntityMapper.toEntity(userCredential);
        UserCredentialEntity createdEntity = userCredentialDataAccessObject.insert(entityToCreate);
        return userCredentialEntityMapper.toDto(createdEntity);
    }

    @Transactional
    public UserCredential update(UserCredential userCredential) {
        UserCredentialEntity existingEntity = userCredentialDataAccessObject.get(userCredential.getId())
                .orElseThrow(() -> new EntityNotFoundException("TODO"));

        userCredentialEntityMapper.copyToEntity(userCredential, existingEntity);
        UserCredentialEntity updatedEntity = userCredentialDataAccessObject.update(existingEntity);
        return userCredentialEntityMapper.toDto(updatedEntity);
    }

    @Transactional
    public UserCredential delete(long id) {
        UserCredentialEntity existingEntity = userCredentialDataAccessObject.get(id)
                .orElseThrow(() -> new EntityNotFoundException("TODO"));
        UserCredential existingUserCredential = userCredentialEntityMapper.toDto(existingEntity);
        userCredentialDataAccessObject.delete(existingEntity);
        return existingUserCredential;
    }
}
