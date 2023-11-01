package com.kiwiko.jdashboard.permissions.service.internal;

import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.jdashboard.permissions.service.api.dto.Permission;
import com.kiwiko.jdashboard.permissions.service.api.interfaces.PermissionService;
import com.kiwiko.jdashboard.permissions.service.internal.data.PermissionEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        permission.setCreatedDate(Instant.now());
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

    @Override
    public Set<Permission> query(QueryPermissionsInput input) {
        Objects.requireNonNull(input, "Input is required");
        return permissionEntityDataFetcher.query(input).stream()
                .map(permissionEntityMapper::toDto)
                .collect(Collectors.toSet());
    }
}
