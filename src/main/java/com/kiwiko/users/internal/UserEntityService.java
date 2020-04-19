package com.kiwiko.users.internal;

import com.kiwiko.mvc.security.authentication.api.PasswordService;
import com.kiwiko.persistence.dataAccess.api.PersistenceException;
import com.kiwiko.users.api.UserService;
import com.kiwiko.users.data.User;
import com.kiwiko.users.internal.dataAccess.UserEntityDAO;
import com.kiwiko.users.internal.dataAccess.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

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
                .map(mapper::toTargetType);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getByUsername(String username) {
        return userEntityDAO.getByUsername(username)
                .map(mapper::toTargetType);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getByEmailAddress(String emailAddress) {
        return userEntityDAO.getByEmailAddress(emailAddress)
                .map(mapper::toTargetType);
    }

    @Transactional
    @Override
    public User create(User user) {
        if (getByUsername(user.getUsername()).isPresent()) {
            throw new PersistenceException(String.format("User with email address \"%s\" already exists", user.getEmailAddress()));
        }

        UserEntity entity = mapper.toSourceType(user);
        String encryptedPassword = passwordService.encryptPassword(user.getEncryptedPassword());
        entity.setEncryptedPassword(encryptedPassword);

        UserEntity managedEntity = userEntityDAO.save(entity);
        return mapper.toTargetType(managedEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getWithValidation(String username, String password) {
        return getByUsername(username)
                .filter(user -> passwordService.matches(password, user.getEncryptedPassword()));
    }
}
