package com.kiwiko.mvc.requests.internal;

import com.kiwiko.mvc.requests.data.RequestContext;
import com.kiwiko.mvc.requests.internal.entities.RequestContextEntity;
import com.kiwiko.persistence.properties.internal.FieldPropertyMapper;

public class RequestContextPropertyMapper extends FieldPropertyMapper<RequestContextEntity, RequestContext> {

    @Override
    protected Class<RequestContext> getTargetType() {
        return RequestContext.class;
    }
}
