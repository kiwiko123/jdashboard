package com.kiwiko.mvc.json.data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class ResponseBuilder {

    private @Nullable Object body;
    private Collection<String> errors;
    private HttpStatus status;

    public ResponseBuilder() {
        body = null;
        errors = new ArrayList<>();
        status = HttpStatus.OK;
    }

    public ResponseBuilder withBody(Object body) {
        this.body = body;
        return this;
    }

    public ResponseBuilder withError(String error) {
        errors.add(error);
        return this;
    }

    public ResponseBuilder withStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ResponseEntity<ResponsePayload> toResponseEntity() {
        ResponsePayload payload = new ResponsePayload(body, errors);
        return new ResponseEntity<>(payload, status);
    }

    public static ResponseEntity<ResponsePayload> ok() {
        return new ResponseBuilder()
                .toResponseEntity();
    }
}
