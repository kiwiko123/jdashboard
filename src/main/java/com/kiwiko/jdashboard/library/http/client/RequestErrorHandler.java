package com.kiwiko.jdashboard.library.http.client;

import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;

import java.net.http.HttpResponse;

@FunctionalInterface
public interface RequestErrorHandler {

    <T> void handleError(HttpResponse<T> response) throws ClientException, ServerException;
}
