package com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.parameters;

import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.dto.PushRequest;

public class OnPushReceivedParameters extends PushRequest {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
