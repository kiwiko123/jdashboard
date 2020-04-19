package com.kiwiko.mvc.security.sessions.internal;

import com.kiwiko.lang.reflection.properties.api.BidirectionalFieldMapper;
import com.kiwiko.lang.reflection.properties.api.errors.PropertyMappingException;
import com.kiwiko.mvc.security.sessions.data.Session;
import com.kiwiko.mvc.security.sessions.internal.dataAccess.SessionEntity;
import com.kiwiko.users.api.UserService;
import com.kiwiko.users.data.User;

import javax.inject.Inject;
import java.util.Optional;

public class SessionEntityMapper extends BidirectionalFieldMapper<SessionEntity, Session> {

    @Inject
    private UserService userService;

    @Override
    protected Class<SessionEntity> getSourceType() {
        return SessionEntity.class;
    }

    @Override
    protected Class<Session> getTargetType() {
        return Session.class;
    }

    @Override
    public void toSource(Session source, SessionEntity destination) {
        super.toSource(source, destination);
        source.getUser()
                .map(User::getId)
                .ifPresent(destination::setUserId);
    }

    @Override
    public void toTarget(SessionEntity source, Session destination) throws PropertyMappingException {
        super.toTarget(source, destination);
        Optional.ofNullable(source.getUserId())
                .flatMap(userService::getById)
                .ifPresent(destination::setUser);
    }
}
