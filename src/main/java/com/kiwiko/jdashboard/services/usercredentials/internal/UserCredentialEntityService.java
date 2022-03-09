package com.kiwiko.jdashboard.services.usercredentials.internal;

import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.CreateUserCredentialInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.EncryptionStrategies;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.clients.usercredentials.api.dto.UserCredential;
import com.kiwiko.jdashboard.services.usercredentials.api.interfaces.QueryUserCredentialsInput;
import com.kiwiko.jdashboard.services.usercredentials.api.interfaces.UserCredentialService;
import com.kiwiko.jdashboard.services.usercredentials.internal.data.UserCredentialEntity;
import com.kiwiko.jdashboard.services.usercredentials.internal.data.UserCredentialEntityDataAccessObject;
import com.kiwiko.jdashboard.services.usercredentials.internal.encryption.DefaultUserCredentialEncryptor;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserCredentialEntityService implements UserCredentialService {

    @Inject private UserCredentialEntityDataAccessObject dataAccessObject;
    @Inject private UserCredentialEntityMapper mapper;
    @Inject private DefaultUserCredentialEncryptor defaultUserCredentialEncryptor;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;
    @Inject private TransactionProvider transactionProvider;

    @Override
    public Optional<UserCredential> get(long id) {
        return crudExecutor.get(id, dataAccessObject, mapper);
    }

    @Override
    public UserCredential create(UserCredential userCredential) {
        return crudExecutor.create(userCredential, dataAccessObject, mapper);
    }

    @Override
    public UserCredential create(CreateUserCredentialInput input) {
        Objects.requireNonNull(input.getCredentialType(), "Credential type is required");
        Objects.requireNonNull(input.getCredentialValue(), "Credential value is required");

        if (input.getEncryptionStrategy() == null) {
            input.setEncryptionStrategy(EncryptionStrategies.DEFAULT);
        }

        UserCredential userCredential = new UserCredential();
        userCredential.setUserId(input.getUserId());
        userCredential.setCredentialType(input.getCredentialType());
        userCredential.setCreatedDate(Instant.now());
        userCredential.setIsRemoved(false);

        final String credentialValue;
        switch (input.getEncryptionStrategy()) {
            case EncryptionStrategies.DEFAULT:
            case EncryptionStrategies.SPRING_BCRYPT:
                credentialValue = defaultUserCredentialEncryptor.encryptPassword(input.getCredentialValue());
                break;
            case EncryptionStrategies.NONE:
            default:
                credentialValue = input.getCredentialValue();
        }

        userCredential.setCredentialValue(credentialValue);

        return transactionProvider.readWrite(() -> {
            UserCredentialEntity entityToCreate = mapper.toEntity(userCredential);
            UserCredentialEntity createdEntity = dataAccessObject.save(entityToCreate);
            return mapper.toDto(createdEntity);
        });
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

    @Override
    public Set<UserCredential> query(QueryUserCredentialsInput input) {
        return transactionProvider.readOnly(() -> dataAccessObject.query(input)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toSet()));
    }
}
