package com.kiwiko.jdashboard.webapp.framework.resolvers.data;

import com.kiwiko.jdashboard.library.json.data.IntermediateJsonBody;
import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;

public class RequestBodyCacheData {

    private IntermediateJsonBody body;
    private RequestContext requestContext;

    public RequestBodyCacheData() { }

    public IntermediateJsonBody getBody() {
        return body;
    }

    public void setBody(IntermediateJsonBody body) {
        this.body = body;
    }

    public RequestContext getRequestContext() {
        return requestContext;
    }

    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }
}
