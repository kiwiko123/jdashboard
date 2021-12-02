package com.kiwiko.jdashboard.webapp.mvc.requests.internal.dataAccess;

import com.kiwiko.jdashboard.webapp.mvc.persistence.dataaccess.api.AuditableEntityManagerDAO;

import javax.inject.Singleton;

@Singleton
public class RequestContextEntityDAO extends AuditableEntityManagerDAO<RequestContextEntity> {

    @Override
    protected Class<RequestContextEntity> getEntityType() {
        return RequestContextEntity.class;
    }
}
