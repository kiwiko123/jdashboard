package com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters;

import com.kiwiko.jdashboard.permissions.service.api.dto.Permission;

import java.util.Set;

public class QueryPermissionsOutput {
    private Set<Permission> permissions;

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
