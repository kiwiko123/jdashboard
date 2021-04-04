package com.kiwiko.library.http.client.dto;

public class PutRequest extends BodyRequest {
    public static RequestBuilder<PutRequest> newBuilder() {
        return new PutRequestBuilder();
    }
}
