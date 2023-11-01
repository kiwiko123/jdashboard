package com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.parameters;

import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.dto.PushRequest;

public class PushToClientParameters extends PushRequest {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
