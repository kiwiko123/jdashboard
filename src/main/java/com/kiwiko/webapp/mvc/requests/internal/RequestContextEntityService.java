package com.kiwiko.webapp.mvc.requests.internal;

import com.kiwiko.webapp.mvc.requests.api.RequestContextService;
import com.kiwiko.webapp.mvc.requests.data.RequestContext;
import com.kiwiko.webapp.mvc.requests.internal.dataAccess.RequestContextEntity;
import com.kiwiko.webapp.mvc.requests.internal.dataAccess.RequestContextEntityDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class RequestContextEntityService implements RequestContextService {

    private static final int MAX_RESULTS = 100;

    @Inject
    private RequestContextEntityDAO requestContextEntityDAO;

    @Inject
    private RequestContextEntityMapper mapper;

    @Override
    public String getRequestUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<RequestContext> getById(long requestContextId) {
        return requestContextEntityDAO.getById(requestContextId)
                .map(mapper::toDTO);
    }

    @Override
    public Optional<RequestContext> getFromSession(HttpSession session, String sessionKey) {
        return Optional.ofNullable(session)
                .map(s -> s.getAttribute(sessionKey))
                .map(requestContextId -> (Long) requestContextId)
                .flatMap(this::getById);
    }

    @Transactional
    @Override
    public RequestContext saveRequestContext(RequestContext context) {
        RequestContextEntity entity = mapper.toEntity(context);
        RequestContextEntity result = requestContextEntityDAO.save(entity);
        return mapper.toDTO(result);
    }
}
