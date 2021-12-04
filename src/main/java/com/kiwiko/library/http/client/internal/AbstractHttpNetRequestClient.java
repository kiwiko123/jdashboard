package com.kiwiko.library.http.client.internal;

import com.kiwiko.library.http.client.dto.HttpClientResponse;
import com.kiwiko.library.http.client.dto.RequestHeader;
import com.kiwiko.library.http.client.dto.ResponseMetadata;
import com.kiwiko.library.http.client.internal.caching.RequestCacheHelper;
import com.kiwiko.library.http.client.internal.security.DefaultAuthenticator;
import com.kiwiko.jdashboard.webapp.framework.json.api.JsonSerializer;
import com.kiwiko.jdashboard.webapp.framework.json.impl.GsonJsonSerializer;

import java.net.Authenticator;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

abstract class AbstractHttpNetRequestClient {
    private static final Duration DEFAULT_CLIENT_TIMEOUT = Duration.ofSeconds(30);

    protected final HttpClient httpClient;
    protected final HttpRequestConverter httpRequestConverter;
    protected final RequestCacheHelper requestCacheHelper;

    public AbstractHttpNetRequestClient() {
        if (Authenticator.getDefault() == null) {
            Authenticator.setDefault(new DefaultAuthenticator());
        }

        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(DEFAULT_CLIENT_TIMEOUT)
                .authenticator(Authenticator.getDefault())
                .build();
        httpRequestConverter = new HttpRequestConverter();
        requestCacheHelper = new RequestCacheHelper();
    }

    protected JsonSerializer getSerializer() {
        return new GsonJsonSerializer();
    }

    @SuppressWarnings("unchecked")
    protected <ResponseType> HttpClientResponse<ResponseType> convertResponse(HttpResponse<String> httpResponse, Class<ResponseType> responseType) {
        int status = httpResponse.statusCode();
        ResponseMetadata metadata = ResponseMetadata.newBuilder()
                .setUrl(httpResponse.uri().toString())
                .setHeaders(makeHeaders(httpResponse))
                .build();
        ResponseType payload;

        if (responseType.isAssignableFrom(String.class)) {
            payload = (ResponseType) (httpResponse.body());
        } else {
            payload = getSerializer().fromJson(httpResponse.body(), responseType);
        }

        return new HttpClientResponse<>(status, payload, metadata);
    }

    private Set<RequestHeader> makeHeaders(HttpResponse<?> httpResponse) {
        Set<RequestHeader> headers = new HashSet<>();
        httpResponse.headers().map().forEach((header, values) -> {
            values.stream()
                    .map(value -> new RequestHeader(header, value))
                    .forEach(headers::add);
        });

        return headers;
    }
}
