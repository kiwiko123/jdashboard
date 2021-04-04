package com.kiwiko.library.http.client.internal;

import com.google.gson.Gson;
import com.kiwiko.library.http.client.dto.HttpClientResponse;
import com.kiwiko.library.http.client.internal.security.DefaultAuthenticator;

import java.net.Authenticator;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;

abstract class AbstractHttpNetRequestClient {
    private static final Duration DEFAULT_CLIENT_TIMEOUT = Duration.ofSeconds(30);
    private static final Gson DEFAULT_GSON = new Gson();

    protected final HttpClient httpClient;
    protected final HttpRequestConverter httpRequestConverter;

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
    }

    protected Gson getSerializer() {
        return DEFAULT_GSON;
    }

    @SuppressWarnings("unchecked")
    protected <ResponseType> HttpClientResponse<ResponseType> convertResponse(HttpResponse<String> httpResponse, Class<ResponseType> responseType) {
        int status = httpResponse.statusCode();
        ResponseType payload;

        if (responseType.isAssignableFrom(String.class)) {
            payload = (ResponseType) (httpResponse.body());
        } else {
            payload = getSerializer().fromJson(httpResponse.body(), responseType);
        }

        return new HttpClientResponse<>(status, payload);
    }
}
