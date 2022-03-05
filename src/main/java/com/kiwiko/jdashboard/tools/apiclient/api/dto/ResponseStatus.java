package com.kiwiko.jdashboard.tools.apiclient.api.dto;

import javax.annotation.Nullable;

public class ResponseStatus {

    public static ResponseStatus successful() {
        return new ResponseStatus(true, null, null);
    }

    public static ResponseStatus fromCode(String errorCode) {
        return new ResponseStatus(false, errorCode, null);
    }

    public static ResponseStatus fromMessage(String errorMessage) {
        return new ResponseStatus(false, null, errorMessage);
    }

    private final boolean isSuccessful;
    private final @Nullable String errorCode;
    private final @Nullable String errorMessage;

    public ResponseStatus(boolean isSuccessful, @Nullable String errorCode, @Nullable String errorMessage) {
        this.isSuccessful = isSuccessful;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    @Nullable
    public String getErrorCode() {
        return errorCode;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "ResponseStatus{" +
                "isSuccessful=" + isSuccessful +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
