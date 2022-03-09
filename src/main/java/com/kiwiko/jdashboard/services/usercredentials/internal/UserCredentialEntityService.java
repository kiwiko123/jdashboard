package com.kiwiko.jdashboard.services.usercredentials.internal;

import com.kiwiko.jdashboard.services.usercredentials.api.dto.UserCredential;
import com.kiwiko.jdashboard.services.usercredentials.api.interfaces.UserCredentialService;
import com.kiwiko.jdashboard.services.usercredentials.internal.data.UserCredentialEntityDataAccessObject;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.util.Optional;

public class UserCredentialEntityService implements UserCredentialService {

    @Inject private UserCredentialEntityDataAccessObject dataAccessObject;
    @Inject private UserCredentialEntityMapper mapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;

    @Override
    public Optional<UserCredential> get(long id) {
        return crudExecutor.get(id, dataAccessObject, mapper);
    }

    @Override
    public UserCredential create(UserCredential userCredential) {
        return crudExecutor.create(userCredential, dataAccessObject, mapper);
    }

    @Override
    public UserCredential update(UserCredential userCredential) {
        return crudExecutor.update(userCredential, dataAccessObject, mapper);
    }

    @Override
    public UserCredential merge(UserCredential userCredential) {
        return crudExecutor.merge(userCredential, dataAccessObject, mapper);
    }

    @Override
    public void delete(long id) {
        crudExecutor.delete(id, dataAccessObject);
    }
}
