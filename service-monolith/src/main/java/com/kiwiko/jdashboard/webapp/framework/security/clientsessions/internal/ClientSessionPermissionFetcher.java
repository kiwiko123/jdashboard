package com.kiwiko.jdashboard.webapp.framework.security.clientsessions.internal;

import com.kiwiko.jdashboard.permissions.client.api.interfaces.PermissionClient;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.QueryPermissionsOutput;
import com.kiwiko.jdashboard.permissions.service.api.dto.Permission;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientSessionPermissionFetcher {
    @Inject private PermissionClient permissionClient;

    public Set<Integer> getPermissionInternalIdsForClientSession(@Nullable Long userId) {
        return getPermissionsForClientSession(userId).stream()
                .map(Permission::getPermissionName)
                .map(ClientSessionPermissionInternalIds::getByPermissionName)
                .flatMap(Optional::stream)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Set<Permission> getPermissionsForClientSession(@Nullable Long userId) {
        if (userId == null) {
            return Collections.emptySet();
        }

        QueryPermissionsInput queryPermissionsInput = QueryPermissionsInput.newBuilder()
                .setUserIds(Collections.singleton(userId))
                .build();
        QueryPermissionsOutput queryPermissionsOutput = permissionClient.query(queryPermissionsInput);
        return queryPermissionsOutput.getPermissions();
    }
}
