package com.kiwiko.mvc.security.sessions.internal;

import com.kiwiko.lang.reflection.properties.api.errors.PropertyMappingException;
import com.kiwiko.mvc.security.sessions.data.Session;
import com.kiwiko.mvc.security.sessions.internal.dataAccess.SessionEntity;
import com.kiwiko.persistence.properties.api.EntityMapper;
import com.kiwiko.users.api.UserService;
import com.kiwiko.users.data.User;

import javax.inject.Inject;
import java.util.Optional;

public class SessionEntityMapper extends EntityMapper<SessionEntity, Session> {

    @Inject
    private UserService userService;

    @Override
    protected Class<SessionEntity> getEntityType() {
        return SessionEntity.class;
    }

    @Override
    protected Class<Session> getDTOType() {
        return Session.class;
    }

    @Override
    public void copyToEntity(Session source, SessionEntity destination) {
        super.copyToEntity(source, destination);
        source.getUser()
                .map(User::getId)
                .ifPresent(destination::setUserId);
    }

    @Override
    public void copyToDTO(SessionEntity source, Session destination) throws PropertyMappingException {
        super.copyToDTO(source, destination);
        Optional.ofNullable(source.getUserId())
                .flatMap(userService::getById)
                .ifPresent(destination::setUser);
    }
}
