package com.kiwiko.jdashboard.webapp.http.client.impl.apiclient;

import com.kiwiko.jdashboard.webapp.http.client.api.dto.RequestHeader;
import com.kiwiko.jdashboard.webapp.http.client.api.exceptions.RequestTimeoutException;
import com.kiwiko.jdashboard.webapp.http.client.api.exceptions.ServerException;
import com.kiwiko.library.http.client.internal.security.DefaultAuthenticator;

import java.io.IOException;
import java.net.Authenticator;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class ApiClientHttpClient {
    private static final Duration DEFAULT_CLIENT_TIMEOUT = Duration.ofSeconds(10);

    protected final HttpClient httpClient;

    public ApiClientHttpClient() {
        if (Authenticator.getDefault() == null) {
            Authenticator.setDefault(new DefaultAuthenticator());
        }

        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(DEFAULT_CLIENT_TIMEOUT)
                .authenticator(Authenticator.getDefault())
                .build();
    }

    public HttpResponse<String> sendSynchronousRequest(HttpRequest httpRequest)
            throws InterruptedException, ServerException {
        try {
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (HttpTimeoutException e) {
            throw new RequestTimeoutException(String.format("Request timed out: %s", httpRequest.toString()), e);
        } catch (IOException e) {
            throw new ServerException(String.format("I/O error occurred with request %s", httpRequest.toString()), e);
        }
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
