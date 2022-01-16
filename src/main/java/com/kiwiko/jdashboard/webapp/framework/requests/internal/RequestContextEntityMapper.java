package com.kiwiko.jdashboard.webapp.framework.requests.internal;

import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;
import com.kiwiko.jdashboard.webapp.framework.requests.internal.dataAccess.RequestContextEntity;
import com.kiwiko.library.persistence.data.properties.api.interfaces.DataEntityFieldMapper;

import javax.inject.Singleton;

@Singleton
public class RequestContextEntityMapper extends DataEntityFieldMapper<RequestContextEntity, RequestContext> {
}
