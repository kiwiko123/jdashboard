package com.kiwiko.jdashboard.webapp.framework.requests.internal;

import com.kiwiko.jdashboard.webapp.framework.requests.api.RequestContextService;
import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;
import com.kiwiko.jdashboard.webapp.framework.requests.internal.dataAccess.RequestContextEntityDAO;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Optional;

public class RequestContextEntityService implements RequestContextService {

    @Inject private RequestContextEntityDAO requestContextEntityDAO;
    @Inject private RequestContextEntityMapper mapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;

    @Override
    public String getRequestUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @Override
    public Optional<RequestContext> getById(long requestContextId) {
        return crudExecutor.get(requestContextId, requestContextEntityDAO, mapper);
    }

    @Override
    public Optional<RequestContext> getFromSession(HttpSession session, String sessionKey) {
        return Optional.ofNullable(session)
                .map(s -> s.getAttribute(sessionKey))
                .map(requestContextId -> (Long) requestContextId)
                .flatMap(this::getById);
    }

    @Override
    public RequestContext create(RequestContext requestContext) {
        Instant now = Instant.now();
        requestContext.setCreatedDate(now);
        requestContext.setLastUpdatedDate(now);
        requestContext.setIsRemoved(false);

        return crudExecutor.create(requestContext, requestContextEntityDAO, mapper);
    }

    @Override
    public RequestContext merge(RequestContext requestContext) {
        requestContext.setLastUpdatedDate(Instant.now());

        return crudExecutor.merge(requestContext, requestContextEntityDAO, mapper);
    }
}
