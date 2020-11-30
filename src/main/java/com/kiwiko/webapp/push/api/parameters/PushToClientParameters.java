package com.kiwiko.webapp.push.api.parameters;

import com.kiwiko.webapp.push.data.PushRequest;

public class PushToClientParameters extends PushRequest {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PushToClientParameters withMessage(String message) {
        this.message = message;
        return this;
    }
}
