package com.kiwiko.jdashboard.webapp.framework.requests.internal.dataAccess;

import com.kiwiko.jdashboard.webapp.framework.persistence.dataaccess.api.AuditableEntityManagerDAO;

import javax.inject.Singleton;

@Singleton
public class RequestContextEntityDAO extends AuditableEntityManagerDAO<RequestContextEntity> {

    @Override
    protected Class<RequestContextEntity> getEntityType() {
        return RequestContextEntity.class;
    }
}
