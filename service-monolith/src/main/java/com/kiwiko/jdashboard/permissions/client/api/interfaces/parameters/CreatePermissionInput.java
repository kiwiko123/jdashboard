package com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters;

import com.kiwiko.jdashboard.permissions.service.api.dto.Permission;

public class CreatePermissionInput {

    private Permission permission;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
