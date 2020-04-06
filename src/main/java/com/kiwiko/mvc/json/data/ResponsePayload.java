package com.kiwiko.mvc.json.data;

import java.util.Collection;

public class ResponsePayload {

    private final Object payload;
    private final Collection<String> errors;

    ResponsePayload(Object payload, Collection<String> errors) {
        this.payload = payload;
        this.errors = errors;
    }
}