package com.kiwiko.library.http.client.dto;

public final class PostRequestBuilder extends RequestBuilder<PostRequest> {

    @Override
    protected PostRequest constructRequest() {
        return new PostRequest();
    }
}
