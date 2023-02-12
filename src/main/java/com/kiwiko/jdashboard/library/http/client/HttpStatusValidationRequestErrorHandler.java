package com.kiwiko.jdashboard.library.http.client;

import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;
import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;

public class HttpStatusValidationRequestErrorHandler implements RequestErrorHandler {

    @Override
    public <T> void handleError(HttpResponse<T> response) throws ClientException, ServerException {
        HttpStatus httpStatus = HttpStatus.valueOf(response.statusCode());
        if (httpStatus.is4xxClientError()) {
            throw new ClientException(String.format("Request failed with HTTP status %s: %s", httpStatus, response));
        }

        if (httpStatus.is5xxServerError()) {
            throw new ServerException(String.format("Request failed with HTTP status %s: %s", httpStatus, response));
        }
    }
}
