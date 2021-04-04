package com.kiwiko.library.http.client.dto;

public class PostRequest extends BodyRequest {
    public static RequestBuilder<PostRequest> newBuilder() {
        return new PostRequestBuilder();
    }
}
