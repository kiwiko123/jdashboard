package com.kiwiko.jdashboard.webapp.framework.security.sessions.internal;

import com.kiwiko.jdashboard.webapp.framework.security.sessions.data.Session;
import com.kiwiko.jdashboard.webapp.framework.security.sessions.internal.dataAccess.SessionEntity;
import com.kiwiko.library.persistence.properties.api.EntityMapper;

public class SessionEntityMapper extends EntityMapper<SessionEntity, Session> {

    @Override
    protected Class<SessionEntity> getEntityType() {
        return SessionEntity.class;
    }

    @Override
    protected Class<Session> getDTOType() {
        return Session.class;
    }
}
