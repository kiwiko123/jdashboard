package com.kiwiko.jdashboard.services.users.internal;

import com.kiwiko.jdashboard.clients.users.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.clients.users.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.clients.users.impl.di.UserDtoMapper;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.PasswordService;
import com.kiwiko.jdashboard.library.persistence.dataAccess.api.PersistenceException;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.dto.UserLoginParameters;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;
import com.kiwiko.jdashboard.services.users.api.interfaces.UserService;
import com.kiwiko.jdashboard.services.users.api.interfaces.parameters.CreateUserParameters;
import com.kiwiko.jdashboard.services.users.api.dto.User;
import com.kiwiko.jdashboard.services.users.internal.data.UserEntityDAO;
import com.kiwiko.jdashboard.services.users.internal.data.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserEntityService implements UserService {

    @Inject private UserEntityDAO userEntityDAO;
    @Inject private UserEntityMapper mapper;
    @Inject private UserDtoMapper userDtoMapper;
    @Inject private PasswordService passwordService;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;
    @Inject private TransactionProvider transactionProvider;

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getById(long id) {
        return userEntityDAO.getById(id)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<User> getByIds(Collection<Long> ids) {
        return userEntityDAO.getByIds(ids).stream()
                .map(mapper::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getByUsername(String username) {
        return userEntityDAO.getByUsername(username)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getByEmailAddress(String emailAddress) {
        return userEntityDAO.getByEmailAddress(emailAddress)
                .map(mapper::toDto);
    }

    @Transactional
    @Override
    public User create(CreateUserParameters parameters) {
        if (getByUsername(parameters.getUsername()).isPresent()) {
            throw new PersistenceException(
                    String.format(
                            "User with username \"%s\" already exists",
                            parameters.getUsername()));
        }

        String encryptedPassword = passwordService.encryptPassword(parameters.getPassword());

        User user = new User();
        user.setUsername(parameters.getUsername());
        user.setEncryptedPassword(encryptedPassword);
        user.setEmailAddress(parameters.getEmailAddress());

        UserEntity entity = mapper.toEntity(user);
        entity = userEntityDAO.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public Optional<User> getByLoginParameters(UserLoginParameters parameters) {
        return getByUsername(parameters.getUsername())
                .filter(user -> passwordService.matches(parameters.getPassword(), user.getEncryptedPassword()));
    }

    @Override
    public GetUsersByQueryResponse getByQuery(GetUsersQuery query) {
        Set<com.kiwiko.jdashboard.clients.users.api.dto.User> users = transactionProvider.readOnly(() -> userEntityDAO.getByQuery(query).stream()
                .map(mapper::toDto)
                .map(userDtoMapper::toTargetType)
                .collect(Collectors.toSet()));

        GetUsersByQueryResponse response = new GetUsersByQueryResponse();
        response.setUsers(users);
        return response;
    }

    @Override
    public User merge(User user) {
        return crudExecutor.merge(user, userEntityDAO, mapper);
    }
}
