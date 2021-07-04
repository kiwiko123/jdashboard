package com.kiwiko.library.http.client.dto;

public final class GetRequestBuilder extends RequestBuilder<GetRequest> {

    @Override
    protected GetRequest constructRequest() {
        return new GetRequest();
    }
}
