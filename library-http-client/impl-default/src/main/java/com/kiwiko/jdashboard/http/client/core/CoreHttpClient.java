package com.kiwiko.jdashboard.http.client.core;

import com.kiwiko.jdashboard.http.client.exception.ServerException;

import java.io.IOException;
import java.net.Authenticator;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CoreHttpClient {
    private static final Duration DEFAULT_CLIENT_TIMEOUT = Duration.ofSeconds(60);

    protected final HttpClient httpClient;

    public CoreHttpClient() {
        Authenticator authenticator = Optional.ofNullable(Authenticator.getDefault())
                .orElseGet(DefaultAuthenticator::new);

        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(DEFAULT_CLIENT_TIMEOUT)
                .authenticator(authenticator)
                .build();
    }

    public HttpResponse<String> sendSynchronousRequest(HttpRequest httpRequest)
            throws InterruptedException, ServerException {
        try {
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new ServerException(String.format("I/O error occurred with request %s", httpRequest.toString()), e);
        }
    }

    public CompletableFuture<HttpResponse<String>> sendAsynchronousRequest(HttpRequest httpRequest) {
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
    }
}
