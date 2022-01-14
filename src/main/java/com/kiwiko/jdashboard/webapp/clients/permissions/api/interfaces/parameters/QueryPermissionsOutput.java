package com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters;

import com.kiwiko.jdashboard.webapp.permissions.core.api.dto.Permission;

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
