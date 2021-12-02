package com.kiwiko.jdashboard.webapp.mvc.resolvers.data;

import com.kiwiko.library.json.data.IntermediateJsonBody;
import com.kiwiko.jdashboard.webapp.mvc.requests.data.RequestContext;

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
