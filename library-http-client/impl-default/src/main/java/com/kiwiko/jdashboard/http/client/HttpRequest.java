package com.kiwiko.jdashboard.http.client;

import com.kiwiko.jdashboard.http.client.url.RequestUrl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public interface HttpRequest {
    @Nonnull
    RequestMethod getRequestMethod();

    @Nonnull
    RequestUrl getRequestUrl();

    @Nullable
    Object getRequestBody();

    @Nonnull
    Set<RequestHeader> getRequestHeaders();
}
