package com.kiwiko.mvc.requests.api;

import com.kiwiko.mvc.requests.data.RequestContextDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Optional;

public interface RequestContextService {

    String getRequestUri(HttpServletRequest request);

    Optional<RequestContextDTO> getById(long requestContextId);

    Collection<RequestContextDTO> getRequestContexts(String requestUri);

    Optional<RequestContextDTO> getFromSession(HttpSession session, String sessionKey);

    RequestContextDTO saveRequestContext(RequestContextDTO context);
}
