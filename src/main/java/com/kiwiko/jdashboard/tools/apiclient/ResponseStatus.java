package com.kiwiko.jdashboard.tools.apiclient;

import javax.annotation.Nullable;

public class ResponseStatus {
    public static ResponseStatus successful() {
        return new ResponseStatus(true, "success", null);
    }

    public static ResponseStatus fromMessage(String errorMessage) {
        return new ResponseStatus(false, "failure", errorMessage);
    }

    private final boolean isSuccessful;
    private final String statusCode;
    private final @Nullable String errorMessage;

    public ResponseStatus(boolean isSuccessful, String statusCode, @Nullable String errorMessage) {
        this.isSuccessful = isSuccessful;
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getStatusCode() {
        return statusCode;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "ResponseStatus{" +
                "isSuccessful=" + isSuccessful +
                ", errorCode='" + statusCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
