package com.kiwiko.webapp.mvc.json.data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class WebResponse extends ResponseEntity<ResponsePayload> {

    public WebResponse(ResponsePayload body, HttpStatus status) {
        super(body, status);
    }
}
