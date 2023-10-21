package com.kiwiko.jdashboard.library.http.client.impl;

import com.kiwiko.jdashboard.library.http.client.exceptions.RequestTimeoutException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;

import java.io.IOException;
import java.net.Authenticator;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CoreHttpClient {
    private static final Duration DEFAULT_CLIENT_TIMEOUT = Duration.ofSeconds(30);

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
        } catch (HttpTimeoutException e) {
            String message = httpRequest.timeout()
                    .map(timeout -> String.format("Request timed out after %d milliseconds: %s", timeout.toMillis(), httpRequest))
                    .orElse(String.format("Request timed out: %s", httpRequest));
            throw new RequestTimeoutException(message, e);
        } catch (IOException e) {
            throw new ServerException(String.format("I/O error occurred with request %s", httpRequest.toString()), e);
        }
    }

    public CompletableFuture<HttpResponse<String>> sendAsynchronousRequest(HttpRequest httpRequest) {
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
    }
}