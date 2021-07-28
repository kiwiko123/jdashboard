package com.kiwiko.webapp.users.internal;

import com.kiwiko.webapp.mvc.security.authentication.api.PasswordService;
import com.kiwiko.library.persistence.dataAccess.api.PersistenceException;
import com.kiwiko.webapp.mvc.security.authentication.api.dto.UserLoginParameters;
import com.kiwiko.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;
import com.kiwiko.webapp.users.api.UserService;
import com.kiwiko.webapp.users.api.parameters.CreateUserParameters;
import com.kiwiko.webapp.users.data.User;
import com.kiwiko.webapp.users.internal.dataAccess.UserEntityDAO;
import com.kiwiko.webapp.users.internal.dataAccess.UserEntity;
import com.kiwiko.webapp.clients.users.api.parameters.GetUsersQuery;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserEntityService implements UserService {

    @Inject private UserEntityDAO userEntityDAO;
    @Inject private UserEntityMapper mapper;
    @Inject private PasswordService passwordService;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;

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

    @Transactional(readOnly = true)
    @Override
    public List<User> getByQuery(GetUsersQuery query) {
        return userEntityDAO.getByQuery(query).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public User merge(User user) {
        return crudExecutor.merge(user, userEntityDAO, mapper);
    }
}
