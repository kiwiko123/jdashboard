package com.kiwiko.jdashboard.services.permissions.api.interfaces;

import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.jdashboard.services.permissions.api.dto.Permission;

import java.util.Optional;
import java.util.Set;

public interface PermissionService {

    Optional<Permission> get(long id);

    Permission create(Permission permission);

    Permission merge(Permission permission);

    void remove(long id);

    Set<Permission> query(QueryPermissionsInput input);
}
