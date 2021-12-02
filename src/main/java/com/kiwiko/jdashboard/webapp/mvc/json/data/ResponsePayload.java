package com.kiwiko.jdashboard.webapp.mvc.json.data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponsePayload extends ResponseEntity<com.kiwiko.jdashboard.webapp.mvc.json.internal.data.ResponsePayload> {

    public ResponsePayload(com.kiwiko.jdashboard.webapp.mvc.json.internal.data.ResponsePayload body, HttpStatus status) {
        super(body, status);
    }
}
