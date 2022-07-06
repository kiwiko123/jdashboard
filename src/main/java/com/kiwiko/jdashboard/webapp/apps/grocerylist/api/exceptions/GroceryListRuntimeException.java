package com.kiwiko.jdashboard.webapp.apps.grocerylist.api.exceptions;

public class GroceryListRuntimeException extends RuntimeException {

    public GroceryListRuntimeException(String message) {
        super(message);
    }

    public GroceryListRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
