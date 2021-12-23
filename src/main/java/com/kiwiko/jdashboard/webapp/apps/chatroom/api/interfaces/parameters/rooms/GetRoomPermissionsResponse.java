package com.kiwiko.jdashboard.webapp.apps.chatroom.api.interfaces.parameters.rooms;

public class GetRoomPermissionsResponse {
    private boolean canAccess;

    public boolean isCanAccess() {
        return canAccess;
    }

    public void setCanAccess(boolean canAccess) {
        this.canAccess = canAccess;
    }
}
