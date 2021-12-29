package com.kiwiko.jdashboard.webapp.framework.security.sessions.internal;

import com.kiwiko.library.lang.reflection.properties.api.errors.PropertyMappingException;
import com.kiwiko.jdashboard.webapp.framework.security.sessions.data.Session;
import com.kiwiko.jdashboard.webapp.framework.security.sessions.internal.dataAccess.SessionEntity;
import com.kiwiko.library.persistence.properties.api.EntityMapper;
import com.kiwiko.jdashboard.webapp.users.api.UserService;
import com.kiwiko.jdashboard.webapp.users.data.User;

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
