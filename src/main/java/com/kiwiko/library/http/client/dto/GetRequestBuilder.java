package com.kiwiko.library.http.client.dto;

public class GetRequestBuilder extends RequestBuilder<GetRequest> {

    @Override
    protected GetRequest constructRequest() {
        return new GetRequest();
    }
}
