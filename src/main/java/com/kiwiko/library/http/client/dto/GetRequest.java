package com.kiwiko.library.http.client.dto;

public class GetRequest extends HttpClientRequest {
    public static RequestBuilder<GetRequest> newBuilder() {
        return new GetRequestBuilder();
    }
}
