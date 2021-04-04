package com.kiwiko.library.http.client.api.errors;

import net.bytebuddy.implementation.bytecode.Throw;

public class ServerException extends Exception {

    public ServerException() {
        super();
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }
}
