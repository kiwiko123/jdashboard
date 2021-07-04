package com.kiwiko.library.http.client.api.errors;

public class HttpStatusException extends ServerException {

    public HttpStatusException() {
        super();
    }

    public HttpStatusException(String message) {
        super(message);
    }

    public HttpStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatusException(Throwable cause) {
        super(cause);
    }
}
