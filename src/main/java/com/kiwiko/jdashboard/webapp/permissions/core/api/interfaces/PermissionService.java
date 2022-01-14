package com.kiwiko.jdashboard.webapp.permissions.core.api.interfaces;

import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.jdashboard.webapp.permissions.core.api.dto.Permission;

import java.util.Optional;
import java.util.Set;

public interface PermissionService {

    Optional<Permission> get(long id);

    Permission create(Permission permission);

    Permission merge(Permission permission);

    void remove(long id);

    Set<Permission> query(QueryPermissionsInput input);
}
