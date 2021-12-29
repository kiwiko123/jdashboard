package com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.interfaces.parameters;

import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.dto.ClientSession;

public class CreateClientSessionResponse {
    private ClientSession clientSession;
    private String uuid;

    public ClientSession getClientSession() {
        return clientSession;
    }

    public void setClientSession(ClientSession clientSession) {
        this.clientSession = clientSession;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
