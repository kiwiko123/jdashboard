package com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters;

import com.kiwiko.jdashboard.webapp.permissions.api.dto.Permission;

public class CreatePermissionInput {

    private Permission permission;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
