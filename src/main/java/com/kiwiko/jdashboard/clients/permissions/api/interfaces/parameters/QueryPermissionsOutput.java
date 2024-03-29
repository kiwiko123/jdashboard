package com.kiwiko.jdashboard.clients.permissions.api.interfaces.parameters;

import com.kiwiko.jdashboard.services.permissions.api.dto.Permission;

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
