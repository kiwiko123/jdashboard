package com.kiwiko.jdashboard.webapp.framework.security.clientsessions.internal;

import com.kiwiko.jdashboard.services.permissions.api.interfaces.PermissionNames;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

enum ClientSessionPermissionInternalIds {
    ADMIN(186883, PermissionNames.ADMIN);

    private final int internalId;
    private final String permissionName;

    ClientSessionPermissionInternalIds(Integer internalId, String permissionName) {
        this.internalId = internalId;
        this.permissionName = permissionName;
    }

    public int getInternalId() {
        return internalId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public static Optional<Integer> getByPermissionName(String permissionName) {
        return Optional.ofNullable(PERMISSION_NAMES_TO_INTERNAL_ID_TABLE.get(permissionName));
    }

    private static final Map<String, Integer> PERMISSION_NAMES_TO_INTERNAL_ID_TABLE = Arrays.stream(values())
            .collect(Collectors.toMap(ClientSessionPermissionInternalIds::getPermissionName, ClientSessionPermissionInternalIds::getInternalId));
}
