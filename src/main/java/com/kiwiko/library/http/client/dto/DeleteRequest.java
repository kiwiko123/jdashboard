package com.kiwiko.library.http.client.dto;

public class DeleteRequest extends HttpClientRequest {
    public static RequestBuilder<DeleteRequest> newBuilder() {
        return new DeleteRequestBuilder();
    }
}
