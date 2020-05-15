package com.kiwiko.mvc.json.data;

import java.util.Collection;

public class ResponsePayload {

    private final Object payload;
    private final Collection<String> errors;
    private final int status;

    public ResponsePayload(Object payload, Collection<String> errors, int status) {
        this.payload = payload;
        this.errors = errors;
        this.status = status;
    }
}