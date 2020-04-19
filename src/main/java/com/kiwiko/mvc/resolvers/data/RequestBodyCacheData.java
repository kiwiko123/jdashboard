package com.kiwiko.mvc.resolvers.data;

import com.kiwiko.mvc.json.data.IntermediateJsonBody;
import com.kiwiko.mvc.requests.data.RequestContextDTO;

public class RequestBodyCacheData {

    private IntermediateJsonBody body;
    private RequestContextDTO requestContext;

    public RequestBodyCacheData() { }

    public IntermediateJsonBody getBody() {
        return body;
    }

    public void setBody(IntermediateJsonBody body) {
        this.body = body;
    }

    public RequestContextDTO getRequestContext() {
        return requestContext;
    }

    public void setRequestContext(RequestContextDTO requestContext) {
        this.requestContext = requestContext;
    }
}
