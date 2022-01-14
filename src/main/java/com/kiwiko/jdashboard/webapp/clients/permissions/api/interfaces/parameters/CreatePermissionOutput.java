package com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters;

import com.kiwiko.jdashboard.webapp.permissions.core.api.dto.Permission;

public class CreatePermissionOutput {
    private Permission permission;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
