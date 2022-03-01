package com.kiwiko.jdashboard.tools.httpclient.impl;

import com.kiwiko.jdashboard.library.http.client.api.exceptions.RequestTimeoutException;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ServerException;
import com.kiwiko.jdashboard.library.http.client.internal.security.DefaultAuthenticator;

import java.io.IOException;
import java.net.Authenticator;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class CoreHttpClient {
    private static final Duration DEFAULT_CLIENT_TIMEOUT = Duration.ofSeconds(10);

    protected final HttpClient httpClient;

    public CoreHttpClient() {
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

    public CompletableFuture<HttpResponse<String>> sendAsynchronousRequest(HttpRequest httpRequest) {
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
    }
}
