package com.kiwiko.jdashboard.library.http.client.api.dto;

import com.kiwiko.jdashboard.library.http.client.api.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ServerException;

import java.net.http.HttpResponse;

@FunctionalInterface
public interface RequestErrorHandler {

    <T> void handleError(HttpResponse<T> response) throws ClientException, ServerException;
}
