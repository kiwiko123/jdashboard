package com.kiwiko.webapp.push.api.parameters;

import com.kiwiko.webapp.push.data.PushRequest;

public class PushToClientParameters extends PushRequest {

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
