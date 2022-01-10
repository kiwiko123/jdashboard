package com.kiwiko.jdashboard.webapp.framework.requests.internal;

import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;
import com.kiwiko.jdashboard.webapp.framework.requests.internal.dataAccess.RequestContextEntity;
import com.kiwiko.library.persistence.properties.api.EntityMapper;

import javax.inject.Singleton;

@Singleton
public class RequestContextEntityMapper extends EntityMapper<RequestContextEntity, RequestContext> {

    @Override
    protected Class<RequestContextEntity> getEntityType() {
        return RequestContextEntity.class;
    }

    @Override
    protected Class<RequestContext> getDTOType() {
        return RequestContext.class;
    }
}
