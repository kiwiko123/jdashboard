package com.kiwiko.jdashboard.webapp.permissions.api.interfaces;

import com.kiwiko.jdashboard.webapp.permissions.api.dto.Permission;

import java.util.Optional;

public interface PermissionService {

    Optional<Permission> get(long id);

    Permission create(Permission permission);

    Permission merge(Permission permission);

    void remove(long id);
}
