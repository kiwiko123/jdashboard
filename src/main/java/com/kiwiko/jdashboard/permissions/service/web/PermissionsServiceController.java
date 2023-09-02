package com.kiwiko.jdashboard.permissions.service.web;

import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.CreatePermissionInput;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.CreatePermissionOutput;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.LockedApi;
import com.kiwiko.jdashboard.permissions.service.api.dto.Permission;
import com.kiwiko.jdashboard.permissions.service.api.interfaces.PermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/permissions/service-api")
@JdashboardConfigured
@LockedApi
public class PermissionsServiceController {

    @Inject private PermissionService permissionService;

    @GetMapping("/query")
    public Set<Permission> query(
            @RequestParam("u") Set<Long> userIds,
            @RequestParam(value = "pn", required = false) @Nullable Set<String> permissionNames) {
        QueryPermissionsInput input = QueryPermissionsInput.newBuilder()
                .setUserIds(userIds)
                .setPermissionNames(permissionNames)
                .build();

        return permissionService.query(input);
    }

    @PostMapping("")
    public CreatePermissionOutput create(@RequestBody CreatePermissionInput createPermissionInput) {
        Objects.requireNonNull(createPermissionInput);
        Objects.requireNonNull(createPermissionInput.getPermission());

        Permission createdPermission = permissionService.create(createPermissionInput.getPermission());
        CreatePermissionOutput output = new CreatePermissionOutput();
        output.setPermission(createdPermission);

        return output;
    }
}
