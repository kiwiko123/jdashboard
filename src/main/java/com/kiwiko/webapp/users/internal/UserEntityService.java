package com.kiwiko.webapp.users.internal;

import com.kiwiko.webapp.mvc.security.authentication.api.PasswordService;
import com.kiwiko.library.persistence.dataAccess.api.PersistenceException;
import com.kiwiko.webapp.users.api.UserService;
import com.kiwiko.webapp.users.api.parameters.CreateUserParameters;
import com.kiwiko.webapp.users.data.User;
import com.kiwiko.webapp.users.internal.dataAccess.UserEntityDAO;
import com.kiwiko.webapp.users.internal.dataAccess.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserEntityService implements UserService {

    @Inject
    private UserEntityDAO userEntityDAO;

    @Inject
    private UserEntityMapper mapper;

    @Inject
    private PasswordService passwordService;

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getById(long id) {
        return userEntityDAO.getById(id)
                .map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<User> getByIds(Collection<Long> ids) {
        return userEntityDAO.getByIds(ids).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getByUsername(String username) {
        return userEntityDAO.getByUsername(username)
                .map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getByEmailAddress(String emailAddress) {
        return userEntityDAO.getByEmailAddress(emailAddress)
                .map(mapper::toDTO);
    }

    @Transactional
    @Override
    public User create(CreateUserParameters parameters) {
        if (getByUsername(parameters.getUsername()).isPresent()) {
            throw new PersistenceException(
                    String.format(
                            "User with email address \"%s\" already exists",
                            parameters.getEmailAddress()));
        }

        String encryptedPassword = passwordService.encryptPassword(parameters.getPassword());

        User user = new User();
        user.setUsername(parameters.getUsername());
        user.setEncryptedPassword(encryptedPassword);
        user.setEmailAddress(parameters.getEmailAddress());

        UserEntity entity = mapper.toEntity(user);
        entity = userEntityDAO.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getWithValidation(String username, String password) {
        return getByUsername(username)
                .filter(user -> passwordService.matches(password, user.getEncryptedPassword()));
    }
}
