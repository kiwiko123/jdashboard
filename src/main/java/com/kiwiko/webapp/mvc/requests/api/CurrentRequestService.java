package com.kiwiko.webapp.mvc.requests.api;

import com.kiwiko.webapp.mvc.requests.data.RequestContext;

import java.util.Optional;

public interface CurrentRequestService {

    Optional<RequestContext> getCurrentRequestContext();
}
