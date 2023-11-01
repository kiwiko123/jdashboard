package com.kiwiko.jdashboard.usercredentials.service.internal;

import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.CreateUserCredentialInput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.EncryptionStrategies;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.QueryUserCredentialsOutput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.ValidateUserCredentialInput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.ValidateUserCredentialOutput;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.usercredentials.client.api.dto.UserCredential;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.QueryUserCredentialsInput;
import com.kiwiko.jdashboard.usercredentials.service.api.interfaces.UserCredentialService;
import com.kiwiko.jdashboard.usercredentials.service.internal.data.UserCredentialEntity;
import com.kiwiko.jdashboard.usercredentials.service.internal.data.UserCredentialEntityDataAccessObject;
import com.kiwiko.jdashboard.usercredentials.service.internal.encryption.DefaultUserCredentialEncryptor;
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
    public QueryUserCredentialsOutput query(QueryUserCredentialsInput input) {
        Set<UserCredential> userCredentials = transactionProvider.readOnly(() -> dataAccessObject.query(input)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toSet()));

        QueryUserCredentialsOutput output = new QueryUserCredentialsOutput();
        output.setUserCredentials(userCredentials);
        return output;
    }

    @Override
    public ValidateUserCredentialOutput validateUserCredential(ValidateUserCredentialInput input) {
        ValidateUserCredentialOutput output = new ValidateUserCredentialOutput();

        UserCredential userCredential = get(input.getUserCredentialId())
                .orElse(null);

        if (userCredential == null) {
            output.setIsValid(false);
            return output;
        }

        boolean isValid = defaultUserCredentialEncryptor.matches(input.getPlaintextCredentialValue(), userCredential.getCredentialValue());
        output.setIsValid(isValid);

        return output;
    }
}
