package com.kiwiko.webapp.push.internal.data;

import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

public class PushServiceSessionData {

    private WebSocketSession session;

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }
}
