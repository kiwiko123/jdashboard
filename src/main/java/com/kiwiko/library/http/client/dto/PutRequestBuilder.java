package com.kiwiko.library.http.client.dto;

public final class PutRequestBuilder extends RequestBuilder<PutRequest> {

    @Override
    protected PutRequest constructRequest() {
        return new PutRequest();
    }
}
