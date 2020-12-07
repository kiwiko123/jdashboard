package com.kiwiko.webapp.mvc.json.api;

import com.kiwiko.webapp.mvc.json.data.ResponsePayload;
import com.kiwiko.webapp.mvc.json.data.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ResponseBuilder {

    private @Nullable Object body;
    private final List<String> errors;
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

    public WebResponse build() {
        ResponsePayload payload = new ResponsePayload(body, errors, status.value());
        return new WebResponse(payload, status);
    }

    @Deprecated
    public ResponseEntity<ResponsePayload> toResponseEntity() {
        ResponsePayload payload = new ResponsePayload(body, errors, status.value());
        return new ResponseEntity<>(payload, status);
    }

    public static ResponseEntity<ResponsePayload> ok() {
        return new ResponseBuilder()
                .toResponseEntity();
    }
}
