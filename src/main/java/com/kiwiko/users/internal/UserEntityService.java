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

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getById(long id) {
        return userEntityDAO.getById(id)
                .map(mapper::toTargetType);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getByEmailAddress(String emailAddress) {
        return userEntityDAO.getByEmailAddress(emailAddress)
                .map(mapper::toTargetType);
    }

    @Override
    @Transactional
    public User create(User user) {
        if (getByEmailAddress(user.getEmailAddress()).isPresent()) {
            throw new PersistenceException(String.format("User with email address \"%s\" already exists", user.getEmailAddress()));
        }

        UserEntity entity = mapper.toSourceType(user);
        String encryptedPassword = passwordService.encryptPassword(user.getEncryptedPassword());
        entity.setEncryptedPassword(encryptedPassword);

        userEntityDAO.save(entity);
        return mapper.toTargetType(entity);
    }

    @Override
    public boolean isValidUser(String emailAddress, String password) {
        return getByEmailAddress(emailAddress)
                .map(User::getEncryptedPassword)
                .map(encryptedPassword -> passwordService.matches(password, encryptedPassword))
                .orElse(false);
    }
}
