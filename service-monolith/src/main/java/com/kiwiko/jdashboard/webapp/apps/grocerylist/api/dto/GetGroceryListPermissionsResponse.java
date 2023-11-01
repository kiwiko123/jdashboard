package com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto;

public class GetGroceryListPermissionsResponse {
    private boolean canAccess;

    public boolean getCanAccess() {
        return canAccess;
    }

    public void setCanAccess(boolean canAccess) {
        this.canAccess = canAccess;
    }
}
