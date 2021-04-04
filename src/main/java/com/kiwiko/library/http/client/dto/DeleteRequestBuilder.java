package com.kiwiko.library.http.client.dto;

public class DeleteRequestBuilder extends RequestBuilder<DeleteRequest> {

    @Override
    protected DeleteRequest constructRequest() {
        return new DeleteRequest();
    }
}
