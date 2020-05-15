package com.kiwiko.mvc.requests.internal.dataAccess;

import com.kiwiko.persistence.dataAccess.api.AuditableEntityManagerDAO;

import javax.inject.Singleton;

@Singleton
public class RequestContextEntityDAO extends AuditableEntityManagerDAO<RequestContextEntity> {

    @Override
    protected Class<RequestContextEntity> getEntityType() {
        return RequestContextEntity.class;
    }
}
