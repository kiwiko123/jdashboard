package com.kiwiko.jdashboard.tools.apiclient;

import com.google.common.collect.ImmutableSet;
import com.kiwiko.jdashboard.library.http.client.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class HttpApiRequest {
    private final @Nonnull Set<RequestHeader> requestHeaders = new HashSet<>();

    /**
     * (Required)
     *
     * @return the HTTP method of this request
     */
    @Nonnull
    public abstract RequestMethod getRequestMethod();

    /**
     * (Required)
     *
     * @return the URL to which this request is being made
     */
    @Nonnull
    public abstract RequestUrl getRequestUrl();

    /**
     * (Optional)
     *
     * @return the object that will be serialized into the request's body
     */
    @Nullable
    public Object getRequestBody() {
        return null;
    }

    /**
     * @return all request headers for this request
     */
    @Nonnull
    public final Set<RequestHeader> getRequestHeaders() {
        return new ImmutableSet.Builder<RequestHeader>()
                .addAll(getConstantRequestHeaders())
                .addAll(requestHeaders)
                .build();
    }

    /**
     * (Optional) Override this method to return any static request headers for this request
     *
     * @return static/constant request headers for this request
     */
    @Nonnull
    public Set<RequestHeader> getConstantRequestHeaders() {
        return Collections.emptySet();
    }

    /**
     * (Optional) Invoke this method to add a request header to this request. This can be utilized in a programmatic
     * context, like in a {@link com.kiwiko.jdashboard.library.http.client.plugins.v2.PreRequestPlugin}.
     *
     * @param requestHeader the request header to add
     */
    public final void addRequestHeader(@Nonnull RequestHeader requestHeader) {
        requestHeaders.add(requestHeader);
    }
}
