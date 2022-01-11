package com.kiwiko.jdashboard.webapp.permissions.core.internal;

import com.kiwiko.jdashboard.webapp.permissions.core.api.dto.Permission;
import com.kiwiko.jdashboard.webapp.permissions.core.api.interfaces.PermissionService;
import com.kiwiko.jdashboard.webapp.permissions.core.internal.data.PermissionEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.util.Optional;

public class PermissionEntityService implements PermissionService {

    @Inject private PermissionEntityDataFetcher permissionEntityDataFetcher;
    @Inject private PermissionEntityMapper permissionEntityMapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;

    @Override
    public Optional<Permission> get(long id) {
        return crudExecutor.get(id, permissionEntityDataFetcher, permissionEntityMapper);
    }

    @Override
    public Permission create(Permission permission) {
        return crudExecutor.create(permission, permissionEntityDataFetcher, permissionEntityMapper);
    }

    @Override
    public Permission merge(Permission permission) {
        return crudExecutor.merge(permission, permissionEntityDataFetcher, permissionEntityMapper);
    }

    @Override
    public void remove(long id) {
        crudExecutor.delete(id, permissionEntityDataFetcher);
    }
}
